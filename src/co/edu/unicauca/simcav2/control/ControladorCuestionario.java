package co.edu.unicauca.simcav2.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import co.edu.unicauca.simcav2.modelo.evaluaciondocente.Cuestionario;
 
@ManagedBean
@ViewScoped
public class ControladorCuestionario implements Serializable {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 7878133280708313432L;
    private String tipo;  
    private Map<String,String> tipos;
    
    private ArrayList<Cuestionario> cuestionarios;    
    private ArrayList<Cuestionario> cuest;

	@PostConstruct
    public void init() {
    	
        setTipos(new HashMap<String, String>());
        getTipos().put("Tipo1", "Tipo1");
        getTipos().put("Tipo2", "Tipo2");
        getTipos().put("Tipo3", "Tipo3");
        
        Cuestionario c1 = new Cuestionario(1,"Cuestionario uno","Sistemas");
        Cuestionario c2 = new Cuestionario(2,"Cuestionario dos","Electronica");
        Cuestionario c3 = new Cuestionario(3,"Cuestionario tres","Ambiental");
        Cuestionario c4 = new Cuestionario(4,"Cuestionario cuatro","Sistemas");
        Cuestionario c5 = new Cuestionario(5,"Cuestionario cinco","Sistemas");
        Cuestionario c6 = new Cuestionario(6,"Cuestionario seis","Sistemas");
        
        cuestionarios = new ArrayList<Cuestionario>();
        cuestionarios.add(c1);
        cuestionarios.add(c2);
        cuestionarios.add(c3);
        cuestionarios.add(c4);
        cuestionarios.add(c5);
        cuestionarios.add(c6);   
           
              
    }

	
	public List<Cuestionario> getCuestionarios() {
		return cuestionarios;
	}

	public void setCuestionarios(ArrayList<Cuestionario> cuestionarios) {
		this.cuestionarios = cuestionarios;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Map<String,String> getTipos() {
		return tipos;
	}

	public void setTipos(Map<String,String> tipos) {
		this.tipos = tipos;
	}
	
	public ArrayList<Cuestionario> getCuest() {
		return cuest;
	}


	public void setCuest(ArrayList<Cuestionario> cuest) {
		this.cuest = cuest;
	}

}
