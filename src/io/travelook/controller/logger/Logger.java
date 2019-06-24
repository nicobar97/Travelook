package io.travelook.controller.logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import io.travelook.controller.filtro.Filtro;
import io.travelook.model.Entry;

public class Logger implements ILogger {
    
	

    private File file ; 
	private String path ;//percorso del file in cui scrivere i logs 
	
	public Logger() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public Logger(String path) {
		super();
		this.path = path;
		file= new File(path);
		if(file.exists()) {
			System.out.println("il file "+path+" esiste\n");
		}
		else {
			try {
				file.createNewFile();
				System.out.println("Creato il file "+path+" \n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	@Override
	public void scriviEntry(Entry e) {
		try {
			FileWriter fw= new FileWriter(path,true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(e.toString());
			bw.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

	@Override
	public List<Entry> visualizzaLog(Filtro filtro) {
		List<Entry> filtrate=new ArrayList<Entry>();
		List<Entry> totali=new ArrayList<Entry>();
        List<Object> tot=new ArrayList<Object>();
		totali=this.visualizzaLog();
		for(Entry e :totali) {
			Object o=(Object)e;
			tot.add(o);
		}
		filtrate=this.convertToEntry(filtro.filtra(filtro.getFiltri(),tot));
		return filtrate;
	}

	@Override
	public List<Entry> visualizzaLog() {
		List<Entry> res = new ArrayList<Entry>();
		FileReader fr;
		try {
			fr = new FileReader(path);
			BufferedReader buff= new BufferedReader(fr);
			String line=null;
			while((line=buff.readLine())!=null) {
						Entry e =getEntry(line);
						res.add(e);
					}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	public Date getDate(String data,int id ) {
		StringTokenizer st = new StringTokenizer(data,"/");
		Calendar c =Calendar.getInstance();
		Date res = new Date();
		int num=st.countTokens();
		if(num!=6) {
			System.out.println("errore nel parsing nel timpestamp dell'entry: "+id);
		}
		else {
			int day = Integer.parseInt(st.nextToken());
			int month = Integer.parseInt(st.nextToken());
			int year = Integer.parseInt(st.nextToken());
			int hour = Integer.parseInt(st.nextToken());
			int minute= Integer.parseInt(st.nextToken());
			int second = Integer.parseInt(st.nextToken());
			c.set(year, month, day, hour, minute, second);	
			res = c.getTime();
		}
		return res;
	}
	
	
   public Entry getEntry(String line) {
	   Entry e = new Entry();
	   StringTokenizer stentry = new StringTokenizer(line,";");
	   int count=stentry.countTokens();
		if(count!=5) {
			System.out.println("errore nel formato della entry \n ");
		}
		else {
		int id=Integer.parseInt(stentry.nextToken());
	    int	idutente=Integer.parseInt(stentry.nextToken());
	    String datacompl=stentry.nextToken();
	    Date ts= getDate(datacompl,id);
	    String tipo=stentry.nextToken();
	    String operazione=stentry.nextToken();
	    e.setIdEntry(id);
	    e.setIdUtente(idutente);
	    e.setTimestamp(ts);
	    e.setTipo(tipo);
	    e.setOperazione(operazione);	    
       }
	  return e;
   }
   

	public List<Entry> convertToEntry(List<Object> oggetti){
			List<Entry> res= new ArrayList<Entry>();
			for(Object o :oggetti) {
				if(o instanceof Entry) {
				Entry e=(Entry)o;
				res.add(e);}
			}
			return res;
		}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
