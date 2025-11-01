package com.neoof.ridinghood.catcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Interpolation.Pow;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pools;
import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.gdx.lib.actors.elements.Direccion;
import com.neoof.gdx.lib.actors.ui.Boton;
import com.neoof.gdx.lib.actors.ui.Mensaje;
import com.neoof.gdx.lib.utils.Inet;
import com.neoof.lib.engine.Sonidos;
import com.neoof.lib.engine.colisiones.NeoColisiones;
import com.neoof.ridinghood.catcher.Main;
import com.neoof.ridinghood.catcher.enums.TipoPersonajes;
import com.neoof.ridinghood.catcher.personajes.Abuela;
import com.neoof.ridinghood.catcher.personajes.Jugador;
import com.neoof.ridinghood.catcher.personajes.Lobo;

public class Menu extends ScreensGeneric {

	private static final float _BASE_BUTTONS = 230f;
	private final float MENU_SCALE_AMOUNT = 0.02f;
	private final float MENU_SCALE_DURATION = 0.75f;
	private final Pow MENU_SCALE_INTERPOLATION = Interpolation.pow2;
	private final int RANDOM = 200;
	private final int DIRECCION = 2;
	private final float SUELO = 150;
	private final int VELOCIDADES = 2;
	private final int LIVE_TIME = 10;

	private Mensaje title;
	private Personaje cap;
	private Boton showLeaderboardIntent, showArchievements, play, sound, music,
			twitter, facebook;
	private NeoColisiones colisiones;
	private Boton musicby;
	private Boton atribucion;

	public Menu(Main juego) {
		super(juego);
	}

	@Override
	public void show() {
		colisiones = new NeoColisiones(gameLayer) {
			@Override
			public void hasColision(Personaje personaje1,
					Personaje personaje2) {
				if (personaje1.getTag() == "abuela") {
					if (personaje2.getTag() == "lobo") {
						personaje1
								.setVelocidad(personaje1.getVelocidad() * 1.1f);
						personaje2
								.setVelocidad(personaje2.getVelocidad() * 1.1f);
						personaje1.setCanColision(false);
						personaje2.setCanColision(false);
						if (personaje1.getDireccion() == Direccion.DERECHA) {
							personaje1.setDireccion(Direccion.IZQUIERDA);
							personaje2.setDireccion(Direccion.DERECHA);
						} else {
							personaje1.setDireccion(Direccion.DERECHA);
							personaje2.setDireccion(Direccion.IZQUIERDA);
						}
					}
				}
			}
		};

		// añadimos mensaje de attribucion
		atribucion = new Boton("attrib.png", 0, -500) {
			@Override
			public void onTouch() {
				clearActions();
				atribucion.addAction(Actions.moveTo(atribucion.getX(),
						gameLayer.getHeight() + 500, 2f));
			}
		};
		atribucion.setHasSonido(false);
		atribucion.setDelay(0f);
		gameLayer.addActor(atribucion);

		// añadimos boton de atribucion
		musicby = new Boton("musicby.png", 425, 27) {
			@Override
			public void onTouch() {
				atribucion.setPosition(atribucion.getX(), -500);
				atribucion.addAction(Actions.moveTo(atribucion.getX(),
						gameLayer.getHeight() + 500, 25f));
			}
		};
		gameLayer.addActor(musicby);

		// Aï¿½adimos el titulo
		title = new Mensaje("title.png", 44f, 756f);
		title.addAction(Actions.forever(Actions.sequence(
				Actions.scaleBy(MENU_SCALE_AMOUNT, MENU_SCALE_AMOUNT,
						MENU_SCALE_DURATION, MENU_SCALE_INTERPOLATION),
				Actions.scaleBy(-MENU_SCALE_AMOUNT, -MENU_SCALE_AMOUNT,
						MENU_SCALE_DURATION, MENU_SCALE_INTERPOLATION))));
		gameLayer.addActor(title);

		// aï¿½adimos boton twitter
		twitter = new Boton("twitter.png", 600f, 1002f) {
			@Override
			public void onTouch() {
				Inet.open("https://twitter.com/neoofcom");
			}
		};
		gameLayer.addActor(twitter);

		// aï¿½adimos boton facebook
		facebook = new Boton("facebook.png", 498f, 1002f) {
			@Override
			public void onTouch() {
				Inet.open("https://www.facebook.com/neoofcom");
			}
		};
		gameLayer.addActor(facebook);

		// aï¿½adimos a Jugador grande
		cap = new Personaje("redriding.png", 208f, 581f);
		cap.setDireccion(Direccion.ABAJO);
		cap.animado(true);
		cap.setVelocidad(3f);
		cap.scaleBy(0.4f);
		gameLayer.addActor(cap);

		// Aï¿½adimos el boton de Play
		play = new Boton("play.png", 201f, 418f) {
			@Override
			public void onTouch() {
				game.jugar();

			}
		};
		gameLayer.addActor(play);

		// Aï¿½adimos el boton de Scores
		showLeaderboardIntent = new Boton("leaderboard.png", 50f,
				_BASE_BUTTONS) { // 55f, 137f
			@Override
			public void onTouch() {
				game.getPlayServices().showLeaderboardGPGS();
			}
		};
		gameLayer.addActor(showLeaderboardIntent);

		// Aï¿½adimos botï¿½n Logros
		showArchievements = new Boton("archievements.png", 207f,
				_BASE_BUTTONS) {
			@Override
			public void onTouch() {
				game.getPlayServices().showAchievementsGPGS();
			}
		};
		gameLayer.addActor(showArchievements);

		// aï¿½adimos boton sonido
		sound = new Boton("sound.png", 555f, _BASE_BUTTONS) {
			@Override
			public void onTouch() {
				sound.setActivo(!sound.getActivo());
				game.setSonido(sound.getActivo());
			}
		};
		gameLayer.addActor(sound);
		sound.setActivo(game.getSonido());

		// aï¿½adimos boton musica
		music = new Boton("music.png", 398f, _BASE_BUTTONS) {
			@Override
			public void onTouch() {
				music.setActivo(!music.getActivo());
				game.setMusica(music.getActivo());
			}
		};
		gameLayer.addActor(music);
		music.setActivo(game.getMusica());

		// Aï¿½adimos mï¿½sica
		Sonidos.playMusica("music1.mp3");
		Sonidos.hasMusic(music.getActivo());
		Sonidos.hasSounds(sound.getActivo());

	}

