package simplestream;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import simplestream.common.config.APPContext;
import simplestream.common.config.Config;
import simplestream.common.ui.MainFrame;
import simplestream.common.ui.Viewer;
import simplestream.common.util.WebCamera;
import simplestream.network.Server;

public class Main
{
	public static void main(String[] args)
	{
		try
		{
			// commandline configuration
			System.out.println("config");
			Config config = new Config();
			CmdLineParser parser = new CmdLineParser(config);
			parser.parseArgument(args);

			APPContext.getInstance().setConfig(config);

			// start camera
			System.out.println("start camera");
			WebCamera camera = new WebCamera();
			Viewer view = new Viewer(APPContext.getInstance().getVideoSize());
			camera.setView(view);
			camera.start();

			// start server
			System.out.println("start server");
			Server server = new Server();
			server.setCamera(camera);

			// display vedio
			MainFrame frame = new MainFrame(server);
			frame.add(view);
			frame.setVisible(true);
			frame.pack();

			APPContext.getInstance().setFrame(frame);

			server.startServer();

		} catch (CmdLineException e)
		{
			System.out.println("config error!");
			System.exit(0);
		} catch (Exception e)
		{
			System.out.println("system error!");
			System.exit(0);
		}

	}
}
