package io.travelook.broker;

public class EndPointClient {

   private String ipclient; 
   private int port ;
   
   public EndPointClient(String ipclient, int port) {
		super();
		this.ipclient = ipclient;
		this.port = port;
	}
   public EndPointClient() {
		super();
		// TODO Auto-generated constructor stub
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
