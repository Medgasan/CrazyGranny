package com.neoof.ridinghood.catcher.varios;

import com.badlogic.gdx.math.MathUtils;
import com.neoof.gdx.lib.actors.elements.Direccion;

public final class GameUtils {

	
	private GameUtils() {
	}

	public static Direccion getRandDireccion() {
		Direccion direccion;
		switch (MathUtils.random(1)) {
			case 0 :
				direccion = Direccion.DERECHA;
				break;

			default :
				direccion = Direccion.IZQUIERDA;
				break;
		}
		return direccion;
	}

	
}
