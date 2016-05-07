package gameEngine;

import GUI.Vector3f;
import graphicEngine.*;

public class Monster extends GameObject{
	
	private VertexArrayObject vao;
	public Vector3f position;
	
	float[] vertices = {-0.0f, 0.05f, 0f,0.0f, 0.0f, 0f, 0.05f, 0.0f, 0f,0.05f, 0.05f, 0f};
	public byte[] indices = new byte[]{0,1,2,2,3,0};
	public Monster(){
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
	}
	public void update(){

	}
}
