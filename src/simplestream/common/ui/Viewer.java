package simplestream.common.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

public class Viewer extends JPanel
{

	private static final long serialVersionUID = 1L;

	private int width;
	private int height;

	private BufferedImage image;

	private int[] toIntArray(byte[] barr)
	{
		int[] result = new int[barr.length];
		for (int i = 0; i < barr.length; i++)
			result[i] = barr[i];
		return result;
	}

	public Viewer(Dimension size)
	{
		this.width = size.width;
		this.height = size.height;
		setPreferredSize(size);
		image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
	}

	public void ViewerInput(byte[] image_bytes)
	{
		WritableRaster raster = image.getRaster();
		raster.setPixels(0, 0, width, height, toIntArray(image_bytes));
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}

}
