package entity;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import combat.Mechanics;
import maths.Distance;
import maths.Vector3f;
import gameEngine.Block;
import gameEngine.EntityManager;
import gameEngine.Map;
import gameEngine.Tick;
import graphicEngine.ShaderManager;

public class Monster extends Entity{

	private int destXIndex = 0;
	private int destYIndex = 0;


	private int xIndex = Map.HEXESACROSS / 2;
	private int yIndex = Map.HEXESDOWN / 2;

	private int lastMove;

	Distance distance = new Distance();

	private float offsetX;
	private float offsetY;
	private float zoomFactor;

	static Entity self; //this needs to go away but im lazy
	//Entity target = Player.self; //rwff

	Mechanics m = new Mechanics();

	Map map;
	Block block;

	public Monster(Map map, String entityTag) {
		self = getEntity(entityTag);
		initPlayerShader();
		this.map = map;
		block = new Block();
		lastMove = 0;
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

	public void update() {

		//System.out.println(inventory[0]);
		//System.out.println(Tick.getUpdateTick());
		//System.out.println(self.getEntitySpeed());
		if (Tick.getUpdateTick() - lastMove > (45.2 / self.getEntitySpeed() - 5.2)) {
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
			int r = Map.rng.nextInt(5);

			if (r==0) {
				if (xIndex % 2 == 0) {
					destXIndex -= 1;
				} else {
					destYIndex -= 1;
					destXIndex -= 1;
				}
			} else if (r==1) {
				destYIndex -= 1;
			} else if (r==2) {
				if (xIndex % 2 == 0) {
					destXIndex += 1;

				} else {
					destXIndex += 1;
					destYIndex -= 1;
				}
			} else if (r==3) {
				if (xIndex % 2 == 0) {
					destXIndex -= 1;
					destYIndex += 1;
				} else {
					destXIndex -= 1;
				}

			} else if (r==4) {
				destYIndex += 1;
			} else if (r==5) {
				if (xIndex % 2 == 0) {
					destXIndex += 1;
					destYIndex += 1;
				} else {
					destXIndex += 1;
				}
			} else if (r==6) {
				//System.out.println(Map.elevation[xIndex][yIndex]);
				lastMove = Tick.getUpdateTick();
				Player player = EntityManager.player;
				int[] index = { xIndex, yIndex };
				m.attackHandler(self, player, index, player.getIndex());
			}

		}
	}

	private boolean checkDestination() {
		//System.out.println(xIndex + " " + yIndex);
		if ((destXIndex > 0) && (destYIndex > 0) && (destXIndex < (Map.HEXESACROSS))
				&& (destYIndex < (Map.HEXESDOWN))) {
			if (block.destinationTraversable(destXIndex, destYIndex)) {
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
			//System.out.println("toofar");
		}
		if (offsetY + y > -zoomFactor + 1 && offsetY + y < zoomFactor - 1) {
			offsetY += y;
		} else {
			//	System.out.println("toofar");
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