package com.neoof.riddinghood.catcher.desktop;

import java.util.ArrayList;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.neoof.google.playservices.ILeaderboardScoreFacade;
import com.neoof.google.playservices.INeoofGooglePlayApi;
import com.neoof.ridinghood.catcher.Main;

public class DesktopLauncher implements INeoofGooglePlayApi {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		boolean hd = false;
		float scaler = 1f;		
		
		config.samples = 8;
		
		//config.foregroundFPS = 120; 
		config.vSyncEnabled = false;
		
		config.width = (int) (480 * scaler);
		config.height = (int) (800 * scaler);
		
		if (hd) {
			config.width = (int) (720 / scaler);
			config.height = (int) (1280 / scaler);
		}
		
		new LwjglApplication(new Main(new DesktopLauncher()), config);
	}

	@Override
	public boolean getSignedInGPGS() {
		return false;
	}

	@Override
	public void loginGPGS() {
	}

	@Override
	public void submitScoreGPGS(int score) {
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
	}

	@Override
	public void showLeaderboardGPGS() {
	}

	@Override
	public void showAchievementsGPGS() {
	}

	@Override
	public void showMsg(String text, int time) {
	}

	@Override
	public void unlockAchievementByPointsGPGS(int puntos) {
		
	}

	@Override
	public ArrayList<ILeaderboardScoreFacade> getLeaderboardGPGS() {
		return null;
	}


}
