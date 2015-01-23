package simplestream.common.config;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

/**
 * 
 * 配置信息类
 * 
 * @author Administrator
 * 
 */
public class Config
{
	@Option(name = "-sport")
	private int sPort = 6262; // 服务器端口 默认6262

	private List<Peer> peers = new ArrayList<Peer>(); // 需要连接的远程服务器信息

	private VideoSize size = new VideoSize(); // 视频分辨率尺寸

	@Option(name = "-rate")
	private int rate = 100; // 视频限速,减少带宽

	public int getsPort()
	{
		return sPort;
	}

	public void setsPort(int sPort)
	{
		this.sPort = sPort;
	}

	public int getRate()
	{
		return rate;
	}

	public void setRate(int rate)
	{
		this.rate = rate;
	}

	public List<Peer> getPeers()
	{
		return peers;
	}

	public VideoSize getSize()
	{
		return size;
	}

	@Option(name = "-width")
	public void setWidth(int w)
	{
		size.setWidth(w);
	}

	@Option(name = "-height")
	public void setHeight(int h)
	{
		size.setHeight(h);
	}

	@Option(name = "-remote", handler = StringArrayOptionHandler.class)
	public void setHost(String[] hosts)
	{
		for (String s : hosts)
		{
			Peer p = new Peer();
			p.setHost(s);
			peers.add(p);
		}
	}

	@Option(name = "-rport", handler = StringArrayOptionHandler.class)
	public void setPort(String[] ports)
	{
		for (int i = 0; i < peers.size(); ++i)
		{
			if (i == ports.length)
				break;

			Peer p = peers.get(i);
			p.setPort(Integer.valueOf(ports[i]));
		}
	}
}
