package com.neoof.ridinghood.catcher.personajes;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.gdx.lib.actors.elements.Direccion;
import com.neoof.gdx.lib.utils.Console;
import com.neoof.lib.engine.colisiones.INeoColisionListener;
import com.neoof.ridinghood.catcher.enums.DuenoObjetos;
import com.neoof.ridinghood.catcher.enums.EnumBarreras;
import com.neoof.ridinghood.catcher.enums.TipoPersonajes;
import com.neoof.ridinghood.catcher.varios.GameUtils;
import com.neoof.ridinghood.catcher.varios.ObjectMaker;

public class Abuela extends Personaje implements INeoColisionListener {

	private final static TipoPersonajes personaje = TipoPersonajes.ABUELA;
	private final static String TAG = personaje.getTipo();
	private final static String SKIN = personaje.getSkin();
	private final static float POS_X_INI = 158;
	private final static float POS_Y_INI = 702;
	public final static float VEL_INI = 5f;
	private final float COLISION_INI = 0.3f;
	private Direccion direccion;
	private int porcentajeSoltarObjeto = 200;
	private Barrera barrera0;
	private Barrera barrera1;
	private Barrera barrera2;
	private Barrera barrera3;
	private float delaySeconds = 0.3f;
	private final static float VELOCIDAD_BASE_OBJETO = 2f;
	private float velocidadObjeto = 0f;
	private EnumBarreras barreraActual;

	public Abuela() {
		this(TAG, SKIN, POS_X_INI, POS_Y_INI);
	}

	public Abuela(String file, float posX, float posY) {
		super(file, posX, posY);
	}

	public Abuela(String tag, String file, float posX, float posY) {
		super(tag, file, posX, posY);
	}

	public Abuela(String tag, String file, float posX, float posY, int frames,
			int anim) {
		super(tag, file, posX, posY, frames, anim);
	}

	public void gameMode() {
		setVelocidad(VEL_INI);
		setColisionArea(COLISION_INI);
		setDireccion(GameUtils.getRandDireccion());
		run();
	}

	@Override
	public void colision(Personaje personaje2) {

		if (personaje2 instanceof Barrera) {

			Barrera bar = (Barrera) personaje2;
			barreraActual = bar.getEnum();
			setX(bar.getX() - getWidth()/2 );

			switch (barreraActual) {

				case BARRERA0 :
					barrera1.setCanColision(true);
					direccion = Direccion.DERECHA;
					personaje2.setCanColision(false);
					break;
				case BARRERA1 :
					barrera0.setCanColision(true);
					barrera2.setCanColision(true);
					direccion = GameUtils.getRandDireccion();
					personaje2.setCanColision(false);
					break;
				case BARRERA2 :
					barrera1.setCanColision(true);
					barrera3.setCanColision(true);
					direccion = GameUtils.getRandDireccion();
					personaje2.setCanColision(false);
					break;
				case BARRERA3 :
					barrera2.setCanColision(true);
					direccion = Direccion.IZQUIERDA;
					personaje2.setCanColision(false);
					break;
			}

			for (EnumBarreras barrera : EnumBarreras.values()) {
				if (barrera.equals(barreraActual)) {
					if (colision()) {
						barrera.setCanDroped(personaje.getDueno());
						//barreraActual.setCanDroped(personaje.getDueno());
					}
					
				}
			}


		}

	}

	public boolean colision() {

		boolean soltar = false;
		setDesplaza(false);
		animado(false);
		setDireccion(Direccion.ABAJO);
		soltar = soltarObjeto();
		Timer tm = new Timer();
		tm.scheduleTask(new Task() {

			@Override
			public void run() {
				setDireccion(direccion);
				animado(true);
				setDesplaza(true);
				setCanColision(true);
			}
		}, getDelaySeconds());

		return soltar;

	}

	public void setPorcentajeSotarObjeto(int porcentaje) {
		this.porcentajeSoltarObjeto = porcentaje;
	}

	public int getPorcentajeSotarObjeto() {
		return porcentajeSoltarObjeto;
	}

	public void setVelocidadObjeto(float velocidadObjeto) {
		this.velocidadObjeto = velocidadObjeto;
	}

	public boolean soltarObjeto() {
		boolean soltar = false;
		Console.log("Abuela",barreraActual.getCanDroped());
		if ((MathUtils.random(100) <= porcentajeSoltarObjeto)
				&& barreraActual.getCanDroped() != DuenoObjetos.LOBO) {
			ObjetoDrop oa = ObjectMaker.dropObjeto(personaje, getX() + 20, getY() - 15);
			oa.setBarrera(barreraActual);
			barreraActual.addObjeto();
			gameLayer.addActor(oa);
			soltar = true;
		}
		return soltar;
	}

	public void run() {
		animado(true);
		setDesplaza(true);
	}

	@Override
	public void addElementosColision() {

		barrera0 = (Barrera) gameLayer.findActor("abuelaBarrera0");
		barrera1 = (Barrera) gameLayer.findActor("abuelaBarrera1");
		barrera2 = (Barrera) gameLayer.findActor("abuelaBarrera2");
		barrera3 = (Barrera) gameLayer.findActor("abuelaBarrera3");

	}

	public float getDelaySeconds() {
		return delaySeconds;
	}

	public void setDelaySeconds(float delaySeconds) {
		this.delaySeconds = delaySeconds;
	}

}
