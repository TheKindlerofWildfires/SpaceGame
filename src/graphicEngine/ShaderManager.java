package graphicEngine;

public class ShaderManager {
	
	public static Shader shader1;
	public static Shader playerShader;
	
	public static void loadAll(){
		shader1 = new Shader("src/shaders/landVertex.shader", "src/shaders/landFragment.shader");
		playerShader = new Shader("src/shaders/playerVertex.shader", "src/shaders/playerFragment.shader");
	}
}
