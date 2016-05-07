package gameEngine;

import GUI.KeyboardInput;
import GUI.Vector3f;
import graphicEngine.*;
import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameObject{
	public float WIDTH = 0.05f;
	public float HEIGHT = 0.25f;
	private VertexArrayObject vao;
	public Vector3f position;
	
	float[] vertices = {-0.0f, 0.25f, 0f,0.0f, 0.0f, 0f, 0.05f, 0.0f, 0f,0.05f, 0.25f, 0f};
	public byte[] indices = new byte[]{0,1,2,2,3,0};
	public Player(){
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
	}
	public boolean checkBounds(){
		return false;
	}
	public void update(){
		if(!checkBounds()){
		if(KeyboardInput.isKeyDown(GLFW_KEY_W)){
			position.y += 0.01f;
		}
		if(KeyboardInput.isKeyDown(GLFW_KEY_S)){
			position.y -= 0.01f;
		}
		if(KeyboardInput.isKeyDown(GLFW_KEY_A)){
			position.x -= 0.01f;
		}
		if(KeyboardInput.isKeyDown(GLFW_KEY_D)){
			position.x += 0.01f;
		}
	}
	}
}
