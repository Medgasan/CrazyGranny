package com.neoof.ridinghood.catcher.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.neoof.gdx.lib.actors.Particulas;
import com.neoof.gdx.lib.actors.Personaje;
import com.neoof.gdx.lib.actors.Puntos;
import com.neoof.gdx.lib.actors.elements.Direccion;
import com.neoof.gdx.lib.actors.ui.Boton;
import com.neoof.gdx.lib.actors.ui.Mensaje;
import com.neoof.gdx.lib.utils.Console;
import com.neoof.gdx.lib.utils.EstadosJuego;
import com.neoof.gdx.lib.utils.Inet;
import com.neoof.lib.engine.Sonidos;
import com.neoof.lib.engine.colisiones.NeoColisiones;
import com.neoof.ridinghood.catcher.Main;
import com.neoof.ridinghood.catcher.enums.DuenoObjetos;
import com.neoof.ridinghood.catcher.enums.EnumBarreras;
import com.neoof.ridinghood.catcher.personajes.Abuela;
import com.neoof.ridinghood.catcher.personajes.Barrera;
import com.neoof.ridinghood.catcher.personajes.Jugador;
import com.neoof.ridinghood.catcher.personajes.Lobo;
import com.neoof.ridinghood.catcher.personajes.ObjetoDrop;

/**
 * Clase que incluye la dinamica de juego.
 * 
 * @author MedGasan (Francisco Medina)
 * @version 27/01/2015
 */

public class Juego extends ScreensGeneric {

	private static final int PLATAFORMA_LOBO_X = 100;
	private static final int PLATAFORMA_ABUELA_X = -275;
	private Image plataforma1;
	private Image plataforma2;
	private Abuela abuela;
	private Lobo lobo;
	private Boton sonido;
	private Boton musica;
	private Boton botonDerecho;
	private Boton botonIzquierdo;
	private Jugador jugador;
	private NeoColisiones colisiones;
	private Barrera abuelaBarrera0;
	private Barrera abuelaBarrera1;
	private Barrera abuelaBarrera2;
	private Barrera abuelaBarrera3;
	private Barrera loboBarrera1;
	private Barrera loboBarrera0;
	private Barrera loboBarrera2;
	private Barrera loboBarrera3;
	private Barrera capeBarrera0;
	private Barrera capeBarrera1;
	private Barrera capeBarrera2;
	private Barrera capeBarrera3;
	private EstadosJuego gameStatus = EstadosJuego.GENERAL;
	private float timeline;
	private Barrera suelo;
	private Mensaje touchMensaje;
	private Boton pausa;
	private boolean newrecord;
	private Particulas particulasShot;
	private boolean partidaEmpezada = false;

	public Juego(Main juego) {
		super(juego);
	}

	/**
	 * M?todo que que ejecuta cuando se muestra la pantalla del Juego
	 */
	@Override
	public void show() {
		
		Console.log("Comienzo metodo show");
		newrecord = false;

		plataforma1 = new Image(
				new Texture(Gdx.files.internal("platform.png")));
		plataforma1.setName("plataformaAbuela");
		plataforma1.setPosition(PLATAFORMA_ABUELA_X, 662);
		gameLayer.addActor(plataforma1);

		plataforma2 = new Image(
				new Texture(Gdx.files.internal("platform.png")));
		plataforma2.setName("plataformaLobo");
		plataforma2.setPosition(PLATAFORMA_LOBO_X, 854);
		gameLayer.addActor(plataforma2);

		Puntos.getInstance().configure("numbers.png", 3);

		Puntos.getInstance().setPosition(255, 1035);
		gameLayer.addActor(Puntos.getInstance());

		iniciaPersonajes();
		iniciaBarreras();
		inicializaBotones();
		iniciaColisiones();
		gameStatus = EstadosJuego.PREINICIO;
	}

