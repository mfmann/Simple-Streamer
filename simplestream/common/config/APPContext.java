package simplestream.common.config;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import simplestream.common.ui.Viewer;
import simplestream.network.Connection;

/**
 Configures and manages connections 
 */
public class APPContext {
	private static APPContext _INSTANCE = new APPContext();

	private ConnectManager manager = new ConnectManager();

	private Config config;

	private JFrame frame;

	private APPContext() {}

	public static APPContext getInstance() {
		return _INSTANCE;
	}

	public ConnectManager getManager() {
		return manager;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void addConnect(Connection conn) {
		manager.add(conn);
		System.out.println("add a connect, size : " + manager.size());
	}
	
	public void remove(Connection conn) {
		manager.remove(conn);
		frame.remove(conn.getView());
		frame.repaint();
		frame.pack();
		
		System.out.println("remove a connect, size : " + manager.size());
	}

	public Dimension getVideoSize() {
		return config.getSize().getSize();
	}

	public List<Peer> getPeers() {
		return config.getPeers();
	}

	public void closeConnects() {
		manager.close();
	}

	public void addViewer(Viewer view) {
		if (frame == null) return;
		System.out.println("add view");
		frame.add(view);
		frame.repaint();
		frame.pack();
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

}
