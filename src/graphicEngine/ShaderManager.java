package graphicEngine;

public class ShaderManager {
	
	public static Shader landShader;
	public static Shader playerShader;
	public static Shader waterShader;
	
	public static void loadAll(){
		landShader = new Shader("src/shaders/landVertex.shader", "src/shaders/landFragment.shader");
		playerShader = new Shader("src/shaders/playerVertex.shader", "src/shaders/playerFragment.shader");
		waterShader = new Shader("src/shaders/waterVertex.shader", "src/shaders/waterFragment.shader");
	}
}