	/**
	 * Inicializa los personajes del juego
	 */
	private void iniciaPersonajes() {
		Console.log("Personajes");
		lobo = new Lobo();
		gameLayer.addActor(lobo);

		abuela = new Abuela();
		gameLayer.addActor(abuela);

		jugador = new Jugador();
		gameLayer.addActor(jugador);

	}

	/**
	 * Inicializa las elementosColisionCaperucita con las que colisionan los
	 * persnajes en su desplazamiento
	 */
	private void iniciaBarreras() {
		Console.log("Barreras");
		for (EnumBarreras bar : EnumBarreras.values()) {
			bar.setCanDroped(DuenoObjetos.NEMO);
		}
		capeBarrera0 = new Barrera("capeBarrera0", 125, jugador.getY());
		capeBarrera0.setEnum(EnumBarreras.BARRERA0);
		gameLayer.addActor(capeBarrera0);

		capeBarrera1 = new Barrera("capeBarrera1", 281, jugador.getY());
		capeBarrera1.setEnum(EnumBarreras.BARRERA1);
		gameLayer.addActor(capeBarrera1);

		capeBarrera2 = new Barrera("capeBarrera2", 437, jugador.getY());
		capeBarrera2.setEnum(EnumBarreras.BARRERA2);
		gameLayer.addActor(capeBarrera2);

		capeBarrera3 = new Barrera("capeBarrera3", 593, jugador.getY());
		capeBarrera3.setEnum(EnumBarreras.BARRERA3);
		gameLayer.addActor(capeBarrera3);

		abuelaBarrera0 = new Barrera("abuelaBarrera0", 125, abuela.getY());
		abuelaBarrera0.setEnum(EnumBarreras.BARRERA0);
		gameLayer.addActor(abuelaBarrera0);

		abuelaBarrera1 = new Barrera("abuelaBarrera1", 281, abuela.getY());
		abuelaBarrera1.setEnum(EnumBarreras.BARRERA1);
		gameLayer.addActor(abuelaBarrera1);

		abuelaBarrera2 = new Barrera("abuelaBarrera2", 437, abuela.getY());
		abuelaBarrera2.setEnum(EnumBarreras.BARRERA2);
		gameLayer.addActor(abuelaBarrera2);

		abuelaBarrera3 = new Barrera("abuelaBarrera3", 593, abuela.getY());
		abuelaBarrera3.setEnum(EnumBarreras.BARRERA3);
		gameLayer.addActor(abuelaBarrera3);

		loboBarrera0 = new Barrera("loboBarrera0", 125, lobo.getY());
		loboBarrera0.setEnum(EnumBarreras.BARRERA0);
		gameLayer.addActor(loboBarrera0);

		loboBarrera1 = new Barrera("loboBarrera1", 281, lobo.getY());
		loboBarrera1.setEnum(EnumBarreras.BARRERA1);
		gameLayer.addActor(loboBarrera1);

		loboBarrera2 = new Barrera("loboBarrera2", 437, lobo.getY());
		loboBarrera2.setEnum(EnumBarreras.BARRERA2);
		gameLayer.addActor(loboBarrera2);

		loboBarrera3 = new Barrera("loboBarrera3", 593, lobo.getY());
		loboBarrera3.setEnum(EnumBarreras.BARRERA3);
		gameLayer.addActor(loboBarrera3);

		suelo = new Barrera("Suelo", "suelo.png", 0, 140, 1, 1) {
			@Override
			public void colision(Personaje personaje2) {
				super.colision(personaje2);
				if (personaje2 instanceof ObjetoDrop) {
					switch (((ObjetoDrop) personaje2).getDueno()) {
						case ABUELA :
							//TODO: modo debug 
							gameStatus = EstadosJuego.PREGAMEOVER;
							break;
						default :
							break;
					}

					personaje2.dead();
				}

			}
		};
		gameLayer.addActor(suelo);
	}

	/**
	 * Instancia la clase GameColisions e inicia las acciones de colision
	 */
	private void iniciaColisiones() {
		Console.log("NeoColisiones");

		jugador.addElementosColision();
		abuela.addElementosColision();
		lobo.addElementosColision();

		colisiones = new NeoColisiones(gameLayer);
	}

