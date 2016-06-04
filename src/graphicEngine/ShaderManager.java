package graphicEngine;

public class ShaderManager {

	public static Shader landShader;
	public static Shader entityShader;
	public static Shader chunkShader;
	public static void loadAll() {
		landShader = new Shader("src/shaders/landVertex.shader", "src/shaders/landFragment.shader");
		entityShader = new Shader("src/shaders/entityVertex.shader", "src/shaders/entityFragment.shader");
		chunkShader = new Shader("src/shaders/chunkVertex.shader", "src/shaders/chunkFragment.shader");
	}
	
}
