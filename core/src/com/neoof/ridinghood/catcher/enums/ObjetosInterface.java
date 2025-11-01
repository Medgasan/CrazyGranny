package com.neoof.ridinghood.catcher.enums;

import com.neoof.gdx.lib.actors.Particulas;
import com.neoof.gdx.lib.actors.Personaje;


public interface ObjetosInterface {

	public String getSkin();
	public int getValor();
	public int getFrames();
	public int getAnim();
	public Particulas getParticulas();
	public void afectar(Personaje personaje);
	public DuenoObjetos getName();
	public ObjetosInterface[] valores();

}
