package graphicEngine;


import maths.Frustum;
import maths.Matrix4f;
import maths.Vector3f;

public class Camera {

	private Vector3f pos, target, up;
	private float angle, aspect, near, far;
	private double degX, degZ;
	private double sense = 0.1;
	private Matrix4f projection;

	private Frustum frust;

	/**
	 * Initializes camera
	 * 
	 * @param cameraPos
	 *            position of camera
	 * @param cameraTarget
	 *            location camera is looking at
	 * @param cameraUp
	 *            up vector
	 */
	public Camera(Vector3f cameraPos, Vector3f cameraTarget, Vector3f cameraUp,
			float angle, float aspect, float near, float far) {
		pos = cameraPos;
		target = cameraTarget;
		up = cameraUp;
		this.angle = angle;
		this.aspect = aspect;
		this.near = near;
		this.far = far;
		projection = Matrix4f.perspective(angle, aspect, near, far);
		Matrix4f view = Matrix4f.gluLookAt(pos, target, up);
		Matrix4f mat = projection.multiply(view);
		frust = new Frustum(mat);
		ShaderManager.setCamera(view, pos);
		ShaderManager.setProjection(projection);
	}

	/**
	 * Moves the camera and target
	 * 
	 * @param displacement
	 *            displacement vector
	 */
	public void moveCamera(String dir) {
		
		//do some vector math between target and pos to get move direction with displacement
		float vx = pos.x- target.x;
		float vy = pos.y - target.y;
		float vz = pos.z - target.z;
		Vector3f displacement = new Vector3f(0,0,0);
		if(dir=="UP"){
			displacement = new Vector3f(0,0,1);
		}else if(dir=="DOWN"){
			displacement = new Vector3f(0,0,-1);
		}else if(dir == "FORWARD"){
			displacement = new Vector3f(-vx,-vy,0);
		}else if(dir == "BACK"){
			displacement = new Vector3f(vx,vy,0);
		}else if(dir == "LEFT"){
			displacement = new Vector3f(-vy,vx,0);
		}else if(dir == "RIGHT"){
			displacement = new Vector3f(vy,-vx,0);
		}
		
		pos = pos.add(displacement);
		target = target.add(displacement);
		Matrix4f view = Matrix4f.gluLookAt(pos, target, up);
		ShaderManager.setCamera(view, pos);
		frust.updateMatrix(projection.multiply(view));
	}
	
	public void rotateCamera(double[] mousePos) {
		if (degX>360){
			degX -=360;
		}
		if (degZ>360){
			degZ -=360;
		}
		float mouseX = (float) ((1920/2-mousePos[0])/1920*2); 
		//could be -pi/2 to pi/2
		float mouseY = (float) ((1080/2-mousePos[1])/1080*2); 
		//why does this slow down(prolly a clamp issue)
		//float dx = target.x-pos.x;
		//float dy = target.y-pos.y;
		degX += mouseX;
		degZ += mouseY;
		//System.out.println(degX);
		float x = (float) Math.cos(degX*sense);
		float y = (float) Math.sin(degX*sense);
		
		float xz = (float) Math.cos(degZ*sense);
		float z= (float) Math.sin(degZ*sense);
		
		target.x = x+pos.x;
		target.y = y +pos.y;
		
		//target.x = xz +pos.x;
		//target.z = pos.z+z;
		//System.out.println(target.x+ "    "+ target.y);
		//target.z += mouseY;
		
		//Vector3f displacement = new Vector3f(mouseX,0, mouseY);
		//target = target.add(displacement);
		Matrix4f view = Matrix4f.gluLookAt(pos, target, up);
		ShaderManager.setCamera(view, pos);
		frust.updateMatrix(projection.multiply(view));
	}

	public Vector3f getPos() {
		return pos;
	}

	public Vector3f getTarget() {
		return target;
	}

	public Vector3f getUp() {
		return up;
	}

	public Frustum getFrustum() {
		return frust;
	}
	@Deprecated
	public void rotateCamera(String direction) {
		float tx = 0;
		float ty = 0;
		if(direction == "RIGHT"){
			tx = 1 +pos.x;
			ty = 1+pos.y;
		}else if(direction == "LEFT"){
			tx = -1 +pos.x;
			ty = 0+pos.y;
		}
		else if(direction == "UP"){
			tx = 0 +pos.x;
			ty = 1+pos.y;
		}
		else if(direction == "DOWN"){
			tx = 0 +pos.x;
			ty = -1+pos.y;
		}
		Vector3f displacement = new Vector3f(tx, ty, pos.z-1);
		target = displacement;
		Matrix4f view = Matrix4f.gluLookAt(pos, target, up);
		ShaderManager.setCamera(view, pos);
		frust.updateMatrix(projection.multiply(view));
		
	}

}