	/**
	 * Inicializa los Botones del Juego
	 */
	private void inicializaBotones() {

		Console.log("inicializaBotones");
		sonido = new Boton("sound.png", 620, 1017) {
			@Override
			public void onTouch() {
				sonido.setActivo(!getActivo());
				game.setSonido(sonido.getActivo());
			}
		};
		sonido.setSize(91, 109);
		sonido.setActivo(game.getSonido());
		gameLayer.addActor(sonido);

		musica = new Boton("music.png", 506, 1017) {
			@Override
			public void onTouch() {
				musica.setActivo(!getActivo());
				game.setMusica(musica.getActivo());
			}
		};
		musica.setSize(91, 109);
		musica.setActivo(game.getMusica());
		gameLayer.addActor(musica);

		pausa = new Boton("pause.png", -355, 1017) {
			@Override
			public void onTouch() {
				pausa.setActivo(!getActivo());
			}
		};
		pausa.setSize(91, 109);
		gameLayer.addActor(pausa);

		botonDerecho = new Boton("panel.png", 360, 0) {
			@Override
			public void onTouch() {
				if (botonDerecho.getActivo()) {
					jugador.animado(true);
					jugador.setDireccion(Direccion.DERECHA);
					jugador.setDesplaza(true);
				}
			}
		};
		botonDerecho.setHasEffect(false);
		botonDerecho.setHasSonido(false);
		botonDerecho.setDelayRun(0);
		botonDerecho.setDelay(0.02f);
		botonDerecho.setName("botonDerecho");
		gameLayer.addActor(botonDerecho);

		botonIzquierdo = new Boton("panel.png", -280, 0) {
			@Override
			public void onTouch() {
				if (botonIzquierdo.getActivo()) {
					jugador.animado(true);
					jugador.setDesplaza(true);
					jugador.setDireccion(Direccion.IZQUIERDA);
				}
			}
		};

		botonIzquierdo.setHasEffect(false);
		botonIzquierdo.setHasSonido(false);
		botonIzquierdo.setDelayRun(0);
		botonIzquierdo.setDelay(0.02f);
		botonIzquierdo.setName("botonIzquierdo");
		gameLayer.addActor(botonIzquierdo);

		stage.addCaptureListener(new InputListener() {
			@Override
			public boolean keyDown(InputEvent event, int keycode) {
				switch (keycode) {
					case Input.Keys.CONTROL_LEFT :
					case Input.Keys.LEFT :
						botonIzquierdo.meTouchDown();
						break;
					case Input.Keys.CONTROL_RIGHT :
					case Input.Keys.RIGHT :
						botonDerecho.meTouchDown();
						break;
					default :
						break;
				}
				return super.keyDown(event, keycode);
			}
		});
	}

	/**
	 * Called when the screen should render itself. Incluye un switch que
	 * selecciona la rutina a ejecutar seg?n el estado del juego.
	 */
	@Override
	public void render(float delta) {

		switch (gameStatus) {

			case PREINICIO :
				gamePreInicio();
				break;

			case INICIO :
				gameInicio();
				break;

			case PREJUEGO :
				gamePreJuego();
				break;

			case JUEGO :
				if (!pausa.getActivo()) {
					gameStatus = EstadosJuego.PAUSE;
				}
				gameJuego();
				break;

			case PAUSE :
				Gdx.graphics.setContinuousRendering(pausa.getActivo());
				Sonidos.pauseMusica(pausa.getActivo());
				if (pausa.getActivo()) {
					gameStatus = EstadosJuego.JUEGO;
				}

				break;

			case PREGAMEOVER :
				gamePreGameOver();
				break;

			case GAMEOVER :
				gameGameOver();
				break;

			case REINICIO :
				gameReinicio();
				break;

			case GENERAL :
				break;

			default :
				break;
		}

		if (pausa instanceof Boton) {
			if (pausa.getActivo()) {
				super.render(delta);
			}
		} else {
			super.render(delta);
		}
		

	}

