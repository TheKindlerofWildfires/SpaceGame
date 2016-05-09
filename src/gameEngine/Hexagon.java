package gameEngine;

import GUI.Vector3f;
import graphicEngine.*;

public class Hexagon extends GameObject{
	public static double apothem;
	public float WIDTH = 0.05f;
	public float HEIGHT = 0.25f;
	private VertexArrayObject vao;
	public Vector3f position;
	//public double apothem = 0.03;
	public int xIndex;
	public int yIndex;
	private boolean land;
	//do some real math and remove rounding error--simons job
	public Hexagon(double apothem){
		Hexagon.apothem =apothem;
		float w = (float) (0.4*apothem);
		float h = (float) (Math.sqrt(3)/2*apothem);
		float l = (float) (0.8*apothem);
		float[] vertices = {
				w, h, 0.0f, //upper right 0
				w, -h, 0.0f,//upper left 1
				l, 0.0f, 0.0f, //right	2
				-l, 0.0f, 0.0f, //left 3
				-w, -h, 0.0f, //lower left 4
				-w, h, 0.0f, //lower right 5
				0.0f, 0.0f, 0.0f //center 6
				}; 
		byte[] indices = new byte[]{6,1,2,6,3,4,6,0,2,6,0,5,6,1,4,6,3,5};
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(vertices, indices);
		this.vaoID = vao.getVaoID();
	}
	
	public boolean checkBounds(){
		return false;
	}
	
	public void update(){
	}
	
	public void setLand(boolean land){
		this.land = land;
	}	
	
	public boolean isLand(){
		return land;
	}
}
