package com.neoof.ridinghood.catcher.screens;

import java.util.Arrays;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.neoof.gdx.lib.utils.Console;
import com.neoof.gdx.lib.utils.NeoofUtils;
import com.neoof.lib.engine.NeoofGlobal;
import com.neoof.lib.engine.NeoStage;
import com.neoof.ridinghood.catcher.Main;
import com.neoof.ridinghood.catcher.personajes.Nube;

public abstract class ScreensGeneric implements Screen {

	protected Main game;
	protected NeoStage stage;
	protected Group gameLayer;
	protected Group backLayer;
	private Texture fondoTexture;
	private Texture arbolesTexture;
	private Texture sueloTexture;

	public ScreensGeneric(Main pantalla) {
		this.game = pantalla;
		this.stage = NeoofGlobal.getStage();
		this.gameLayer = NeoofGlobal.getGameLayer();
		this.backLayer = NeoofGlobal.getBackLayer();

		if (backLayer.findActor("suelo") != null) {
			return;
		}
		initScreen();
	}

	@Override
	public abstract void show();

	public abstract void onBackKeyPressed();
	
	
	private void initScreen() {
        Gdx.input.setCatchBackKey(true);
		// Añadimos el fondo
		fondoTexture = new Texture(Gdx.files.internal("background2.png"), true);
		fondoTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		Image fondo = new Image(fondoTexture);
		fondo.setScale(2, 2);
		fondo.setPosition(10, 150);
		fondo.setName("fondo");
		backLayer.addActor(fondo);
		arbolesTexture = new Texture(Gdx.files.internal("background1.png"),
				true);
		arbolesTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		Image arboles = new Image(arbolesTexture);
		arboles.setPosition(50, 135);
		arboles.setName("arboles");
		backLayer.addActor(arboles);
		sueloTexture = new Texture(Gdx.files.internal("background0.png"), true);
		sueloTexture.setFilter(TextureFilter.MipMapLinearLinear, TextureFilter.Linear);
		Image suelo = new Image(sueloTexture);
		suelo.setPosition(0, 0); // 100, 0
		suelo.setName("suelo");
		backLayer.addActor(suelo);
		Console.log(Arrays.toString((backLayer.getChildren().items)));
		for (int x = 5; x < MathUtils.random(6, 8); x++) {
			backLayer.addActor(Nube.crearNube("", MathUtils.random(720f),
					MathUtils.random(400f, 1200f)));
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.0f, .0f, .0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		efectoNubes();
		stage.act(delta);
		stage.draw();
		//NeoofUtils.getFPSLogger().log();
		if (Gdx.input.isKeyPressed(Keys.BACK)) {
			onBackKeyPressed();
		}
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void dispose() {
		game.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {
	}

	private void efectoNubes() {
		if (MathUtils.random(1000) < 1) {
			backLayer.addActor(Nube.crearNube());
		}
	}
	
}