	/**
	 * M?todo de preparaci?n al Inicio del Juego
	 */
	private void gamePreInicio() {

		Puntos.getInstance().setPuntos(game.getPuntos());
		abuela.setX(-150);
		abuela.setDireccion(Direccion.DERECHA);
		abuela.setDesplaza(false);
		lobo.setX(800);
		lobo.setDireccion(Direccion.IZQUIERDA);
		lobo.setDesplaza(false);
		jugador.setX(-150);
		jugador.setDireccion(Direccion.DERECHA);
		jugador.setDesplaza(false);
		lobo.animado(true);
		abuela.animado(true);
		jugador.animado(true);
		lobo.setVelocidad(2f);
		abuela.setVelocidad(1.7f);
		jugador.setVelocidad(2f);
		timeline = 0;
		plataforma1.setX(PLATAFORMA_ABUELA_X);
		plataforma2.setX(PLATAFORMA_LOBO_X);
		if (partidaEmpezada) {
			Sonidos.playMusica("reintro.mp3", 0, false);
			gameStatus = EstadosJuego.REINICIO;
		} else {
			Sonidos.playMusica("intro.mp3", 0, false);
			gameStatus = EstadosJuego.INICIO;
		}
	}

	/**
	 * Bucle inicio del Juego
	 */
	private void gameInicio() {

		botonDerecho.setActivo(false);
		botonIzquierdo.setActivo(false);
		backLayer.findActor("suelo").setPosition(0, 0);
		backLayer.findActor("arboles").setPosition(50, 135);
		backLayer.findActor("fondo").setPosition(10, 150);
		

		switch (MathUtils.floor(timeline)) {

			case 0 :
				abuela.setDesplaza(true);
				lobo.setDesplaza(true);
				jugador.setDesplaza(true);
				break;

			case 4 :
				lobo.setVelocidad(2.5f);
				abuela.setVelocidad(2.5f);
				jugador.setVelocidad(2.5f);
				lobo.setDesplaza(false);
				abuela.setDesplaza(false);
				jugador.setDesplaza(false);
				lobo.setDireccion(Direccion.ABAJO);
				abuela.setDireccion(Direccion.ABAJO);
				jugador.setDireccion(Direccion.ABAJO);
				break;

			case 5 :
				lobo.setDireccion(Direccion.IZQUIERDA);
				abuela.setDireccion(Direccion.IZQUIERDA);
				jugador.setDireccion(Direccion.IZQUIERDA);
				break;

			case 6 :
				lobo.setDireccion(Direccion.ARRIBA);
				abuela.setDireccion(Direccion.ARRIBA);
				jugador.setDireccion(Direccion.ARRIBA);
				break;

			case 7 :
				lobo.setDireccion(Direccion.DERECHA);
				abuela.setDireccion(Direccion.DERECHA);
				jugador.setDireccion(Direccion.DERECHA);
				break;

			case 8 :
				lobo.setDireccion(Direccion.ABAJO);
				abuela.setDireccion(Direccion.ABAJO);
				jugador.setDireccion(Direccion.ABAJO);
				break;
			case 9 :
				lobo.animado(false);
				abuela.animado(false);
				jugador.animado(false);
				touchMensaje = new Mensaje("ready2.png", 0, 1300);
				gameLayer.addActor(touchMensaje);

				touchMensaje.addAction(Actions.parallel(
						Actions.moveTo(0, 0, 2.5f, Interpolation.pow5Out)));

				gameStatus = EstadosJuego.PREJUEGO;
				break;
		}

		timeline += Gdx.graphics.getDeltaTime();
	}

