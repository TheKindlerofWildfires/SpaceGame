package graphicEngine;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_BGR;
import static org.lwjgl.opengl.GL15.glBindBuffer;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import maths.Utilities;

public class Texture {
	private int width;
	private int height;
	private ByteBuffer image;
	private int ID;
	
	public void loadImage(String filepath){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		//		for(int i=0;i<pixels.length;i++){
		//			System.out.println(pixels[i]&255);
		//		}
		width = img.getWidth();
		height = img.getHeight();
		image = Utilities.createByteBuffer(pixels);
	}
	
	public void glTheFool(){
		ID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, ID);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_BGR, GL_UNSIGNED_BYTE, image);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glBindBuffer(GL_TEXTURE_2D, 0);
	}
	
	public void start(){
		glBindTexture(GL_TEXTURE_2D, ID);
	}
	
	public void stop(){
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
