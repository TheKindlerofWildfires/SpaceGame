package graphicEngine;

import java.util.ArrayList;
import java.util.List;

import maths.Vector3f;
//this is a class that holds models, they have vertices, normals and faces
public class Model {
	public List<Vector3f> vertices = new ArrayList<Vector3f>();
	public List<Vector3f> normals = new ArrayList<Vector3f>();
	public List<Face> faces = new ArrayList<Face>();
	public Model(){
		
	}
}
