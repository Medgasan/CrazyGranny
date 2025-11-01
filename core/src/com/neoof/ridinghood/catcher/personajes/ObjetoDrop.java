package com.neoof.ridinghood.catcher.personajes;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.gdx.lib.utils.Console;
import com.neoof.lib.engine.colisiones.INeoColisionListener;
import com.neoof.ridinghood.catcher.enums.DuenoObjetos;
import com.neoof.ridinghood.catcher.enums.EnumBarreras;
import com.neoof.ridinghood.catcher.enums.ObjetosInterface;

public class ObjetoDrop extends Personaje implements INeoColisionListener {

	ObjetosInterface objeto;
	private final float VEL_INI = 12f;
	private final float COLISION_INI = 1f;
	private EnumBarreras barrera;

	public ObjetoDrop() {
		super();
	}

	public void init(ObjetosInterface objeto, float x, float y) {
		this.objeto = objeto;
		setName(objeto.getSkin());
		setSprite(objeto.getSkin(), objeto.getFrames(), objeto.getAnim());
		setPosition(x, y);
		setVelocidad(VEL_INI);
		setColisionArea(COLISION_INI);
		if (objeto.getParticulas() != null) {
			objeto.getParticulas().setOrigin(getOriginX(), getOriginY());
			objeto.getParticulas().setPosition(37, 37);
			addActor(objeto.getParticulas());
		}
		setDesplaza(false);
		setScale(0.5f, 0.5f);

		addAction(
				Actions.parallel(
						Actions.sequence(
								Actions.parallel(
										Actions.moveBy(0, 150, 0.5f, Interpolation.pow2Out),
										Actions.scaleTo(1.01f, 1.01f, 0.5f)),
								Actions.moveBy(0, -(getY() + 500), 1.10f, Interpolation.pow2In)),
						Actions.forever(
								Actions.rotateBy(MathUtils.random(5f))
								)
						)
				);
		run();
	}

	@Override
	public void colision(Personaje personaje2) {

	}


	public void setObjeto(ObjetosInterface objeto) {
		this.objeto = objeto;
	}

	public boolean colision() {
		return false;
	}

	public void afectar(Personaje personaje) {
		objeto.afectar(personaje);
	}

	public void run() {
		animado(true);
		if (objeto.getParticulas() != null) {
			objeto.getParticulas().start();
			objeto.getParticulas().setVisible(true);
		}
	}


	@Override
	public void addElementosColision() {

	}

	public DuenoObjetos getDueno() {
		return objeto.getName();
	}

	public void setBarrera(EnumBarreras barrera) {
		this.barrera = barrera;
	}

	@Override
	public void dead() {
		for (EnumBarreras barrera1 : EnumBarreras.values()) {
			if (barrera1.equals(barrera)) {
				if (barrera1.removeObjeto() < 2) {
					barrera1.setCanDroped(DuenoObjetos.NEMO);
					Console.log("Objeto:", barrera1.getCanDroped());
				}
			}
		}
		super.dead();
	}

}
