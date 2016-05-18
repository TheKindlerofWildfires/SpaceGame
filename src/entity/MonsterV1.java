package entity;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.Random;

import combat.Mechanics;
import GUI.Tick;
import maths.Vector3f;
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
	public int xIndex;
	public int yIndex;
	Entity target = Player.self;
	float APOTHEM = EntityManager.APOTHEM;
	float ASPECTSCALER = EntityManager.aspectScaler;
	private Random rng;
	static Entity self = Entity.getEntity("Leader");

	public MonsterV1() {
		rng = new Random();
		xIndex = 14;
		yIndex = 17;
		this.count = EntityManager.indices.length;
		this.position = new Vector3f();
		this.destination = new Vector3f();
		vao = new VertexArrayObject(EntityManager.vertices, EntityManager.indices);
		this.vaoID = vao.getVaoID();

		this.position.z = this.elevation;
		if (xIndex % 2 == 0) {
			this.position.y = -1.2f * (yIndex * APOTHEM * 2) * ASPECTSCALER + 1;
			this.position.x = 1.2f * (xIndex * 3 * APOTHEM / sqrt3) - 1;
			this.destination.x = this.position.x;
			this.destination.y = this.position.y;
		} else {
			this.position.y = -1.2f * (yIndex * APOTHEM * 2 + APOTHEM) * ASPECTSCALER + 1;
			this.position.x = 1.2f * (xIndex * 3 * APOTHEM / sqrt3) - 1;
			this.destination.x = this.position.x;
			this.destination.y = this.position.y;
		}
	}

	public void render() {
		if (dead()) {
			ShaderManager.deathShader.start();
			ShaderManager.deathShader.setUniform3f("pos", this.position);
			glBindVertexArray(this.vaoID);
			glEnableVertexAttribArray(0);
			glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
			ShaderManager.deathShader.stop();
		} else {
			ShaderManager.monsterShader.start();
			ShaderManager.monsterShader.setUniform3f("pos", this.position);
			glBindVertexArray(this.vaoID);
			glEnableVertexAttribArray(0);
			glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
			glDisableVertexAttribArray(0);
			glBindVertexArray(0);
			ShaderManager.monsterShader.stop();
		}
	}

	public boolean dead() {
		if (self.getEntityHealth() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void update() {
		if (!dead()) {
			getDestination();
			if (checkDestination()) {//thisisnevercalled
				if ((this.position.x - this.destination.x != 0) || (this.position.y - this.destination.y != 0)) {
					lastMove = Tick.getUpdateTick();
					this.position.x = this.destination.x;
					this.position.y = this.destination.y;
				}
			} else {
				destination.x = position.x;
				destination.y = position.y;
			}
		}

	}

	public void getDestination() {
		//20/3 = 5/2
		//less than 2.5, more than 2
		//welcome to rng
		int time = Tick.getUpdateTick();
		if (time - lastMove > 50 * (6 - self.getEntitySpeed())) { //5*(6-Entity.getSpeed())
			float dis = (2.4f);
			int r = rng.nextInt(6);
			if (r == 0) {
				this.destination.x = this.position.x - (APOTHEM * sqrt3 / 2 * dis);
				this.destination.y = this.position.y + (APOTHEM / 2 * ASPECTSCALER * dis);
			}
			if (r == 1) {
				this.destination.y = this.position.y + (APOTHEM * ASPECTSCALER * dis);
			}
			if (r == 2) {
				this.destination.x = this.position.x + (APOTHEM * sqrt3 / 2 * dis);
				this.destination.y = this.position.y + (APOTHEM / 2 * ASPECTSCALER * dis);
			}
			if (r == 3) {
				this.destination.x = this.position.x - (APOTHEM * sqrt3 / 2 * dis);
				this.destination.y = this.position.y - (APOTHEM / 2 * ASPECTSCALER * dis);
			}
			if (r == 4) {
				this.destination.y = this.position.y - (APOTHEM * ASPECTSCALER * dis);
			}
			if (r == 5) {
				this.destination.x = this.position.x + (APOTHEM * sqrt3 / 2 * dis);
				this.destination.y = this.position.y - (APOTHEM / 2 * ASPECTSCALER * dis);
			}
			Mechanics ah = new Mechanics();
			Player tarHex = EntityManager.player;
			ah.attackHandler(self, target, position, tarHex.getPosition());
			//System.out.println(target.getEntityHealth());
		}
	}

	private boolean checkDestination() {
		//System.out.println(Map.hexes.get(this.xIndex).get(this.yIndex).isLand());
		//if(Map.hexes.get(this.xIndex).get(this.yIndex).isLand()){
		return true;
	}

	//else{
	//return false;
	//this function ensures that is its land... or it would
	//}
	//}
	public Vector3f getPosition() {
		return this.position;
	}

}
