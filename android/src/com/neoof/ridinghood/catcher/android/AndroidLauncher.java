package com.neoof.ridinghood.catcher.android;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.neoof.google.playservices.android.NeoofGooglePlayApi;
import com.neoof.ridinghood.catcher.Main;

public class AndroidLauncher extends AndroidApplication {

	NeoofGooglePlayApi nGoogleApi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// ca-app-pub-9508761809917307/9078881678
		super.onCreate(savedInstanceState);
		nGoogleApi = new NeoofGooglePlayApi(this);
		initAdSense();
		initGooglePlayServices();
	}

	private void initGooglePlayServices() {
		SparseArray<String> logros = new SparseArray<String>();
		logros.put(100, getString(R.string.achievement_level_1));
		logros.put(200, getString(R.string.achievement_level_2));
		logros.put(300, getString(R.string.achievement_level_3));
		logros.put(400, getString(R.string.achievement_level_4));
		logros.put(500, getString(R.string.achievement_level_5));
		logros.put(600, getString(R.string.achievement_level_6));
		logros.put(700, getString(R.string.achievement_level_7));
		logros.put(800, getString(R.string.achievement_level_8));
		logros.put(900, getString(R.string.achievement_level_9));

		nGoogleApi.setLeaderBoardId(getString(R.string.leaderboard_rankings));
		nGoogleApi.setArvievementsKeys(logros);
		nGoogleApi.init();
	}

	private void initAdSense() {

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.numSamples = 2;
		config.maxSimultaneousSounds = 10;
		// initialize(new Main(), config);

		// ------------
		RelativeLayout layout = new RelativeLayout(this);

		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		// Create the libgdx View
		View gameView = initializeForView(new Main(nGoogleApi), config);

		// Add the libgdx view
		layout.addView(gameView);

		// Add Adds View
		AdView adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-9508761809917307/9078881678");
		adView.setAdSize(AdSize.SMART_BANNER);

		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.setMargins(0, 0, 0, 0);

		layout.addView(adView, adParams);

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		setContentView(layout);

	}

	@Override
	public void onStart() {
		super.onStart();
		nGoogleApi.onStart();
	}

	@Override
	public void onStop() {
		log("AGA", "On Stop fired..........");
		nGoogleApi.onStop();
		super.onStop();
		//finish();
	}


}
