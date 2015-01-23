package simplestream.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import simplestream.common.config.APPContext;
import simplestream.common.config.Peer;
import simplestream.common.util.WebCamera;

/**
 * 服务端线程，主要进行监听TCP连接
 * 
 * @author Administrator
 *
 */
public class Server extends Thread
{
	private volatile boolean isWork = false;
	private ServerSocket s;

	private WebCamera camera;

	public Server()
	{
		isWork = true;
	}

	public void startServer()
	{
		start();
		connectRemote();
	}

	public void run()
	{
		try
		{
			s = new ServerSocket(APPContext.getInstance().getConfig().getsPort());
			System.out.println("server start success");

			while (isWork)
			{
				Socket socket = s.accept();
				System.out.println("get a new connect");

				new Connection(socket);
			}
			System.out.println("server close");
		} catch (IOException e)
		{
			// e.printStackTrace();
		}
	}

	/**
	 * 关闭服务器 
	 */
	public void close()
	{
		isWork = false; //停止监听

		try
		{
			camera.close();  //关闭摄像头
			APPContext.getInstance().closeConnects();  //关闭所有Socket连接

			if (s != null && !s.isClosed())
			{
				s.close();
			}
		} catch (Exception e)
		{
			// e.printStackTrace();
		}
	}

	/**
	 * 作为客户端，连接配置中的remote服务器
	 */
	private void connectRemote()
	{
		List<Peer> peers = APPContext.getInstance().getPeers();

		for (Peer peer : peers)
		{
			try
			{
				System.out.println("connect camera");
				Socket socket = new Socket(peer.getHost(), peer.getPort());
				System.out.println("connected.  " + peer);

				new Connection(socket);
			} catch (Exception e)
			{
				System.out.println("error when connect to peer " + peer);
			}
		}
	}

	public void setCamera(WebCamera camera)
	{
		this.camera = camera;
	}
}
