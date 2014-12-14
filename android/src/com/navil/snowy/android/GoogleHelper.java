package com.navil.snowy.android;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import com.google.example.games.basegameutils.GameHelper.GameHelperListener;
import com.navil.snowy.SnowyGame;
import com.navil.snowy.util.GoogleActions;

public class GoogleHelper implements GameHelperListener {

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		Log.e("SignIn","Failed");
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		Log.e("SignIn","Succeeded");
		if(SnowyGame.googleAction.equals(GoogleActions.OPENSCOREBOARD)){
			SnowyGame.actionResolver.showScores();
		}else if(SnowyGame.googleAction.equals(GoogleActions.UPLOADSCORE)){
			SnowyGame.actionResolver.submitPreviousScore();
		}
	}

}
