package entity;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import GUI.KeyboardInput;
import combat.Mechanics;
import maths.Distance;
import maths.Vector3f;
import gameEngine.Biome;
import gameEngine.EntityManager;
import gameEngine.Map;
import gameEngine.Tick;
import graphicEngine.ShaderManager;
import graphicEngine.VertexArrayObject;

public class Player {

	private int destXIndex = 0;
	private int destYIndex = 0;

	private float elevation = 0;

	private int xIndex = Map.HEXESACROSS / 2;
	private int yIndex = Map.HEXESDOWN / 2;

	String[] inventory = new String[144]; //I suspect this will be objects soon 
											//EWW STRINGS
	private int lastMove;

	Distance distance = new Distance();

	private float offsetX;
	private float offsetY;
	private float zoomFactor;

	static Entity self = Entity.getEntity("Neo");
	Entity target = MonsterV1.self; //rwff

	Mechanics m = new Mechanics();

	Map map;
	Biome biome;

	public Player(Map map) {
		initPlayerShader();
		this.map = map;
		biome = new Biome(map);
		inventory[0] = self.getEntityWeaponTag();
		inventory[1] = self.getEntityArmorTag();
		lastMove = 0;
		xIndex = Map.HEXESACROSS / 2;
		yIndex = Map.HEXESDOWN / 2;
	}

	private void initPlayerShader() {
		ShaderManager.playerShader.start();
		ShaderManager.playerShader.setUniform1f("side", EntityManager.side);
		ShaderManager.playerShader.setUniform1f("apothem", EntityManager.APOTHEM);
		ShaderManager.playerShader.setUniform1f("aspect", EntityManager.aspectScaler);
		ShaderManager.playerShader.setUniform3f("pos", new Vector3f(-1f, 1f, 0));
		ShaderManager.playerShader.setUniform1i("xcoord", xIndex);
		ShaderManager.playerShader.setUniform1i("ycoord", yIndex);
		ShaderManager.playerShader.stop();
	}

	public void render() {
		ShaderManager.playerShader.start();
		ShaderManager.playerShader.setUniform1i("death", this.dead() ? 1 : 0);
		glBindVertexArray(Map.vaoID);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		ShaderManager.playerShader.stop();
	}

	public boolean dead() {
		if (self.getEntityHealth() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void update() {

		//System.out.println(inventory[0]);
		//System.out.println(Tick.getUpdateTick());
		//System.out.println(self.getEntitySpeed());
		if (Tick.getUpdateTick() - lastMove > (45.2 / self.getEntitySpeed() - 5.2)) {
			getDestination();

			if (checkDestination()) {				
				xIndex = destXIndex;
				yIndex = destYIndex;
				ShaderManager.playerShader.start();
				ShaderManager.playerShader.setUniform1i("xcoord", xIndex);
				ShaderManager.playerShader.setUniform1i("ycoord", yIndex);
				ShaderManager.playerShader.start();
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
				MonsterV1 monster = EntityManager.monster;
				int[] index = { xIndex, yIndex };
				m.attackHandler(self, target, index, monster.getIndex());
			}

		}
	}

	private boolean checkDestination() {
		//System.out.println(xIndex + " " + yIndex);
		if ((destXIndex > 0) && (destYIndex > 0) && (destXIndex < (Map.HEXESACROSS))
				&& (destYIndex < (Map.HEXESDOWN))) {
			if (biome.destinationTraversable(destXIndex, destYIndex)) {
				if (map.land[destXIndex][destYIndex] == Map.SEED) {
					return true;
					//seeds are bs
				} else if (Math.abs(Map.elevation[xIndex][yIndex] - Map.elevation[destXIndex][destYIndex]) < 100) {
					return true;
					//controls step up height
				}
			}
		}
		return false;
	}

	public void zoom(float zoomFactor) {
		this.zoomFactor = zoomFactor;
		ShaderManager.playerShader.start();
		ShaderManager.playerShader.setUniform1f("side", EntityManager.side * zoomFactor);
		ShaderManager.playerShader.setUniform1f("apothem", EntityManager.APOTHEM * zoomFactor);
		ShaderManager.playerShader.setUniform3f("pos", new Vector3f(-zoomFactor + offsetX, zoomFactor + offsetY, 0));
		ShaderManager.playerShader.stop();
	}

	public void offset(float x, float y) {
		if (offsetX + x > -zoomFactor + 1 && offsetX + x < zoomFactor - 1) {
			offsetX += x;
		} else {
			//System.out.println("toofar");
		}
		if (offsetY + y > -zoomFactor + 1 && offsetY + y < zoomFactor - 1) {
			offsetY += y;
		} else {
			//	System.out.println("toofar");
		}
		ShaderManager.playerShader.start();
		ShaderManager.playerShader.setUniform3f("pos", new Vector3f(-zoomFactor + offsetX, zoomFactor + offsetY, 0));
		ShaderManager.playerShader.stop();
	}

	public int[] getIndex() {
		return new int[] { xIndex, yIndex };
	}
}