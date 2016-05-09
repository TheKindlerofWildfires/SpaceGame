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
	private double apothem = 0.03;
	private float w = (float) (0.4*apothem);
	private float h = (float) (Math.sqrt(3)/2*apothem);
	private float l = (float) (0.8*apothem);
	private long lastTime;
	float[] vertices = {
			w, h, 0.0f, //upper right 0
			w, -h, 0.0f,//upper left 1
			l, 0.0f, 0.0f, //right	2
			-l, 0.0f, 0.0f, //left 3
			-w, -h, 0.0f, //lower left 4
			-w, h, 0.0f, //lower right 5
			0.0f, 0.0f, 0.0f //center 6
			}; 
	public byte[] indices = new byte[]{6,1,2,6,3,4,6,0,2,6,0,5,6,1,4,6,3,5};
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
		if(canMove()){
			if(!checkBounds()){
				float dis = 0.0069f;
				if(KeyboardInput.isKeyDown(GLFW_KEY_Q)){//why is this jumpy?
					for(int x = 0; x<10;x++){
					position.x -= (float)(dis*0.579);
					position.y += (float)(dis*Math.sqrt(3)/4);
					}
				}
				if(KeyboardInput.isKeyDown(GLFW_KEY_W)){
					for(int x = 0; x<10;x++){
						position.y += (float)(dis*Math.sqrt(3)/2);
						}
				}
				if(KeyboardInput.isKeyDown(GLFW_KEY_E)){
					for(int x = 0; x<10;x++){
						position.x += (float)(dis*0.579);
						position.y += (float)(dis*Math.sqrt(3)/4);
						}
				}
				if(KeyboardInput.isKeyDown(GLFW_KEY_A)){
					for(int x = 0; x<10;x++){
						position.x -= (float)(dis*0.579);
						position.y -= (float)(dis*Math.sqrt(3)/4);
						}
				}
				if(KeyboardInput.isKeyDown(GLFW_KEY_S)){
					for(int x = 0; x<10;x++){
						position.y -= (float)(dis*Math.sqrt(3)/2);
						}
				}
				if(KeyboardInput.isKeyDown(GLFW_KEY_D)){
					for(int x = 0; x<10;x++){
						position.x += (float)(dis*0.579);
						position.y -= (float)(dis*Math.sqrt(3)/4);
						}
				}
				lastTime = System.nanoTime();
			}
		}
	}
	/*public void draw(){
		shaderManager.shader1.start();
		shaderManager.shader1.setUniform3f("pos",player.position);
		player.draw();	
		shaderManager.shader1.stop();
	}*/
	private boolean canMove() {
		long delta = System.nanoTime()-lastTime;
		if(delta<100000000){
			return false;
		}else{
			return true;
		}
	}
}
