package com.neoof.ridinghood.catcher.personajes;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pools;
import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.gdx.lib.actors.elements.Direccion;
import com.neoof.gdx.lib.utils.Console;
import com.neoof.lib.engine.NeoofGlobal;

public class Nube extends Personaje {

	static ArrayList<String> texturasNubes;
	private static final float ALTURA_MIN = 500f;
	private static final float ALTURA_MAX = 1200f;
	private static final float VELOCIDAD_DESPLAZAMIENTO_MIN = 0.05f;
	private static final float VELOCIDAD_DESPLAZAMIENTO_MAX = 0.2f;
	private static final float TIEMPO_DE_VIDA = 600;
	private static final float POSICION_X_INI = -200f;
	private static Nube nube;

	public static Nube crearNube() {

		nube = Pools.obtain(Nube.class);
		nube.setY(MathUtils.random(ALTURA_MIN, ALTURA_MAX));
		Console.log("creamos una nube");
		return nube;

	}

	public static Nube crearNube(String file, float x, float y) {

		file = file.length() > 0 ? file : "nube1.png";
		nube = Pools.obtain(Nube.class);
		nube.setSprite(file,1,1);
		nube.setPosition(x, y);
		Console.log("creamos una nube inicial " + x + " : " + y);
		return nube;

	}


	public Nube() {
		this("nube1.png", POSICION_X_INI, ALTURA_MIN);
	}

	public Nube(String file, float posX, float posY) {
		super("nube", file, posX, posY);
		init();
	}

	private void init() {
		texturasNubes = new ArrayList<String>();
		texturasNubes.add("nube1.png");
		texturasNubes.add("nube2.png");
		texturasNubes.add("nube3.png");
		String randTexture = texturasNubes
				.get(MathUtils.random(texturasNubes.size() - 1));
		this.setSprite(randTexture, 1, 1);
		scaleBy(-MathUtils.random(0f, 0.5f));
		animado(false);
		setVelocidad(MathUtils.random(VELOCIDAD_DESPLAZAMIENTO_MIN,
				VELOCIDAD_DESPLAZAMIENTO_MAX));
		setDesplaza(true);
		setDireccion(Direccion.DERECHA);
		setCanColision(false);
		setLiveTime(TIEMPO_DE_VIDA);
	}


	@Override
	protected void desplaza() {
		super.desplaza();
		if ((getX() + getWidth()) > NeoofGlobal.getStage().getWidth()) {
			dead();
		}
	};

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}

}
