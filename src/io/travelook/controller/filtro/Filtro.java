
package io.travelook.controller.filtro;
import java.util.List;

public interface Filtro {
   
	public List<Object> filtra(Object[] ogg,List<Object> o);
	public Object[] getFiltri();
}
