package io.travelook.broker;

public class EndPointServer {
	
	 private String tiposerver;
	 private String ipserver; 
	 private int port ;
	 
	 public EndPointServer(String tiposerver, String ips, int port) {
			super();
			this.tiposerver = tiposerver;
			this.ipserver = ips;
			this.port = port;
		}
	 
	 public EndPointServer() {
			super();
			// TODO Auto-generated constructor stub
		}

	public String getTiposerver() {
		return tiposerver;
	}

	public void setTiposerver(String tiposerver) {
		this.tiposerver = tiposerver;
	}

	public String getIpserver() {
		return ipserver;
	}

	public void setIpserver(String ips) {
		this.ipserver = ips;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

		
	 
	 
	 
}
