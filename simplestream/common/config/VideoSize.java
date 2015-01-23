package simplestream.common.config;

import java.awt.Dimension;

public class VideoSize
{
	private int height = 120;

	private int width = 160;

	public int getHeight(){
		return height;
	}

	public void setHeight(int height){
		this.height = height;
	}

	public int getWidth(){
		return width;
	}

	public void setWidth(int width){
		this.width = width;
	}

	public Dimension getSize(){
		return new Dimension(width, height);
	}

	@Override
	public String toString(){
		return "{width:" + width + ",height:" + height + "}";
	}
}
