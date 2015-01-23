package simplestream.common.config;

import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

//Configures the commmand line

public class Config
{
	@Option(name = "-sport")
	private int sPort = 6262; //Default server port as 6262

	private List<Peer> peers = new ArrayList<Peer>(); //List of hosts to connect to 

	private VideoSize size = new VideoSize(); //

	@Option(name = "-rate")
	private int rate = 100; //Default sleep interval of 100ms

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

	//Sets image width
	@Option(name = "-width")
	public void setWidth(int w)
	{
		size.setWidth(w);
	}

	//Sets image height
	@Option(name = "-height")
	public void setHeight(int h)
	{
		size.setHeight(h);
	}
	
	//Sets hostnames of remote servers to connect to
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
 	
	//Sets remote server ports to connect to
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
