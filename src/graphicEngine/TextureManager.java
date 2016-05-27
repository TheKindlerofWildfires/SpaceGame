package graphicEngine;

public class TextureManager {

	public static Texture texture = new Texture();
	
	
	public static void loadImages(){
		texture.loadImage("/Users/Simon/Desktop/texture.png");
	}
	
	public static void glTheFools(){
		texture.glTheFool();
	}
	

}
