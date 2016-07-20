package graphicEngine;

import maths.Vector3f;

public class Face {
	//I dont really understand what this does, but I think it holds verteices and normals
	public Vector3f vertex = new Vector3f();
	public Vector3f normal = new Vector3f();
	public Face(Vector3f vertex, Vector3f normal){
		this.vertex = vertex;
		this.normal = normal;
	}
}
