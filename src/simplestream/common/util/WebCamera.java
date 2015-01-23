package simplestream.common.util;

import org.apache.commons.codec.binary.Base64;
import org.bridj.Pointer;

import simplestream.common.config.APPContext;
import simplestream.common.ui.Viewer;

import com.github.sarxos.webcam.ds.buildin.natives.Device;
import com.github.sarxos.webcam.ds.buildin.natives.DeviceList;
import com.github.sarxos.webcam.ds.buildin.natives.OpenIMAJGrabber;

/**
 * 摄像头采集线程,  采集画面传输给connection
 * 
 * @author Administrator
 *
 */
public class WebCamera extends Thread
{

	private volatile boolean isWork = true;

	private OpenIMAJGrabber grabber;

	private int width;

	private int height;

	private Viewer view = null;

	public WebCamera()
	{
		width = APPContext.getInstance().getVideoSize().width;
		height = APPContext.getInstance().getVideoSize().height;
		open();
	}

	private void open()
	{
		grabber = new OpenIMAJGrabber();

		Device device = null;
		Pointer<DeviceList> devices = grabber.getVideoDevices();
		for (Device d : devices.get().asArrayList())
		{
			device = d;
			break;
		}

		boolean started = grabber.startSession(width, height, 20, Pointer.pointerTo(device));
		if (!started)
		{
			throw new RuntimeException("Not able to start native grabber!");
		}
		System.out.println("open camera success");

	}

	@Override
	public void run()
	{
		while (isWork)
		{

			grabber.nextFrame();

			/* Get the raw bytes of the frame. */
			byte[] raw_image = grabber.getImage().getBytes(width * height * 3);

			if (view != null)
				view.ViewerInput(raw_image);

			/* Apply a crude kind of image compression. */
			byte[] compressed_image = Compressor.compress(raw_image);

			/* Prepare the date to be sent in a text friendly format. */
			byte[] base64_image = Base64.encodeBase64(compressed_image);

			String msg = String.format("{\"type\":\"image\",\"data\":\"%s\"}", new String(base64_image));
			APPContext.getInstance().getManager().sendMsg(msg);

			try
			{
				Thread.sleep(APPContext.getInstance().getConfig().getRate());
			} catch (InterruptedException e)
			{
				// e.printStackTrace();
			}
		}
		System.out.println("camera stop work");
	}

	public void close()
	{
		isWork = false;
		grabber.stopSession();
		System.out.println("camera close");
	}

	public void setView(Viewer view)
	{
		this.view = view;
	}
}
