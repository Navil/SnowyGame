package com.navil.snowy.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	
	private static Assets INSTANCE;
    private static AssetManager manager;
    private static Skin menuSkin;

    public Assets(){
    	manager = new AssetManager();  
    }
    
    // In here we'll put everything that needs to be loaded in this format:
    // manager.load("file location in assets", fileType.class);
    // 
    // libGDX AssetManager currently supports: Pixmap, Texture, BitmapFont,
    //     TextureAtlas, TiledAtlas, TiledMapRenderer, Music and Sound.
    public void queueLoading() {
        manager.load("skin/myPack.pack", TextureAtlas.class);
    }

    //In here we'll create our skin, so we only have to create it once.
    public void setMenuSkin() {
        if (menuSkin == null)
            menuSkin = new Skin(Gdx.files.internal("skin/skin.json"),
                    manager.get("skin/myPack.pack", TextureAtlas.class));
    }
    
    public Skin getSkin(){
    	return menuSkin;
    }
    // This function gets called every render() and the AssetManager pauses the loading each frame
    // so we can still run menus and loading screens smoothly
    public boolean update() {
        return manager.update();
    }
    public void dispose(){
    	manager.clear();
    	menuSkin = null;
    	Assets.INSTANCE = null;
    }
    public static Assets getInstance()
    {
    	if(Assets.INSTANCE == null)
    		Assets.INSTANCE = new Assets();
        return INSTANCE;
    }

}