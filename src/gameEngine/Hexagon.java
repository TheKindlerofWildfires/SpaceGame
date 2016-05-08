package gameEngine;

import java.util.ArrayList;

import GUI.Vector3f;
import graphicEngine.*;

public class Hexagon extends GameObject{
	public float WIDTH = 0.05f;
	public float HEIGHT = 0.25f;
	private VertexArrayObject vao;
	public Vector3f position;
	public double apothem = 0.03;
	//do some real math and remove rounding error--simons job
	public float w = (float) (0.4*apothem);
	public float h = (float) (Math.sqrt(3)/2*apothem);
	public float l = (float) (0.8*apothem);
	
	public int xIndex;
	public int yIndex;
	float[] vertices = {
			w, h, 0.0f, //upper right 0
			w, -h, 0.0f,//upper left 1
			l, 0.0f, 0.0f, //right	2
			-l, 0.0f, 0.0f, //left 3
			-w, -h, 0.0f, //lower left 4
			-w, h, 0.0f, //lower right 5
			0.0f, 0.0f, 0.0f //center 6
			}; 
	public byte[] indices = new byte[]{6,1,2,6,3,4,6,0,2,6,0,5,6,1,4,6,3,5}; //most inefficient hexagon ever
	//all this can do is triangles
	public Hexagon(){
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(this.vertices, this.indices);
		this.vaoID = vao.getVaoID();
	}
	public boolean checkBounds(){
		return false;
	}
	public void update(){
	}
}
