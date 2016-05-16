package entity;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.Random;

import combat.Mechanics;
import GUI.Tick;
import maths.Vector3f;
import classesSimonDoesntLike.KeyboardInput;
import gameEngine.EntityManager;
import gameEngine.Map;
import graphicEngine.ShaderManager;
import graphicEngine.VertexArrayObject;


public class MonsterV1 {

	public int vaoID;
	public int count;
	public static final float sqrt3 = 1.7320508075688772f;
	private VertexArrayObject vao;
	public Vector3f position;
	public Vector3f destination;
	private float elevation;
	private int lastMove;
	ShaderManager shaderManager;
	public int xIndex;
	public int yIndex;
	Entity target = Player.self;
	public static final float aspectScaler = 16 / 9f;
	float apothem = gameEngine.Map.APOTHEM;
	float side = (float) (apothem * 2 / sqrt3);
	static Entity self = Entity.getEntity("Leader");
	float[] vertices = { side, 0, 0, //right 0
			side / 2, -apothem * aspectScaler, 0, // lower right 1
			-side / 2, -apothem * aspectScaler, 0, //lower left 2
			-side, 0, 0, //left 3
			-side / 2, apothem * aspectScaler, 0, //upper left 4
			side / 2, apothem * aspectScaler, 0, //upper right 5
			0, 0, 0 //center 6
			
	};
	private Random rng;
	byte[] indices = new byte[] { 0, 1, 2, 3, 4, 5, 0 };

	public MonsterV1(){
		rng = new Random();
		shaderManager = new ShaderManager();
		shaderManager.loadAll();
		xIndex = 14;
		yIndex = 17;
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
			shaderManager.deathShader.start();
			shaderManager.deathShader.setUniform3f("pos", this.position);
			glBindVertexArray(this.vaoID);
			glEnableVertexAttribArray(0);
			glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
			shaderManager.deathShader.stop();
		}else{
			shaderManager.monsterShader.start();
			shaderManager.monsterShader.setUniform3f("pos", this.position);
			glBindVertexArray(this.vaoID);
			glEnableVertexAttribArray(0);
			glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
			shaderManager.monsterShader.stop();
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
			if(!dead()){
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
			
	}
	public void getDestination(){
			//20/3 = 5/2
			//less than 2.5, more than 2
			//welcome to rng
			int time = Tick.getUpdateTick();
		if(time-lastMove >50*(6-self.getEntitySpeed())){ //5*(6-Entity.getSpeed())
			float dis = (2.4f);
			int r = rng.nextInt(6);
			if(r==0){
				this.destination.x = this.position.x-(apothem*sqrt3/2*dis);
				this.destination.y = this.position.y+(apothem/2*aspectScaler*dis);
			}
			if(r== 1){
				this.destination.y = this.position.y+(apothem*aspectScaler*dis);
			}
			if(r== 2){
				this.destination.x = this.position.x+(apothem*sqrt3/2*dis);
				this.destination.y = this.position.y+(apothem/2*aspectScaler*dis);
			}
			if(r == 3){
				this.destination.x = this.position.x-(apothem*sqrt3/2*dis);
				this.destination.y = this.position.y-(apothem/2*aspectScaler*dis);
			}
			if(r == 4){
				this.destination.y = this.position.y-(apothem*aspectScaler*dis);
			}
			if(r == 5){
				this.destination.x = this.position.x+(apothem*sqrt3/2*dis);
				this.destination.y = this.position.y-(apothem/2*aspectScaler*dis);
			}
				Mechanics ah = new Mechanics();
				Player tarHex = EntityManager.player; 
				ah.attackHandler(self, target, position, tarHex.getPosition());
				//System.out.println(target.getEntityHealth());
			}
	}
	private boolean checkDestination(){
		//System.out.println(Map.hexes.get(this.xIndex).get(this.yIndex).isLand());
		if(Map.hexes.get(this.xIndex).get(this.yIndex).isLand()){
			return true;
		}
		else{
			return false;
			//this function ensures that is its land... or it would
		}
	}
	public Vector3f getPosition(){
		return this.position;
	}

}


