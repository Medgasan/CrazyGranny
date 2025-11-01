package com.neoof.ridinghood.catcher.enums;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.neoof.gdx.lib.actors.Particulas;
import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.gdx.lib.actors.Puntos;
import com.neoof.lib.engine.Sonidos;
import com.neoof.ridinghood.catcher.personajes.Jugador;

public enum TipoObjetoAbuela implements ObjetosInterface {
	// enum(sking, frames, anim, puntos, velocidad, controles, direccion)
	BESO("beso.png", 1, 1, 1, 1f, false, false),
	BROCOLI("brocoli.png", 1, 1, 1,	1f, false, false),
	HAMBURGUESA("hamburguesa.png", 1, 1, 1, 1f,	false, false),
	NARANJA("naranja.png", 1, 1, 1, 1f, false, false),
	PINA("pina.png", 1, 1, 1, 1f, false, false),
	ZANAHORIA("zanahoria.png", 1, 1, 1, 1f, false, false);

	private String skin;
	private int valor;
	private int frames;
	private int anim;
	private float velocidad;
	private boolean direccion;
	private boolean controles;
	private final static DuenoObjetos TIPO_OBJETO = DuenoObjetos.ABUELA;

	private TipoObjetoAbuela(String skin, int frames, int anim, int valor,
			float velocidad, boolean controles, boolean direccion) {
		this.skin = skin;
		this.valor = valor;
		this.frames = frames;
		this.anim = anim;
		this.velocidad = velocidad;
		this.controles = controles;
		this.direccion = direccion;
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

	@Override
	public ObjetosInterface[] valores() {
		return values();
	}

	@Override
	public void afectar(Personaje personaje) {
		if (personaje instanceof Jugador) {
			Sonidos.playSonido("getit.mp3");
			final Jugador c = (Jugador) personaje;

			Puntos.getInstance().addPuntos(valor);

			final float velocidadAnterior = c.getVelocidad();
			final Vector2 colisionArea = c.getColisionArea();
			//c.setColisionArea(c.getColisionArea().x * velocidad);
			//c.setVelocidad(velocidadAnterior * velocidad);
			/*
			Timer.schedule(new Task() {
				@Override
				public void run() {
					c.setColisionArea(colisionArea);
					c.setVelocidad(velocidadAnterior);
				}
			}, 3f);
			*/

			if (controles) {

				final Actor controlDerecho = ((Actor) c
						.findInGameLayer("botonDerecho"));
				final Actor controlIzquierdo = ((Actor) c
						.findInGameLayer("botonIzquierdo"));

				final float x_bd = controlDerecho.getScaleX();
				final float y_bd = controlDerecho.getScaleY();
				final float x_bi = controlIzquierdo.getScaleX();
				final float y_bi = controlIzquierdo.getScaleY();

				controlDerecho.setScale(x_bd * 1.05f, y_bd * 1.05f);
				controlIzquierdo.setScale(x_bi * 1.05f, y_bi * 1.05f);

				Timer.schedule(new Task() {
					@Override
					public void run() {
						controlDerecho.setScale(x_bd, y_bd);
						controlIzquierdo.setScale(x_bi, y_bi);
					}
				}, 3f);
			}

			if (direccion) {
				// c.setDireccion(GameUtils.getRandDireccion());
			}

		}

	}

	@Override
	public Particulas getParticulas() {
		return null;
	}

}
