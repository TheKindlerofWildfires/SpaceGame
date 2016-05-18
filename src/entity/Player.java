package entity;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import classesSimonDoesntLike.KeyboardInput;
import GUI.Vector3f;
import graphicEngine.ShaderManager;
import graphicEngine.VertexArrayObject;


public class Player {

	public int vaoID;
	public int count;
	public static final float sqrt3 = 1.7320508075688772f;
	private VertexArrayObject vao;
	public Vector3f position;
	private float elevation;
	public int xIndex;
	public int yIndex;
	ShaderManager shaderManager;
	public static final float aspectScaler = 16 / 9f;
	float apothem = gameEngine.Map.APOTHEM;
	float side = (float) (apothem * 2 / sqrt3);
	float[] vertices = { side, 0, 0, //right 0
			side / 2, -apothem * aspectScaler, 0, // lower right 1
			-side / 2, -apothem * aspectScaler, 0, //lower left 2
			-side, 0, 0, //left 3
			-side / 2, apothem * aspectScaler, 0, //upper left 4
			side / 2, apothem * aspectScaler, 0, //upper right 5
			0, 0, 0 //center 6
	};
	byte[] indices = new byte[] { 0, 1, 2, 3, 4, 5, 0 };

	public Player(){
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
	
		this.count = indices.length;
		this.position = new Vector3f();
		vao = new VertexArrayObject(vertices, indices);
		this.vaoID = vao.getVaoID();

		this.position.z = this.elevation;
		if (xIndex % 2 == 0) {
			this.position.y = -1.2f * (yIndex * apothem * 2) * aspectScaler + 1;
			this.position.x = 1.2f * (xIndex * 3 * apothem / sqrt3) - 1;
		} else {
			this.position.y = -1.2f * (yIndex * apothem * 2 + apothem) * aspectScaler + 1;
			this.position.x = 1.2f * (xIndex * 3 * apothem / sqrt3) - 1;
		}
	}

	public void draw() {
		shaderManager.playerShader.start();
		shaderManager.playerShader.setUniform3f("pos", this.position);
		glBindVertexArray(this.vaoID);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		shaderManager.playerShader.stop();
	}

	public void update(){

				if(KeyboardInput.isKeyDown(GLFW_KEY_Q)){//why is this jumpy?
					for(int x = 0; x<10;x++){
					this.position.x -= (float)(0.579);
					this.position.y += (float)(Math.sqrt(3)/4);
					System.out.println(position.x);
			}
	}
	}
}


