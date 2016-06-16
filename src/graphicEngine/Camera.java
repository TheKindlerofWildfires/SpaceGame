package graphicEngine;

import maths.Frustum;
import maths.Matrix4f;
import maths.Vector3f;

public class Camera {

	private Vector3f pos, target, up;
	private float angle, aspect, near, far;

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
	public void moveCamera(Vector3f displacement) {
		pos = pos.add(displacement);
		target = target.add(displacement);
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

}
