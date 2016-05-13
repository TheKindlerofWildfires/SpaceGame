package GUI;

public class Matrix4f {
	
	//stores matricies as single array of floating point elements
	
	public static final int SIZE = 4* 4;
	public  float[] matrix = new float [SIZE];
	
	public Matrix4f(){
	}
	public static Matrix4f identity(){
		Matrix4f returnMatrix = new Matrix4f();
		//Identity Matrix
		// [1, 0, 0, 0]
		// [0, 1, 0, 0]
		// [0, 0, 1, 0]
		// [0, 0, 0, 1]
		returnMatrix.matrix[0+0*4] = 1.0f;
		returnMatrix.matrix[1+1*4] = 1.0f;
		returnMatrix.matrix[2+2*4] = 1.0f;
		returnMatrix.matrix[3+3*4] = 1.0f;
		
		return returnMatrix;
	}
	public static Matrix4f translate(Vector3f vector){
		Matrix4f returnMatrix = identity();
		returnMatrix.matrix[0+3 *4] = vector.x;
		returnMatrix.matrix[1+3 *4] = vector.y;
		returnMatrix.matrix[2+3 *4] = vector.z;
		return returnMatrix;
		//learn to matrix translate
	}
	public static Matrix4f rotate(float angle){
		Matrix4f returnMatrix = identity();
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);
		//rotates around z axis--< prolly want to make it y
		returnMatrix.matrix[0+0*4] = cos;
		returnMatrix.matrix[1+0*4] = sin;
		returnMatrix.matrix[0+1*4] = -sin;
		returnMatrix.matrix[1+1*4] = cos;
		return returnMatrix;
	}
	public Matrix4f multiply(Matrix4f mat4f){
		Matrix4f result = new Matrix4f();
		for(int x = 0; x<4; x++){
			for(int y = 0; y<4; y++){
				float sum = 0.0f;
				for(int e = 0; e<4; e++){
					sum += this.matrix[x+e*4]*mat4f.matrix[y *4];
				}
				result.matrix[x+y *4] = sum;
			}
		}
		return result;
	}
}