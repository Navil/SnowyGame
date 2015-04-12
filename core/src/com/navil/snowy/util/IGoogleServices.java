package com.navil.snowy.util;

public interface IGoogleServices {
	public void signIn();
	public void signOut();
	public void rateGame();
	public void submitScore(int score);
	public void showScores();
	public void showAchievements();
	public boolean isSignedIn();
	public void showOrLoadInterstital();
	public void showToast(String text);
	public void hideToast();
}
