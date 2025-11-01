package com.neoof.ridinghood.catcher.enums;

import com.neoof.ridinghood.catcher.personajes.Abuela;
import com.neoof.ridinghood.catcher.personajes.Jugador;
import com.neoof.ridinghood.catcher.personajes.Lobo;

public enum TipoPersonajes {
	ABUELA("abuela", "granny.png", Abuela.class, 3, 4, DuenoObjetos.ABUELA),
	LOBO("lobo", "wolf.png", Lobo.class, 3, 4, DuenoObjetos.LOBO),
	CAPERUCITA("jugador", "redriding.png", Jugador.class, 3, 4, DuenoObjetos.JUGADOR),
	CAZADOR("jugador","cazador.png", Jugador.class, 3, 4, DuenoObjetos.JUGADOR);

	private String tipo;
	private String skin;
	private Class<?> clase;
	private int frames;
	private int anim;
	DuenoObjetos duenoObjeto;

	TipoPersonajes(String tipo, String skin, Class<?> clase, int frames, int anim, DuenoObjetos duenoObjeto) {
		this.tipo = tipo;
		this.skin = skin;
		this.clase = clase;
		this.frames = frames;
		this.anim = anim;
		this.duenoObjeto = duenoObjeto;
	}

	public String getTipo() {
		return tipo;
	}

	public String getSkin() {
		return skin;
	}

	public Class<?> getClase() {
		return clase;
	}

	public int getFrames() {
		return frames;
	}

	public int getAnim() {
		return anim;
	}
	
	public DuenoObjetos getDueno() {
		return duenoObjeto;
	}

}
