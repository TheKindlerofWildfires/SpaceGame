package entity;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import GUI.KeyboardInput;
import combat.BuffHandler;
import combat.Mechanics;
import maths.Distance;
import maths.Vector3f;
import gameEngine.Block;
import gameEngine.EntityManager;
import gameEngine.Map;
import gameEngine.Tick;
import graphicEngine.ShaderManager;

public class Player extends Entity{

	private int destXIndex = 0;
	private int destYIndex = 0;


	private int xIndex = Map.HEXESACROSS / 2;
	private int yIndex = Map.HEXESDOWN / 2;

	String[] inventory = new String[144]; //I suspect this will be objects soon 
											//EWW STRINGS
	private int lastMove;
	private int hungerTime;

	Distance distance = new Distance();

	private float offsetX;
	private float offsetY;
	private float zoomFactor;

	static Entity self; //this needs to go away but im lazy
	//Entity target = Monster.self; //rwff

	Mechanics m = new Mechanics();

	Map map;

	public Player(Map map, String entityTag) {
		self = getEntity(entityTag); //really i should just find an easy way to set 
		//all values from my txt files, but im lazy and that will change anyways
		initPlayerShader();
		this.map = map;
		inventory[0] = self.getEntityWeaponTag();
		inventory[1] = self.getEntityArmorTag();
		lastMove = 0;
		hungerTime = 0;
		xIndex = Map.HEXESACROSS / 2;
		yIndex = Map.HEXESDOWN / 2;
	}

	private void initPlayerShader() {
		ShaderManager.entityShader.start();
		ShaderManager.entityShader.setUniform1f("side", EntityManager.side);
		ShaderManager.entityShader.setUniform1i("ID", getID(self.getEntityTag()));
		ShaderManager.entityShader.setUniform1f("apothem", EntityManager.APOTHEM);
		ShaderManager.entityShader.setUniform1f("aspect", EntityManager.aspectScaler);
		ShaderManager.entityShader.setUniform3f("pos", new Vector3f(-1f, 1f, 0));
		ShaderManager.entityShader.setUniform1i("xcoord", xIndex);
		ShaderManager.entityShader.setUniform1i("ycoord", yIndex);
		ShaderManager.entityShader.stop();
	}

	public void render() {
		ShaderManager.entityShader.start();
		ShaderManager.entityShader.setUniform1i("death", this.dead() ? 1 : 0);
		glBindVertexArray(Map.vaoID);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		ShaderManager.entityShader.stop();
	}

	public boolean dead() {
		if (self.getEntityHealth() <= 0) {
			return true;
		} else {
			return false;
		}
	}
	public void hunger(){
		//System.out.println(Tick.getUpdateTick());
		if(Tick.getSecTick()-hungerTime>50){
			self.setEntityHunger(self.getEntityHunger()-1);
			hungerTime = Tick.getSecTick();
		}
	}
	public void update() {
		hunger();
		//System.out.println(self.getEntityHunger());
		Block.steppedOn(xIndex, yIndex, self);
		if (Tick.getUpdateTick() - lastMove > (35.2 / self.getEntitySpeed() - 5.2)) {
			getDestination();

			if (checkDestination()) {				
				xIndex = destXIndex;
				yIndex = destYIndex;
				ShaderManager.entityShader.start();
				ShaderManager.entityShader.setUniform1i("xcoord", xIndex);
				ShaderManager.entityShader.setUniform1i("ycoord", yIndex);
				ShaderManager.entityShader.start();
				//	}
			} else {
				destXIndex = xIndex;
				destYIndex = yIndex;
			}
			lastMove = Tick.getUpdateTick();
		}
	}

	public void getDestination() {
		if (!dead()) {
			//System.out.println(Tick.getUpdateTick() - lastMove);

			destXIndex = xIndex;
			destYIndex = yIndex;

			if (KeyboardInput.isKeyDown(GLFW_KEY_Q)) {
				if (xIndex % 2 == 0) {
					destXIndex -= 1;
				} else {
					destYIndex -= 1;
					destXIndex -= 1;
				}
			} else if (KeyboardInput.isKeyDown(GLFW_KEY_W)) {
				destYIndex -= 1;
			} else if (KeyboardInput.isKeyDown(GLFW_KEY_E)) {
				if (xIndex % 2 == 0) {
					destXIndex += 1;

				} else {
					destXIndex += 1;
					destYIndex -= 1;
				}
			} else if (KeyboardInput.isKeyDown(GLFW_KEY_A)) {
				if (xIndex % 2 == 0) {
					destXIndex -= 1;
					destYIndex += 1;
				} else {
					destXIndex -= 1;
				}

			} else if (KeyboardInput.isKeyDown(GLFW_KEY_S)) {
				destYIndex += 1;
			} else if (KeyboardInput.isKeyDown(GLFW_KEY_D)) {
				if (xIndex % 2 == 0) {
					destXIndex += 1;
					destYIndex += 1;
				} else {
					destXIndex += 1;
				}
			} else if (KeyboardInput.isKeyDown(GLFW_KEY_R)) {
				System.out.println(Map.elevation[xIndex][yIndex]);
				lastMove = Tick.getUpdateTick();
				//Monster monster = EntityManager.monster;
				//int[] index = { xIndex, yIndex };
				//System.out.println(monster.getIndex() + "H");//thows null pointer cuz not init
				//m.attackHandler(self, monster, index, monster.getIndex());
			}

		}
	}

	private boolean checkDestination() {
		//System.out.println(xIndex + " " + yIndex);
		if ((destXIndex > 0) && (destYIndex > 0) && (destXIndex < (Map.HEXESACROSS))
				&& (destYIndex < (Map.HEXESDOWN))) {
			if (Block.destinationTraversable(destXIndex, destYIndex)) {
				if (Math.abs(Map.elevation[xIndex][yIndex] - Map.elevation[destXIndex][destYIndex]) < 10) {
					return true;
					//controls step up height
				}
			}
		}
		return false;
	}

	public void zoom(float zoomFactor) {
		this.zoomFactor = zoomFactor;
		ShaderManager.entityShader.start();
		ShaderManager.entityShader.setUniform1f("side", EntityManager.side * zoomFactor);
		ShaderManager.entityShader.setUniform1f("apothem", EntityManager.APOTHEM * zoomFactor);
		ShaderManager.entityShader.setUniform3f("pos", new Vector3f(-zoomFactor + offsetX, zoomFactor + offsetY, 0));
		ShaderManager.entityShader.stop();
	}

	public void offset(float x, float y) {
		if (offsetX + x > -zoomFactor + 1 && offsetX + x < zoomFactor - 1) {
			offsetX += x;
		} else {
		}
		if (offsetY + y > -zoomFactor + 1 && offsetY + y < zoomFactor - 1) {
			offsetY += y;
		} else {
		}
		ShaderManager.entityShader.start();
		ShaderManager.entityShader.setUniform3f("pos", new Vector3f(-zoomFactor + offsetX, zoomFactor + offsetY, 0));
		ShaderManager.entityShader.stop();
	}

	public int[] getIndex() {
		System.out.println("L");
		return new int[] { xIndex, yIndex };
	}
}