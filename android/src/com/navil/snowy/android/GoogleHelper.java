package com.navil.snowy.android;

import android.util.Log;

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
			SnowyGame.actionResolver.submitScore(-1);
		}else if(SnowyGame.googleAction.equals(GoogleActions.OPENACHIEVEMENTS)){
			SnowyGame.actionResolver.showAchievements();
		}
	}

}
