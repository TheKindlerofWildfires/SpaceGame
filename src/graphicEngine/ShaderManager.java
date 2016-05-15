package graphicEngine;

public class ShaderManager {

	public Shader shader1;
	public Shader playerShader;
	public Shader monsterShader;
	public Shader deathShader;

	public void loadAll() {
		shader1 = new Shader("src/shaders/landVertex.shader", "src/shaders/landFragment.shader");
		playerShader = new Shader("src/shaders/playerVertex.shader", "src/shaders/playerFragment.shader");
		monsterShader = new Shader("src/shaders/monsterVertex.shader", "src/shaders/monsterFragment.shader");
		deathShader = new Shader("src/shaders/deathVertex.shader", "src/shaders/deathFragment.shader");
	}
	
}
