package com.neoof.ridinghood.catcher.personajes;

import com.badlogic.gdx.utils.Array;
import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.gdx.lib.actors.elements.Direccion;
import com.neoof.gdx.lib.actors.ui.Boton;
import com.neoof.lib.engine.colisiones.INeoColisionListener;
import com.neoof.ridinghood.catcher.enums.EnumBarreras;
import com.neoof.ridinghood.catcher.enums.TipoPersonajes;

public class Cazador extends Personaje implements INeoColisionListener{

	private final static TipoPersonajes personaje = TipoPersonajes.CAZADOR;
	private final static String TAG = personaje.getTipo();
	private final static String SKIN = personaje.getSkin();
	private final static float POS_X_INI = 296;
	private final static float POS_Y_INI = 150;
	private final float VEL_INI = 10f;
	private final float COLISION_INI_W = 0.08f;
	private final float COLISION_INI_H = 1f;
	private Barrera barrera0;
	private Barrera barrera1;
	private Barrera barrera2;
	private Barrera barrera3;
	private float xanterior = 0;
	private EnumBarreras barreraActual;
	private boolean gameMode;
	private Boton botonIzquierdo;
	private Boton botonDerecho;

	public Cazador() {
		this(TAG, SKIN, POS_X_INI, POS_Y_INI);
	}

	public Cazador(String file, float posX, float posY) {
		super(file, posX, posY);
	}

	public Cazador(String tag, String file, float posX, float posY) {
		super(tag, file, posX, posY);
	}

	public Cazador(String tag, String file, float posX, float posY,
			int frames, int anim) {
		super(tag, file, posX, posY, frames, anim);

	}

	public void gameMode() {
		setVelocidad(VEL_INI);
		setColisionArea(COLISION_INI_W, COLISION_INI_H);
		gameMode = true;
	}

	@Override
	protected void desplaza() {

		if (getY() != POS_Y_INI) {
			setY(POS_Y_INI);
		}

		if (gameMode) {
			float desplazamiento = xanterior - getX();
			float desSuelo = desplazamiento / 2;
			float desArboles = desplazamiento / 10;
			float desFondo = desplazamiento / 100;

			backLayer.findActor("suelo").moveBy(desSuelo, 0);
			backLayer.findActor("arboles").moveBy(desArboles, 0);
			backLayer.findActor("fondo").moveBy(desFondo, 0);
			xanterior = getX();
		}

		super.desplaza();
	}

	@Override
	public void colision(Personaje personaje2) {

		if (personaje2 instanceof Barrera) {

			Barrera bar = (Barrera) personaje2;
			barreraActual = bar.getEnum();
			setX(bar.getX() - getWidth()/2 );
			
			if (barreraActual != null) {
				switch (barreraActual) {

					case BARRERA0 :
						botonIzquierdo.setActivo(false);
						barrera1.setCanColision(true);
						personaje2.setCanColision(false);
						break;
					case BARRERA1 :
						botonIzquierdo.setActivo(true);
						barrera0.setCanColision(true);
						barrera2.setCanColision(true);
						personaje2.setCanColision(false);
						break;
					case BARRERA2 :
						botonDerecho.setActivo(true);
						barrera1.setCanColision(true);
						barrera3.setCanColision(true);
						personaje2.setCanColision(false);
						break;
					case BARRERA3 :
						botonDerecho.setActivo(false);
						barrera2.setCanColision(true);
						personaje2.setCanColision(false);
						break;
				}
			}

			colision();
		}

		if (personaje2 instanceof ObjetoDrop) {
			ObjetoDrop oa = (ObjetoDrop) personaje2;
			personaje2.setDesplaza(false);
			personaje2.setVisible(false);
			personaje2.setPosition(-1000, 0);
			personaje2.setLiveTime(5f);
			oa.afectar(this);
			if (barreraActual != null) {
				barreraActual.setCanDroped(personaje.getDueno());
			}

		}

	}

	public boolean colision() {
		setDesplaza(false);
		setDireccion(Direccion.ARRIBA);
		animado(false);
		return false;
	}

	public void addBarreras(Array<Barrera> barreras) {

	}

	@Override
	public void addElementosColision() {

		barrera0 = (Barrera) findInGameLayer("capeBarrera0");
		barrera1 = (Barrera) findInGameLayer("capeBarrera1");
		barrera2 = (Barrera) findInGameLayer("capeBarrera2");
		barrera3 = (Barrera) findInGameLayer("capeBarrera3");
		botonDerecho = (Boton) findInGameLayer("botonDerecho");
		botonIzquierdo = (Boton) findInGameLayer("botonIzquierdo");

	}

}
