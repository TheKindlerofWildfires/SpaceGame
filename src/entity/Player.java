package entity;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import GUI.Tick;
import combat.Mechanics;
import maths.Vector3f;
import classesSimonDoesntLike.KeyboardInput;
import gameEngine.EntityManager;
import gameEngine.Map;
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
	private int lastMove;
	static Entity self = Entity.getEntity("Agent");
	Entity target = MonsterV1.self; //rwff --Monsterv1.self
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
	Mechanics ah = new Mechanics();
	byte[] indices = new byte[] { 0, 1, 2, 3, 4, 5, 0 };

	public Player(){
		lastMove = 0;
		shaderManager = new ShaderManager();
		ShaderManager.loadAll();
		xIndex = 14;
		yIndex = 21;
		this.count = indices.length;
		this.position = new Vector3f();
		this.destination = new Vector3f();
		vao = new VertexArrayObject(vertices, indices);
		this.vaoID = vao.getVaoID();
		this.position.z = this.elevation;
		if (xIndex % 2 == 0) {
			this.position.y = -1.2f * (yIndex * apothem * 2) * aspectScaler + 1;
			this.position.x = 1.2f * (xIndex * 3 * apothem / sqrt3) - 1;
			this.destination.x = this.position.x;
			this.destination.y = this.position.y;
		} else {
			this.position.y = -1.2f * (yIndex * apothem * 2 + apothem) * aspectScaler + 1;
			this.position.x = 1.2f * (xIndex * 3 * apothem / sqrt3) - 1;
			this.destination.x = this.position.x;
			this.destination.y = this.position.y;
		}
	}

	public void draw() {	
		if(dead()){
			ShaderManager.deathShader.start();
			ShaderManager.deathShader.setUniform3f("pos", this.position);
			glBindVertexArray(this.vaoID);
			glEnableVertexAttribArray(0);
			glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
			ShaderManager.deathShader.stop();
		}else{
			ShaderManager.playerShader.start();
			ShaderManager.playerShader.setUniform3f("pos", this.position);
			glBindVertexArray(this.vaoID);
			glEnableVertexAttribArray(0);
			glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
			ShaderManager.playerShader.stop();
		}
}
public boolean dead(){
	if(self.getEntityHealth()<=0){
		return true;
	}else{
		return false;
	}
}
	public void update(){
		//System.out.println(Tick.getUpdateTick());
		//System.out.println(self.getEntitySpeed());
			getDestination();
			if(checkDestination()){//thisisnevercalled
				if ((this.position.x-this.destination.x !=0)||(this.position.y-this.destination.y !=0)){
					lastMove = Tick.getUpdateTick();
					this.position.x = this.destination.x;
					this.position.y = this.destination.y;
				}
			}else{
					destination.x = position.x;
					destination.y = position.y;
			}
	}
	public void getDestination(){
		
		int time = Tick.getUpdateTick();
		//why is only r called 5 times
		//only called once per tick
		if(!dead()){
		if(time-lastMove >1.8*(6-self.getEntitySpeed())){ //between 6.66 - 33 tiles per second
			//only called once per tick
			//System.out.println(time);
			float dis = (2.4f);
				if(KeyboardInput.isKeyDown(GLFW_KEY_Q)){
					destination.x = position.x-(apothem*sqrt3/2*dis);
					destination.y = position.y+(apothem/2*aspectScaler*dis);
				}else if(KeyboardInput.isKeyDown(GLFW_KEY_W)){
					
					destination.y = position.y+(apothem*aspectScaler*dis);
				}else if(KeyboardInput.isKeyDown(GLFW_KEY_E)){
					destination.x = position.x+(apothem*sqrt3/2*dis);
					destination.y = position.y+(apothem/2*aspectScaler*dis);
				}else if(KeyboardInput.isKeyDown(GLFW_KEY_A)){
					destination.x = position.x-(apothem*sqrt3/2*dis);
					destination.y = position.y-(apothem/2*aspectScaler*dis);
				}else if(KeyboardInput.isKeyDown(GLFW_KEY_S)){
					destination.y = position.y-(apothem*aspectScaler*dis);
				}else if(KeyboardInput.isKeyDown(GLFW_KEY_D)){
					
					destination.x = position.x+(apothem*sqrt3/2*dis);
					destination.y = position.y-(apothem/2*aspectScaler*dis);
				}else if(KeyboardInput.isKeyDown(GLFW_KEY_R)){
					
					MonsterV1 monster = EntityManager.monster; 
					ah.attackHandler(self, target, position, monster.getPosition());
					//System.out.println("posx "+position.x+" posy" +position.y);
					//System.out.println("posx "+monster.getPosition().x+" posy" +monster.getPosition().y);
				}
		}
		}
	}
	private boolean checkDestination(){
		//if(Map.hexes.get(this.xIndex).get(this.yIndex).isLand()){
			return true;
		//}
		//else{
		//	return false;
			//this function ensures that is its land... or it would
		//}
	}
	public Vector3f getPosition(){
		return this.position;
	}

}


