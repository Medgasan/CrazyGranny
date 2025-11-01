package com.neoof.ridinghood.catcher.enums;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.neoof.gdx.lib.actors.Particulas;
import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.gdx.lib.actors.Puntos;
import com.neoof.gdx.lib.actors.elements.Direccion;
import com.neoof.gdx.lib.actors.ui.Boton;
import com.neoof.lib.engine.Sonidos;
import com.neoof.ridinghood.catcher.personajes.Jugador;

public enum TipoObjetoLobo implements ObjetosInterface {
	// enum(sking, frames, anim, puntos, velocidad, controles, direccion)
	BOTA("bota.png", 1, 1, 2, 1.5f, false, false),
	CALABAZA("calabaza.png", 1,	1, 5, 1f, false, false),
	CERVEZA("cerveza.png", 1, 1, 7, 1f, false, true),
	COCTEL("coctel.png", 1, 1, 10, 1f, false, true),
	PIEDRA("piedra.png", 1, 1, 20, 1f, true, true);

	private String skin;
	private int valor;
	private int frames;
	private int anim;
	private float velocidad;
	private boolean direccion;
	private boolean controles;
	private final static DuenoObjetos TIPO_OBJETO = DuenoObjetos.LOBO;
	private Particulas particulas;

	private TipoObjetoLobo(String skin, int frames, int anim, int valor,
			float velocidad, boolean controles, boolean direccion) {
		this.skin = skin;
		this.valor = valor;
		this.frames = frames;
		this.anim = anim;
		this.velocidad = velocidad;
		this.controles = controles;
		this.direccion = direccion;
		this.particulas = new Particulas("danger.p","");
	}

	public String getSkin() {
		return skin;
	}

	public int getValor() {
		return valor;
	}

	public int getFrames() {
		return frames;
	}

	public int getAnim() {
		return anim;
	}

	public DuenoObjetos getName() {
		return TIPO_OBJETO;
	}

	public void afectar(Personaje personaje) {
		if (personaje instanceof Jugador) {
			Sonidos.playSonido("fuiii.mp3");
			final Jugador c = (Jugador) personaje;
			c.setDireccion(Direccion.ABAJO);
			c.setY(Jugador.POS_Y_INI);
			Puntos.getInstance().addPuntos(-valor);
		}
	}

	@Override
	public ObjetosInterface[] valores() {
		return values();
	}

	@Override
	public Particulas getParticulas() {
		return particulas;
	}

}
