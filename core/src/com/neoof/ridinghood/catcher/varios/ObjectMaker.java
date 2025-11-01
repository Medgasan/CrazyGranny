package com.neoof.ridinghood.catcher.varios;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pools;
import com.neoof.ridinghood.catcher.enums.ObjetosInterface;
import com.neoof.ridinghood.catcher.enums.TipoObjetoAbuela;
import com.neoof.ridinghood.catcher.enums.TipoObjetoLobo;
import com.neoof.ridinghood.catcher.enums.TipoPersonajes;
import com.neoof.ridinghood.catcher.personajes.ObjetoDrop;

public final class ObjectMaker {

	public ObjectMaker() {
	}

	public static ObjetoDrop dropObjeto(TipoPersonajes personaje, float x,
			float y) {

		ObjetosInterface enumerador = TipoObjetoAbuela.values()[0];

		switch (personaje) {
			case ABUELA :
				enumerador = TipoObjetoAbuela.values()[0];
				break;
			case LOBO :
				enumerador = TipoObjetoLobo.values()[0];
				break;
			default :
				break;
		}

		ObjetosInterface objeto = enumerador.valores()[MathUtils
				.random(enumerador.valores().length - 1)];
		
		ObjetoDrop objetoDrop = Pools.obtain(ObjetoDrop.class);
		objetoDrop.init(objeto, x, y);
		
		return objetoDrop;
	}
}
