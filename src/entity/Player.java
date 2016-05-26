package entity;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import combat.Mechanics;
import maths.Distance;
import maths.Vector3f;
import classesSimonDoesntLike.KeyboardInput;
import gameEngine.EntityManager;
import gameEngine.Map;
import gameEngine.Tick;
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
	public int xIndex;
	public int yIndex;
	public int xOld;
	public int yOld;
	int[] index = new int[2];
	private int lastMove;
	
	Distance distance = new Distance();
	
	static Entity self = Entity.getEntity("Agent");
	Entity target = MonsterV1.self; //rwff --Monsterv1.self
	public static final float aspectScaler = 16 / 9f;
	float apothem = gameEngine.EntityManager.APOTHEM;
	float side = (float) (apothem * 2 / sqrt3);
	float[] vertices = { side, 0, 0, //right 0
			side / 2, -apothem * aspectScaler, 0, // lower right 1
			-side / 2, -apothem * aspectScaler, 0, //lower left 2
			-side, 0, 0, //left 3
			-side / 2, apothem * aspectScaler, 0, //upper left 4
			side / 2, apothem * aspectScaler, 0, //upper right 5
			0, 0, 0 //center 6
	};
	Mechanics m = new Mechanics();
	byte[] indices = new byte[] { 0, 1, 2, 3, 4, 5, 0 };
	Map map;
	public Player(Map map) {
		this.map = map;
		lastMove = 0;
		xIndex = Map.HEXESACROSS/2;
		yIndex = Map.HEXESDOWN/2;
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

	public boolean dead() {
		if (self.getEntityHealth() <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void update() {
		//System.out.println(Tick.getUpdateTick());
		//System.out.println(self.getEntitySpeed());
		getDestination();
		
		index[0] = xIndex;
		index[1] = yIndex;
		if (checkDestination()) {
			if ((this.position.x - this.destination.x != 0) || (this.position.y - this.destination.y != 0)) {
				lastMove = Tick.getUpdateTick();
				this.position.x = this.destination.x;
				this.position.y = this.destination.y;
			}
		} else {
			destination.x = position.x;
			destination.y = position.y;
			yIndex = yOld;
			xIndex = xOld;
		}
	}

	public void getDestination() {
		if (!dead()) {
			if (Tick.getUpdateTick() - lastMove > (45.2/self.getEntitySpeed()-5.2)) { //between 6.66 - 33 tiles per second
				float dis = (2.4f);
				yOld = yIndex;
				xOld = xIndex;
				if (KeyboardInput.isKeyDown(GLFW_KEY_Q)) {
					destination.x = position.x - (apothem * sqrt3 / 2 * dis);
					destination.y = position.y + (apothem / 2 * aspectScaler * dis);
					if(xIndex%2==0){
						xIndex -= 1;
					}else{
						yIndex -=1;
						xIndex -= 1;	
					}	
				} else if (KeyboardInput.isKeyDown(GLFW_KEY_W)) {
					destination.y = position.y + (apothem * aspectScaler * dis);
					yIndex -= 1; 
				} else if (KeyboardInput.isKeyDown(GLFW_KEY_E)) {
					destination.x = position.x + (apothem * sqrt3 / 2 * dis);
					destination.y = position.y + (apothem / 2 * aspectScaler * dis);
					if(xIndex%2==0){
						xIndex += 1;
						
					}else{
						xIndex += 1; 
						yIndex -= 1;
					}
				} else if (KeyboardInput.isKeyDown(GLFW_KEY_A)) {
					destination.x = position.x - (apothem * sqrt3 / 2 * dis);
					destination.y = position.y - (apothem / 2 * aspectScaler * dis);
					if(xIndex%2==0){
						xIndex -= 1;
						yIndex += 1;
					}else{
						xIndex -= 1;
					}
					
				} else if (KeyboardInput.isKeyDown(GLFW_KEY_S)) {
					destination.y = position.y - (apothem * aspectScaler * dis);
					yIndex += 1; 
				} else if (KeyboardInput.isKeyDown(GLFW_KEY_D)) {
					destination.x = position.x + (apothem * sqrt3 / 2 * dis);
					destination.y = position.y - (apothem / 2 * aspectScaler * dis); 
					if(xIndex%2==0){
						xIndex += 1;
						yIndex +=1;
					}else{
						xIndex += 1;
					}
					
				} else if (KeyboardInput.isKeyDown(GLFW_KEY_R)) {
					System.out.println(Map.elevation[xIndex][yIndex]);
					lastMove = Tick.getUpdateTick();
					MonsterV1 monster = EntityManager.monster;
					//System.out.println(index[1] + "    " + monster.getIndex()[1]);
					m.attackHandler(self, target, index, monster.getIndex());
					//System.out.println("posx "+position.x+" posy" +position.y);
					//System.out.println("posx "+monster.getPosition().x+" posy" +monster.getPosition().y);
				}
			}
		}
	}

	private boolean checkDestination() {
		//System.out.println(xIndex + " " + yIndex);
		
		if((xIndex>0) && (yIndex> 0) &&(xIndex<(Map.HEXESACROSS)) &&(yIndex<(Map.HEXESDOWN))){
			if (map.land[xIndex][yIndex] == Map.LAND||map.land[xIndex][yIndex] == Map.SEED) {
				if( map.land[xOld][yOld] == Map.SEED){
					return true;
					//seeds are bs
				}else if(!(distance.eleDis(Map.elevation[xIndex][yIndex], Map.elevation[xOld][yOld])>20)){
					return true;
					//controls jump height
				}else{
					//the latest kill switch
					return false;
				}
			}
		}else{
			return false;
		}
		return false;
	}

	public int[] getIndex() {
		return this.index;
	}

}
