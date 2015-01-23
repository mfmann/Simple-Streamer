package simplestream.common.ui;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import simplestream.network.Server;

public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;

	public MainFrame(final Server server){

		setTitle("WebCamera View");
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());

		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				server.close();
				System.exit(0);
			}
		});

	}
}
