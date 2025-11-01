package com.neoof.ridinghood.catcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.neoof.gdx.lib.utils.Console;
import com.neoof.google.playservices.INeoofGooglePlayApi;
import com.neoof.lib.engine.NeoStage;
import com.neoof.lib.engine.NeoVirtualViewport;
import com.neoof.lib.engine.Sonidos;
import com.neoof.ridinghood.catcher.screens.Juego;
import com.neoof.ridinghood.catcher.screens.LeaderBoard;
import com.neoof.ridinghood.catcher.screens.Menu;

public class Main extends Game {

	private final float W_WIDTH = 720;
	private final float W_HEIGHT = 1280;

	private NeoStage stage;

	protected Menu menu;
	protected Juego jugar;
	protected LeaderBoard showScores;
	private Preferences bd;
	protected INeoofGooglePlayApi playServices;

	public Main(INeoofGooglePlayApi playServices) {
		Console.off();
		//Console.on();
		this.playServices = playServices;

	}

	@Override
	public void create() {
		Gdx.graphics.setVSync(true);
		Sonidos.getInstance().newSonidos();
		Sonidos.loadAllSonidos("fx");
		Sonidos.loadAllMusicas("music");
		bd = Gdx.app.getPreferences("com.neoof.ridinghood.catcher.db");
		stage = new NeoStage(new NeoVirtualViewport(W_WIDTH, W_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		menu = new Menu(this);
		jugar = new Juego(this);
		showScores = new LeaderBoard(this);
		//resetPuntos();
		menu();
	}

	@Override
	public void pause() {
		super.pause();
	}

	public void jugar() {
		this.setScreen(jugar);
	}

	public void menu() {
		this.setScreen(menu);
	}

	public void scores() {
		this.setScreen(showScores);
	}

	public Preferences db() {
		return bd;
	}

	public void setSonido(boolean sonido) {
		Sonidos.hasSounds(sonido);
		db().putBoolean("sonido", sonido);
		db().flush();
	}

	public void setMusica(boolean musica) {
		Sonidos.hasMusic(musica);
		db().putBoolean("musica", musica);
		db().flush();
	}

	public boolean getSonido() {
		return db().getBoolean("sonido", true);
	}

	public boolean getMusica() {
		return db().getBoolean("musica", true);
	}

	public void setPuntos(int puntos) {
		db().putInteger("puntos", puntos);
		db().flush();
	}

	public void resetPuntos() {
		db().putInteger("puntos", 0);
		db().flush();
	}

	public int getPuntos() {
		return db().getInteger("puntos", 0);
	}

	public INeoofGooglePlayApi getPlayServices() {
		return playServices;
	}

	public void showMsg(String texto, int duracion) {
		playServices.showMsg(texto, duracion);

	}

	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
		stage.clear();
	}
	
}
