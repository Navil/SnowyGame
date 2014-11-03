package com.navil.snowy.android;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.util.ActionResolver;

public class AndroidLauncher extends AndroidApplication implements ActionResolver {

	private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-2578016131001286/7549728250";
	private InterstitialAd interstitialAd;

	protected View gameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setupInterstitial();
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new SnowyGame(this), config);
		
	}

	public void setupInterstitial(){
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Toast.makeText(getApplicationContext(), "Finished Loading Interstitial", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onAdClosed() {
				Toast.makeText(getApplicationContext(), "Closed Interstitial", Toast.LENGTH_SHORT).show();
				showOrLoadInterstital();
				//((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
			}
		});
		showOrLoadInterstital();
	}
	
	public void showOrLoadInterstital() {
		try {
			runOnUiThread(new Runnable() {
				public void run() {
					if (interstitialAd.isLoaded()) {
						interstitialAd.show();
						Toast.makeText(getApplicationContext(), "Showing Interstitial", Toast.LENGTH_SHORT).show();
					} else {
						AdRequest interstitialRequest = new AdRequest.Builder().build();
						interstitialAd.loadAd(interstitialRequest);
						Toast.makeText(getApplicationContext(), "Loading Interstitial", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {
		}
	}
	public void uploadScore(){
		//Games.Leaderboards.submitScore(10);
	}
	

}
