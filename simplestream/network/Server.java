package simplestream.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import simplestream.common.config.APPContext;
import simplestream.common.config.Peer;
import simplestream.common.util.WebCamera;

/**
 * Server thread which is used to listen to incoming connections
 */
public class Server extends Thread
{
	private volatile boolean isWork = false;
	private ServerSocket s;

	private WebCamera camera;

	public Server(){	
		isWork = true;
	}

	public void startServer(){
		start();
		connectRemote();
	}

	public void run(){
		try{
			s = new ServerSocket(APPContext.getInstance().getConfig().getsPort());
			System.out.println("server start success");

			while (isWork){
				Socket socket = s.accept();
				System.out.println("get a new connect");

				new Connection(socket);
			}
			System.out.println("server close");
		} catch (IOException e){
			// e.printStackTrace();
		}
	}

	/**
	 * close server
	 */
	public void close(){
		isWork = false;

		try{
			camera.close();  //close cammera
			APPContext.getInstance().closeConnects();  //close socket

			if (s != null && !s.isClosed()){
				s.close();
			}
		} catch (Exception e){
			// e.printStackTrace();
		}
	}

	/**
	 * Client tries to connect to peers defined in config.
	 */
	private void connectRemote(){
		List<Peer> peers = APPContext.getInstance().getPeers();

		for (Peer peer : peers){
			try{
				System.out.println("connect camera");
				Socket socket = new Socket(peer.getHost(), peer.getPort());
				System.out.println("connected.  " + peer);

				new Connection(socket);
			} catch (Exception e){
				System.out.println("error when connect to peer " + peer);
			}
		}
	}

	public void setCamera(WebCamera camera){
		this.camera = camera;
	}
}
