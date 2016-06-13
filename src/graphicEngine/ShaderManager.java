package graphicEngine;

import maths.Matrix4f;
import maths.Vector3f;

public class ShaderManager {

	public static Shader landShader;
	public static Shader entityShader;
	public static Shader chunkShader;
	public static Shader testShader;

	public static void loadAll() {
		landShader = new Shader("src/shaders/landVertex.shader", "src/shaders/landFragment.shader");
		entityShader = new Shader("src/shaders/entityVertex.shader", "src/shaders/entityFragment.shader");
		chunkShader = new Shader("src/shaders/chunkVertex.shader", "src/shaders/chunkFragment.shader");
		testShader = new Shader("src/shaders/vertex.shader", "src/shaders/fragment.shader");
	}

	public static void setCamera(Vector3f cameraPos, Vector3f cameraTarget, Vector3f cameraUp) {
		chunkShader.start();
		Matrix4f view = Matrix4f.gluLookAt(cameraPos, cameraTarget, cameraUp);
		chunkShader.setUniformMatrix4f("view", view);
		ShaderManager.chunkShader.setUniform3f("cameraPos", cameraPos);
		chunkShader.stop();
	}

	public static void setCamera(Camera camera) {
		chunkShader.start();
		Matrix4f view = Matrix4f.gluLookAt(camera.getPos(), camera.getTarget(), camera.getUp());
		chunkShader.setUniformMatrix4f("view", view);
		ShaderManager.chunkShader.setUniform3f("cameraPos", camera.getPos());
		chunkShader.stop();
	}

	public static void setCamera(Matrix4f view, Vector3f cameraPos) {
		chunkShader.start();
		chunkShader.setUniformMatrix4f("view", view);
		ShaderManager.chunkShader.setUniform3f("cameraPos", cameraPos);
		chunkShader.stop();
	}

	public static void setProjection(Matrix4f projection) {
		chunkShader.start();
		chunkShader.setUniformMatrix4f("projection", projection);
		chunkShader.stop();
	}

	public static void setProjection(float angle, float aspect, float near, float far) {
		chunkShader.start();
		Matrix4f projection = Matrix4f.perspective(angle, aspect, near, far);
		chunkShader.setUniformMatrix4f("projection", projection);
		chunkShader.stop();
	}

	public static void setLight(Vector3f lightPos) {
		chunkShader.start();
		ShaderManager.chunkShader.setUniform3f("lightPos", lightPos);
		chunkShader.stop();
	}

	@Deprecated
	public static void setMatrices(Vector3f cameraPos, Vector3f cameraTarget, Vector3f cameraUp, Vector3f lightPos) {
		setCamera(cameraPos, cameraTarget, cameraUp);
		setProjection(45f, 16 / 9f, .1f, 300);
		setLight(lightPos);
	}
}
