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
		if (position.y <= -1.0f){
			//below
			position.y = -0.99f;
			return true;
		}
		if (position.y +HEIGHT >= 1.0f){
			//above
			position.y = 0.99f-HEIGHT;
			return true;
		}
		if (position.x +WIDTH >= 1.0f){
			//right
			position.x = 0.99f-WIDTH;
			return true;
		}
		if (position.x <= -1.0f){
			//left
			position.x = -0.99f;
			return true;
		}
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
