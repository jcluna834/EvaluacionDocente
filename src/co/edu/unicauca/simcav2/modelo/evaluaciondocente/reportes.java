package co.edu.unicauca.simcav2.modelo.evaluaciondocente;

import java.util.ArrayList;

import co.edu.unicauca.simcav2.modelo.Bean;

public class reportes implements Bean{
	
	private String nombreDocente;
	
	public String getNombreDocente() {
		return nombreDocente;
	}

	public void setNombreDocente(String nombreDocente) {
		this.nombreDocente = nombreDocente;
	}

	private double consolidado;

	public double getConsolidado() {
		return consolidado;
	}

	public void setConsolidado(double consolidado) {
		this.consolidado = consolidado;
	}

}
