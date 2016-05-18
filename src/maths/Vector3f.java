package maths;
//This does cords
public class Vector3f {
	public float x = 0.0f;
	public float y = 0.0f;
	public float z = 0.0f;
	
	public Vector3f(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Vector3f(){
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public void translate(Vector3f vec) {
		this.x += vec.x;
		this.y += vec.y;
		this.z += vec.z;
	}
}
