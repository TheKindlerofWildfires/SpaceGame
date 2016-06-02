package graphicEngine;

public class ShaderManager {

	public static Shader landShader;
	public static Shader playerShader;
	public static Shader monsterShader;
	public static Shader deathShader;
	public static Shader chunkShader;

	public static void loadAll() {
		landShader = new Shader("src/shaders/landVertex.shader", "src/shaders/landFragment.shader");
		playerShader = new Shader("src/shaders/playerVertex.shader", "src/shaders/playerFragment.shader");
		monsterShader = new Shader("src/shaders/monsterVertex.shader", "src/shaders/monsterFragment.shader");
		deathShader = new Shader("src/shaders/deathVertex.shader", "src/shaders/deathFragment.shader");
		chunkShader = new Shader("src/shaders/chunkVertex.shader", "src/shaders/chunkFragment.shader");
	}
	
}
