package io.travelook.controller.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import io.travelook.controller.filtro.Filtro;
import io.travelook.model.Entry;

public class Logger implements ILogger{
    
	

    private File file ; 
	private String path ;//percorso del file in cui scrivere i logs 
	
	public Logger() {
		super();
		// TODO Auto-generated constructor stub
	}
    
	public Logger(String path) {
		super();
		this.path = path;
		try {
			file = new File(path);
			if (file.exists())
			System.out.println("Il file " + path + " esiste");
			else if (file.createNewFile())
			System.out.println("Il file " + path + " è stato creato");
			else
			System.out.println("Il file " + path + " non può essere creato");
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	
	
	
	
	
	@Override
	public void scriviEntry(Entry e) {
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.append(e.toString());
			pw.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}

	@Override
	public List<Entry> visualizzaLog(Filtro filtro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entry> visualizzaLog() {
		List<Entry> res = new ArrayList<Entry>();
		FileReader fr;
		try {
			fr = new FileReader(file);
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
}
