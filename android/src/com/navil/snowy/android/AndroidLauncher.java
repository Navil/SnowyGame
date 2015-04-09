package com.navil.snowy.android;

import java.util.concurrent.TimeUnit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Api.a;
import com.google.android.gms.common.api.Api.c;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.GameHelper;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.android.R;
import com.navil.snowy.util.IGoogleServices;

public class AndroidLauncher extends AndroidApplication implements
		IGoogleServices {

	private static final String AD_UNIT_ID_INTERSTITIAL = "ca-app-pub-2578016131001286/7549728250";
	private InterstitialAd interstitialAd;
	private GameHelper gameHelper;
	private int localScore;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);

		GameHelperListener gameHelperListener = new GoogleHelper();
		gameHelper.setMaxAutoSignInAttempts(0);
		gameHelper.setup(gameHelperListener);

		setupInterstitial();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		//super.hideStatusBar(true);
		initialize(new SnowyGame(this), config);

	}

	@Override
	protected void onStart() {
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				// @Override
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e) {
			Log.e("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}

	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				// @Override
				public void run() {
					gameHelper.signOut();
				}
			});
		} catch (Exception e) {
			Log.e("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		// TODO Auto-generated method stub

	}

	@Override
	public void submitScore(int score) {
		// the parameter is -1 when the login succeeded and the score is about to upload
		if(score != -1)
			this.localScore = score;
		else
			score = localScore;
		if (isSignedIn() == true) {
			Games.Leaderboards.submitScore(gameHelper.getApiClient(),
					getString(R.string.leaderboard_id), score);
			unlockAchievement(score);
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
					gameHelper.getApiClient(),
					getString(R.string.leaderboard_id)), 9002);
		} else {
			signIn();
		}
	}
	
	public void unlockAchievement(int score){
		if(score > 0)
			Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_dodge_the_fire));	
		if(score > 2000)
			Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_its_something));	
		if(score > 4000)
			Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_silver_like_the_snow));	
		if(score > 6000)
			Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_gold_digger));	
		if(score > 10000)
			Games.Achievements.unlock(gameHelper.getApiClient(), getString(R.string.achievement_the_vulcano_is_out_of_fire));		
	}

	@Override
	public void showScores() {
		// TODO Auto-generated method stub
		if (isSignedIn() == true)
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(
					gameHelper.getApiClient(),
					getString(R.string.leaderboard_id)), 9002);
		else {
			signIn();
		}
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}

	public void setupInterstitial() {
		if (!SnowyGame.advertisement)
			return;
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AD_UNIT_ID_INTERSTITIAL);
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				Toast.makeText(getApplicationContext(),
						"Finished Loading Interstitial", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onAdClosed() {
				Toast.makeText(getApplicationContext(), "Closed Interstitial",
						Toast.LENGTH_SHORT).show();
				showOrLoadInterstital();
				// ((Game)Gdx.app.getApplicationListener()).setScreen(new
				// GameScreen());
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
						Toast.makeText(getApplicationContext(),
								"Showing Interstitial", Toast.LENGTH_SHORT)
								.show();
					} else {
						AdRequest interstitialRequest = new AdRequest.Builder()
								.build();
						interstitialAd.loadAd(interstitialRequest);
						Toast.makeText(getApplicationContext(),
								"Loading Interstitial", Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		} catch (Exception e) {
		}
	}

}
