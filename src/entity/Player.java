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
	public Vector3f destination;
	private float elevation;
	ShaderManager shaderManager;
	public int xIndex;
	public int yIndex;
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
		xIndex = 14;
		yIndex = 17;
		this.count = indices.length;
		this.position = new Vector3f();
		this.destination = this.position;
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
			getDestination();
			if(checkDestination()){
					this.position.x = this.destination.x;
					this.position.y = this.destination.y;
			}
			
	}
	public void getDestination(){
			//20/3 = 5/2
			//less than 2.5, more than 2
			float dis = (2.4f);
			if(KeyboardInput.isKeyDown(GLFW_KEY_Q)){
				this.destination.x = this.position.x-(apothem*sqrt3/2*dis);
				this.destination.y = this.position.y+(apothem/2*aspectScaler*dis);
			}
			if(KeyboardInput.isKeyDown(GLFW_KEY_W)){
				this.destination.y = this.position.y+(apothem*aspectScaler*dis);
			}
			if(KeyboardInput.isKeyDown(GLFW_KEY_E)){
				this.destination.x = this.position.x+(apothem*sqrt3/2*dis);
				this.destination.y = this.position.y+(apothem/2*aspectScaler*dis);
			}
			if(KeyboardInput.isKeyDown(GLFW_KEY_A)){
				this.destination.x = this.position.x-(apothem*sqrt3/2*dis);
				this.destination.y = this.position.y-(apothem/2*aspectScaler*dis);
			}
			if(KeyboardInput.isKeyDown(GLFW_KEY_S)){
				this.destination.y = this.position.y-(apothem*aspectScaler*dis);
			}
			if(KeyboardInput.isKeyDown(GLFW_KEY_D)){
				this.destination.x = this.position.x+(apothem*sqrt3/2*dis);
				this.destination.y = this.position.y-(apothem/2*aspectScaler*dis);
			}
	}
	public boolean checkDestination(){
		return true;
		//this function ensures that is its land... or it would
	}

}


