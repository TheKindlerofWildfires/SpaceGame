package entity;

import GUI.Vector3f;
import gameEngine.GameObject;
import graphicEngine.VertexArrayObject;

public class Monster extends GameObject{
	public Vector3f movement;
	private VertexArrayObject vao;
	public Vector3f position;
	
	public float WIDTH = 0.05f;
	public float HEIGHT = 0.05f;
	
	float[] vertices = {-0.0f, 0.05f, 0f,0.0f, 0.0f, 0f, 0.05f, 0.0f, 0f,0.05f, 0.05f, 0f};
	public byte[] indices = new byte[]{0,1,2,2,3,0};
	public Monster(){
		this.movement = new Vector3f(0.02f,0.015f,0.0f);
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
	}
	public void update(){
		if(!checkBounds()){
			this.position.translate(movement);
		}
	}
	public boolean checkBounds(){
		if (position.y <= -1.0f){
			//below
			position.y = -0.99f;
			movement.y *= -1.0f;
			return true;
		}
		if (position.y +HEIGHT >= 1.0f){
			//above
			position.y = 0.99f-HEIGHT;
			movement.y *= -1.0f;
			return true;
		}
		if (position.x +WIDTH >= 1.0f){
			//right
			position.x = 0.99f-WIDTH;
			movement.x *= -1.0f;
			return true;
		}
		if (position.x <= -1.0f){
			//left
			position.x = -0.99f;
			movement.x *= -1.0f;
			return true;
		}
		return false;
	}
}