	/**
	 * Método de Preparación al Juego
	 */
	private void gamePreJuego() {
		if (Gdx.input.justTouched()) {
			particulasShot = new Particulas("starshot.p", "");
			gameLayer.addActor(particulasShot);
			Puntos.getInstance().reset();
			//Puntos.getInstance().setPuntos(900);
			Sonidos.playMusica("play_loop.mp3");
			touchMensaje.remove();
			pausa.addAction(Actions.moveTo(33, pausa.getY(), 1f,
					Interpolation.pow5Out));
			Timer.schedule(new Task() {

				@Override
				public void run() {
					botonDerecho.setActivo(true);
					botonIzquierdo.setActivo(true);
					abuela.gameMode();
					lobo.gameMode();
					jugador.gameMode();
					gameStatus = EstadosJuego.JUEGO;
				}
			}, 0.5f);

		}
	}

	/**
	 * Bucle del Juego en si mismo
	 */
	private void gameJuego() {

		colisiones.act();
		botonDerecho.toFront();
		botonIzquierdo.toFront();

		if (!newrecord
				&& (Puntos.getInstance().getPuntos() > game.getPuntos())) {
			particulasShot.setPosition(Puntos.getInstance().getX() + 70,
					Puntos.getInstance().getY() + 107);
			particulasShot.start();
			Sonidos.playSonido("newrecord.mp3");
			newrecord = true;
		} else if (newrecord
				&& (Puntos.getInstance().getPuntos() < game.getPuntos())) {
			Sonidos.playSonido("newrecordback.mp3");
			newrecord = false;
		}

		// establece la dificultad del juego
		float puntos = (float) Puntos.getInstance().getPuntos();
		game.getPlayServices().unlockAchievementByPointsGPGS((int) puntos);
		
		// TODO: Regular velocidad de juego
		float c = MathUtils.floor(puntos / 100);
		float du = puntos - (c * 100);
		//float velocidad_abuela = _VELOCIDAD_BASE_PERSONAJES * (du / 80) + c / 4 + 4;
		float velocidad_abuela = Abuela.VEL_INI + (du * du) / (500 - (c * 30));
		int porcentage_lobo =  MathUtils.floor(30 + 0.40f * du);
		//float delay = (3 + c) / ((c + 1) * 10);
		float delay = -0.04389f * c + 0.4f;
		//Console.log(delay);
		
		abuela.setVelocidad(velocidad_abuela);
		abuela.setDelaySeconds(delay);

		lobo.setPorcentajeSotarObjeto(porcentage_lobo);
		lobo.setDelaySeconds(delay);
	}

