package graphicEngine;

public class ShaderManager {

	public Shader shader1;
	public Shader playerShader;

	public void loadAll() {
		shader1 = new Shader("src/shaders/landVertex.shader", "src/shaders/landFragment.shader");
		playerShader = new Shader("src/shaders/playerVertex.shader", "src/shaders/playerFragment.shader");
	}
}
