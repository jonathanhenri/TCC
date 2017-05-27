package com.mycompany.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FiltroDinamicoAgrupador implements Serializable {
	private static final long serialVersionUID = 1L;
	
	List<FiltroDinamicoAtributo> listaFiltroDinamicoAtributos;
	
	public FiltroDinamicoAgrupador() {
	}

	public FiltroDinamicoAgrupador(List<FiltroDinamicoAtributo> listaAtributos) {
	}

	public List<FiltroDinamicoAtributo> getListaFiltroDinamicoAtributos() {
		return listaFiltroDinamicoAtributos;
	}

	public void setListaFiltroDinamicoAtributos(
			List<FiltroDinamicoAtributo> listaFiltroDinamicoAtributos) {
		this.listaFiltroDinamicoAtributos = listaFiltroDinamicoAtributos;
	}
	
	public void addListaFiltroDinamico(FiltroDinamicoAtributo dinamicoAtributo){
		if(listaFiltroDinamicoAtributos==null){
			listaFiltroDinamicoAtributos = new ArrayList<FiltroDinamicoAtributo>();
		}
		
		listaFiltroDinamicoAtributos.add(dinamicoAtributo);
	}

}
