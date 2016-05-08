package gameEngine;

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
	public float h = (float) (1.0*apothem);
	public float l = (float) (1.0*apothem);
	float[] vertices = {
			w, h, 0.0f, //0
			l, 0.0f, 0.0f, //1
			w, -h, 0f,//2
			-w, -h, 0.0f, //3
			-l, 0.0f, 0.0f, //4
			-w, h, 0.0f //5
			};
	public byte[] indices = new byte[]{0,1,2,5,4,3,0,2,3,5,0,2,5,0,3}; //wow this is ugly
	//something about this drawls the points
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
