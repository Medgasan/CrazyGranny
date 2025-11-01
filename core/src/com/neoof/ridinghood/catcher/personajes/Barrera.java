package com.neoof.ridinghood.catcher.personajes;

import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.lib.engine.colisiones.INeoColisionListener;
import com.neoof.ridinghood.catcher.enums.EnumBarreras;

public class Barrera extends Personaje implements INeoColisionListener{

	private final static String SKING = "action.png";
	private int valor = 1;
	private EnumBarreras enumBarrera;

	public Barrera(String tag, float posX, float posY) {
		this(tag, SKING, posX, posY, 1, 1);
	}

	public Barrera(String tag, String file, float posX, float posY) {
		super(tag, file, posX, posY, 1, 1);
		init();
	}

	public Barrera(String tag, String file, float posX, float posY, int frames,
			int anim) {
		super(tag, file, posX, posY, frames, anim);
		init();
	}

	public Barrera(String tag, String file, float posX, float posY, int frames,
			int anim, int valor) {
		super(tag, file, posX, posY, frames, anim);
		this.valor = valor;
		init();
	}

	private void init() {
		run();
	}

	@Override
	public void colision(Personaje personaje2) {
	}

	public boolean colision() {
		return false;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public void run() {
	}

	public void setEnum(EnumBarreras enumBarrera) {
		this.enumBarrera = enumBarrera;
	}

	public EnumBarreras getEnum() {

		return enumBarrera;
	}

	@Override
	public void addElementosColision() {
		
	}

}
