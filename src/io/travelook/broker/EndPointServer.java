package io.travelook.broker;

public class EndPointServer {
	
	 private String tiposerver;
	 private String ipclient; 
	 private int port ;
	 
	 public EndPointServer(String tiposerver, String ipclient, int port) {
			super();
			this.tiposerver = tiposerver;
			this.ipclient = ipclient;
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

	public String getIpclient() {
		return ipclient;
	}

	public void setIpclient(String ipclient) {
		this.ipclient = ipclient;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

		
	 
	 
	 
}
