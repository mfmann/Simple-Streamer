package simplestream.common.config;

import java.util.Vector;

import simplestream.network.Connection;

/**
 * 
 * socket连接管理器,保存程序运行期间活跃的tcp连接
 * @author Administrator
 *
 */
public class ConnectManager
{
	private Vector<Connection> conns = new Vector<Connection>();

	public void add(Connection conn)
	{
		conns.add(conn);
	}

	public void remove(Connection conn)
	{
		conns.remove(conn);
	}

	public void sendMsg(String msg)
	{
		for (Connection conn : conns)
			conn.send(msg);
	}

	public void close()
	{
		for (Connection c : conns)
		{
			c.close();
		}
	}

	public int size()
	{
		return conns.size();
	}

}
