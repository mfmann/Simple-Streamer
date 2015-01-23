package simplestream.network;

import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import simplestream.common.config.APPContext;
import simplestream.common.ui.Viewer;
import simplestream.common.util.Compressor;

/**
 * TCP通信类
 * 
 * @author Administrator
 *
 */
public class Connection extends Thread
{
	private Socket s;
	private BufferedReader is;
	private BufferedOutputStream os;

	private volatile boolean alive = true;
	private boolean valid = false;

	private Viewer view;  //每个连接都和一个panel绑定  用来播放视频画面

	public Connection(Socket s) throws IOException
	{
		this.s = s;
		os = new BufferedOutputStream(s.getOutputStream());
		is = new BufferedReader(new InputStreamReader(s.getInputStream()));
		APPContext.getInstance().addConnect(this);

		start();
		openStream(APPContext.getInstance().getVideoSize());
	}

	@Override
	public void run()
	{
		while (alive)
		{
			try
			{
				String str;
				if ((str = is.readLine()) != null)
				{
					handleMsg(str);
				}
			} catch (IOException e)
			{
				// 如果read操作出现异常，通过下面的代码检测是否连接已经断开，如果断开这关闭该链接并移除对应的view
				try
				{
					s.sendUrgentData(0xff);
				} catch (IOException e2)
				{
					System.out.println("discount");
					close();
				}
			}
		}
		System.out.println("connection stop work");
	}

	private void handleMsg(String msg)
	{
		JSONObject json = (JSONObject) JSONValue.parse(msg);
		if (json == null)
			return;

		String type = (String) json.get("type");

		
		//处理协议中的各种消息
		if ("startstream".equals(type))
		{
			valid = true;

			System.out.println("startstream");
			Dimension size = new Dimension();
			size.height = Integer.valueOf(String.valueOf(json.get("height")));
			size.width = Integer.valueOf(String.valueOf(json.get("width")));

			//收到startstream消息后，生成view对象开始播放画面
			view = new Viewer(size.getSize());
			APPContext.getInstance().addViewer(view);
		}

		if (valid && "image".equals(type))
		{
			byte[] nobase64_image = Base64.decodeBase64((String) json.get("data"));
			byte[] decompressed_image = Compressor.decompress(nobase64_image);
			view.ViewerInput(decompressed_image);
		}

		if ("stopstream".equals(type))
		{
			System.out.println("stopstream");
			valid = false;
		}
	}

	/**
	 * 发送各种协议消息
	 * @param msg
	 */
	public void send(String msg)
	{
		if (s.isClosed())
			return;

		try
		{
			os.write(msg.getBytes());
			os.write("\n".getBytes());
			os.flush();
		} catch (IOException e)
		{
			// e.printStackTrace();
		}
	}

	/**
	 * 关闭并移除连接信息,并且移除view画面
	 */
	public void close()
	{
		alive = false;

		try
		{
			stopStream();
			os.close();
			is.close();
			s.close();
			System.out.println("Connection close");
		} catch (Exception e)
		{
			// e.printStackTrace();
		} finally
		{
			APPContext.getInstance().remove(this);
		}
	}

	public boolean isValid()
	{
		return valid;
	}

	public void openStream(Dimension size)
	{
		send("{\"type\":\"startstream\",\"format\":\"raw\",\"width\":" + size.width + ",\"height\":" + size.height
				+ "}");
	}

	public void stopStream()
	{
		send("{\"type\":\"stopstream\"}");
	}

	public Viewer getView()
	{
		return view;
	}
}