	/**
	 * Metodo que prepara el game over
	 */
	private void gamePreGameOver() {

		if (Puntos.getInstance().getPuntos() > game.getPuntos()) {
			Particulas p = new Particulas("star.p", "star.png");
			p.setPosition(Puntos.getInstance().getX() + 70,
					Puntos.getInstance().getY() + 107);
			gameLayer.addActor(p);
			p.start();
			game.setPuntos(Puntos.getInstance().getPuntos());
			/*
			Puntos.getInstance().addAction(Actions.forever(Actions.sequence(
					Actions.moveBy(0f, -50f, .5f, Interpolation.pow5Out),
					Actions.moveBy(0f, 50f, .5f, Interpolation.pow5Out))));
			*/
			game.getPlayServices()
			.submitScoreGPGS(Puntos.getInstance().getPuntos());
		}
		pausa.addAction(
				Actions.moveTo(-355, pausa.getY(), 1f, Interpolation.pow5In));
		Sonidos.stopMusica();
		Timer.schedule(new Task() {

			@Override
			public void run() {
				Sonidos.playMusica("final.mp3");
			}
		}, 1.2f);

		// Paramos Juego y eliminamos elementos no necesarios del Juego
		lobo.dead();
		botonDerecho.remove();
		botonIzquierdo.remove();
		plataforma1.remove();
		plataforma2.remove();
		abuela.dead();

		/*
		 * lobo.setVisible(false); botonDerecho.setVisible(false);
		 * botonIzquierdo.setVisible(false); plataforma1.setVisible(false);
		 * plataforma2.setVisible(false); abuela.setVisible(false);
		 */

		// a?adimos al Lobo del mensaje
		Personaje lob = new Personaje("wolf.png", 459f, 1382f);
		lob.setDireccion(Direccion.ABAJO);
		lob.animado(true);
		lob.setVelocidad(3f);
		lob.scaleBy(0.4f);
		gameLayer.addActor(lob);
		lob.addAction(Actions.parallel(
				Actions.moveTo(459f, 815, 2.5f, Interpolation.pow5Out)));

		// a?adimos mensaje GameOver
		Mensaje gameOverMensaje = new Mensaje("gameover.png", 78, 1300);
		gameLayer.addActor(gameOverMensaje);
		gameOverMensaje.addAction(Actions.parallel(
				Actions.moveTo(78, 673, 2.5f, Interpolation.pow5Out)));

		// a?adimos Boton Play
		final Boton play = new Boton("play.png", 201f, 418f) {
			@Override
			public void onTouch() {
				partidaEmpezada = true;
				game.jugar();

			}
		};
		play.setScale(0);
		gameLayer.addActor(play);
		Timer.schedule(new Task() {

			@Override
			public void run() {
				Sonidos.playSonido("bounce.mp3");
				play.addAction(
						Actions.scaleTo(1, 1, 1f, Interpolation.elasticOut));
			}
		}, 3f);

		// a?adimos animaci?n de GameOver a Jugador
		Sonidos.playSonido("dead.mp3");
		jugador.setDesplaza(false);
		jugador.animado(false);
		jugador.setDireccion(Direccion.DERECHA);
		jugador.addAction(Actions
				.sequence(
						Actions.parallel(
								Actions.rotateTo(90f, 0.05f,
										Interpolation.pow5Out),
								Actions.moveBy(0, 60, 0.1f)),
						Actions.moveBy(0, -80, 0.1f)));
		backLayer.moveBy(2, 2);
		backLayer
		.addAction(Actions.moveBy(-2, -2, 1f, Interpolation.bounceOut));

		// a?adimos el boton de rateit
		final Boton rateit = new Boton("rateit.png", 410, 46) {

			@Override
			public void onTouch() {
				Inet.open(
						"market://details?id=com.neoof.ridinghood.catcher.android");
			}
		};
		rateit.setScale(0);
		gameLayer.addActor(rateit);
		Timer.schedule(new Task() {

			@Override
			public void run() {
				Sonidos.playSonido("bounce.mp3");
				rateit.addAction(
						Actions.scaleTo(1, 1, 1.5f, Interpolation.elasticOut));

			}
		}, 1.5f);

		final Boton back = new Boton("back.png", -400, 38) {

			@Override
			public void onTouch() {
				partidaEmpezada = false;
				game.menu();

			}
		};
		gameLayer.addActor(back);
		Timer.schedule(new Task() {

			@Override
			public void run() {
				back.addAction(Actions.moveTo(33f, back.getY(), 2f,
						Interpolation.pow5Out));
			}
		}, 5f);

		// Establecemos el estado del Juego a Game Over
		gameStatus = EstadosJuego.GAMEOVER;

	}

	/**
	 * Bucle de GameOver
	 */
	private void gameGameOver() {
		jugador.animado(false);
		jugador.setDireccion(Direccion.DERECHA);
		jugador.setDesplaza(false);
		
		// Espera a que se pulse el boton de Play o BackToMenu
	}

	/**
	 * Bucle Reinicio del Juego
	 */

	private void gameReinicio() {
		abuela.setX(222.2996f);
		lobo.setX(362f);
		jugador.setX(288);
		lobo.setDireccion(Direccion.ABAJO);
		abuela.setDireccion(Direccion.ABAJO);
		jugador.setDireccion(Direccion.ABAJO);
		timeline = 9;
		gameStatus = EstadosJuego.INICIO;
	}

	/**
	 * M?todo que se ejecuta cuando ocultamos esta pantalla.
	 */
	@Override
	public void hide() {
		Sonidos.stopMusica();
		gameLayer.clear();
	}

	@Override
	public void onBackKeyPressed() {
		game.menu();
	}

}