	private void addRandPersonaje() {
		addPersonaje(
				TipoPersonajes.values()[
                        MathUtils.random(TipoPersonajes.values().length	- 1)
                        ]
				,
				MathUtils.random(DIRECCION - 1),
				MathUtils.random(VELOCIDADES - 1));
	}

	private void addPersonaje(TipoPersonajes personaje, int direccion, int velocidades) {
		
		Direccion hacia = Direccion.IZQUIERDA;
		float x = stage.getViewport().getWorldWidth() + 10;
		float velocidad = 5f;
		final Personaje actor;

		switch (personaje) {
			case ABUELA:
				actor = (Abuela) Pools.obtain(personaje.getClase());
				break;
			case LOBO:
				actor = (Lobo) Pools.obtain(personaje.getClase());
				break;
			default:
				personaje = TipoPersonajes.CAZADOR;
				actor = (Jugador) Pools.obtain(personaje.getClase());

				break;
			
		}
		
		switch (direccion) {
			case 0 :
				// hacia = Direccion.IZQUIERDA;
				// x = stage.getViewport().getWorldWidth() + 10;
				break;
			case 1 :
				hacia = Direccion.DERECHA;
				x = -stage.getViewport().getWorldWidth() / 5 - 100;

				break;
		}

		switch (velocidades) {
			case 0 :
				velocidad = MathUtils.random(2, 4);
				break;
			case 1 :
				velocidad = MathUtils.random(5, 8);
				break;
			case 2 :
				velocidad = MathUtils.random(9, 12);
				break;
		}
		actor.setSprite(personaje.getSkin(), personaje.getFrames(), personaje.getAnim());
		actor.setColisionArea(0.7f);
		actor.setPosition(x, SUELO);
		actor.animado(true);
		actor.setDesplaza(true);
		actor.setDireccion(hacia);
		actor.setLiveTime(LIVE_TIME);
		actor.setVelocidad(velocidad);
		actor.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				actor.setVelocidad(actor.getVelocidad() * 2);
				if (actor.getDireccion() == Direccion.DERECHA) {
					actor.setDireccion(Direccion.IZQUIERDA);
				} else {
					actor.setDireccion(Direccion.DERECHA);
				}

				return super.touchDown(event, x, y, pointer, button);
			}
		});
		gameLayer.addActor(actor);

	}

	@Override
	public void render(float delta) {
		// mantenemos el mï¿½todo render
		// si queremos actualizar algo antes de mostrar la pantalla
		// Hay que escribirlo ante de Super.render(delta)
		colisiones.act();
		if (Gdx.input.justTouched()) {
			// addPersonaje();
		}
		if (MathUtils.random(RANDOM) < 1) {
			addRandPersonaje();
		}
		atribucion.toFront();
		super.render(delta);
		
	}

	@Override
	public void hide() {
		gameLayer.clear();
		Sonidos.stopMusica();
	}

	private void exit() {
		Sonidos.dispose();
		stage.clear();
		Gdx.app.exit();
	}

	@Override
	public void onBackKeyPressed() {
		exit();
	}

}
