package graphicEngine;

import maths.Matrix4f;
import maths.Vector3f;

public class ShaderManager {

	public static Shader landShader;
	public static Shader playerShader;
	public static Shader monsterShader;
	public static Shader chunkShader;

	public static void loadAll() {
		landShader = new Shader("src/shaders/landVertex.shader", "src/shaders/landFragment.shader");
		playerShader = new Shader("src/shaders/playerVertex.shader", "src/shaders/playerFragment.shader");
		monsterShader = new Shader("src/shaders/monsterVertex.shader", "src/shaders/monsterFragment.shader");
		chunkShader = new Shader("src/shaders/chunkVertex.shader", "src/shaders/chunkFragment.shader");
	}

	public static void setMatrices(Vector3f cameraPos, Vector3f cameraTarget, Vector3f cameraUp, Vector3f lightPos) {
		chunkShader.start();
		chunkShader.setUniformMatrix4f("model", Matrix4f.translate(0, 0, 0).multiply(Matrix4f.scale(1, 1, .5f)));
		//.multiply(Matrix4f.rotate(0, 1f, 0f,0f)).multiply(Matrix4f.rotate(0)));
		//.multiply(Matrix4f.rotate(90, 1, 0, 0)));
		chunkShader.setUniformMatrix4f("view", Matrix4f.gluLookAt(cameraPos, cameraTarget, cameraUp));
		chunkShader.setUniformMatrix4f("projection", Matrix4f.perspective(45f, 16 / 9f, .1f, 300f));

		ShaderManager.chunkShader.setUniform3f("lightPos", lightPos);
		ShaderManager.chunkShader.setUniform3f("cameraPos", cameraPos);
		
		chunkShader.stop();
	}
}
