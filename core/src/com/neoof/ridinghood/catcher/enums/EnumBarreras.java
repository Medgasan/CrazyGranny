package com.neoof.ridinghood.catcher.enums;

public enum EnumBarreras {
	BARRERA0(DuenoObjetos.NEMO,0), BARRERA1(DuenoObjetos.NEMO,0), BARRERA2(DuenoObjetos.NEMO,0), BARRERA3(DuenoObjetos.NEMO,0);

	private DuenoObjetos okToDrop;
	private int objetos;

	private EnumBarreras(DuenoObjetos okToDrop, int obj) {
		this.okToDrop = okToDrop;
		objetos = obj;
	}

	public DuenoObjetos getCanDroped() {
		return this.okToDrop;
	}

	public void setCanDroped(DuenoObjetos candroped) {
		this.okToDrop = candroped;
	}
	
	public int addObjeto() {
		objetos++;
		return objetos;
	}
	
	public int removeObjeto() {
		objetos--;
		return objetos;
	} 

	
}
