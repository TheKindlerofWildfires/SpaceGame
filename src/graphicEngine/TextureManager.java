package graphicEngine;

public class TextureManager {

	public static Texture texture = new Texture();
	
	
	public static void loadImages(){
		texture.loadImage("resources/colors.png");
	}
	
	public static void glTheFools(){
		texture.glTheFool();
	}
	

}