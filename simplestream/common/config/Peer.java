package simplestream.common.config;

public class Peer{

	private String host = "127.0.0.1";

	private int port = 6262;

	public Peer(String host, int port){
		this.host = host;
		this.port = port;
	}

	public Peer(){
	}

	public String getHost(){
		return host;
	}

	public void setHost(String host){
		this.host = host;
	}

	public int getPort(){
		return port;
	}

	public void setPort(int port){
		this.port = port;
	}

	@Override
	public String toString(){
		return "{host:'" + host + "',port:'" + port + "'}";
	}
}
