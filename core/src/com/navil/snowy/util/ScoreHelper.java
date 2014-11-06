package com.navil.snowy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class ScoreHelper {
	public static void saveLocalScore(int score){
		FileHandle file = Gdx.files.local("data/snowyLocals.sno");
		byte[] bytes = new byte[] {(byte) score};
		file.writeBytes(bytes, false);
	}
	
	public static int loadLocalScore(){
		FileHandle file = Gdx.files.local("data/snowyLocals.sno");
		if(!file.exists())
			return 0;
		byte[] bytes = file.readBytes();
		String value ="";
		for(int i =0;i<bytes.length;i++){
			value += bytes[i];
		}
		return Integer.parseInt(value);
	}
}
