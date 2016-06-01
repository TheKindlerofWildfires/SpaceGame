package entity;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.Random;

import combat.Mechanics;
import maths.Distance;
import maths.Vector3f;
import gameEngine.Biome;
import gameEngine.EntityManager;
import gameEngine.Map;
import gameEngine.Tick;
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
	int[] index = new int[2];
	private int lastMove;
	public int xIndex;
	public int yIndex;
	public int dy;
	public int dx;
	Mechanics m = new Mechanics();
	Entity target = Player.self;
	float apothem = EntityManager.APOTHEM;
	float ASPECTSCALER = EntityManager.aspectScaler;
	private Random rng = new Random();
	private Map map;
	Biome biome;
	Distance distance = new Distance();
	static Entity self = Entity.getEntity("Leader");
	Player player;

	public MonsterV1(Map map) {
		player = EntityManager.player;
		this.map = map;
		biome = new Biome(map);
		xIndex = Map.HEXESACROSS/2+1;
		yIndex = Map.HEXESDOWN/2;
		this.count = EntityManager.indices.length;
		this.position = new Vector3f();
		this.destination = new Vector3f();
		vao = new VertexArrayObject(EntityManager.vertices, EntityManager.indices);
		this.vaoID = vao.getVaoID();

		this.position.z = this.elevation;
		if (xIndex % 2 == 0) {
			this.position.y = -1.2f * (yIndex * apothem * 2) * ASPECTSCALER + 1;
			this.position.x = 1.2f * (xIndex * 3 * apothem / sqrt3) - 1;
			this.destination.x = this.position.x;
			this.destination.y = this.position.y;
		} else {
			this.position.y = -1.2f * (yIndex * apothem * 2 + apothem) * ASPECTSCALER + 1;
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

		//System.out.println(self.getEntityAbility());
		index[0] = xIndex;
		index[1] = yIndex;
		if (!dead()) {
			getDestination();
			if (checkDestination()) {
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
		if (!dead()) {
			if (Tick.getUpdateTick() - lastMove > (45.2/self.getEntitySpeed()-5.2)) { 
				int D = getProbableDirection();
				float dis = (2.4f);
				dy = yIndex;
				dx = xIndex;
				//int  D= rng.nextInt(5);
				if (D==0) {
					destination.x = position.x - (apothem * sqrt3 / 2 * dis);
					destination.y = position.y + (apothem / 2 * ASPECTSCALER * dis);
					if(xIndex%2==0){
						xIndex -= 1;
					}else{
						yIndex -=1;
						xIndex -= 1;
						
					}
					
				} else if (D==1) {
					destination.y = position.y + (apothem * ASPECTSCALER * dis);
					yIndex -= 1; 
				} else if (D==2) {
					destination.x = position.x + (apothem * sqrt3 / 2 * dis);
					destination.y = position.y + (apothem / 2 * ASPECTSCALER * dis);
					if(xIndex%2==0){
						xIndex += 1;
						
					}else{
						xIndex += 1; 
						yIndex -= 1;
					}
				} else if (D==3) {
					destination.x = position.x - (apothem * sqrt3 / 2 * dis);
					destination.y = position.y - (apothem / 2 * ASPECTSCALER * dis);
					if(xIndex%2==0){
						xIndex -= 1;
						yIndex += 1;
					}else{
						xIndex -= 1;
					}
					
				} else if (D==4) {
					destination.y = position.y - (apothem * ASPECTSCALER * dis);
					yIndex += 1; 
				} else if (D==5) {
					destination.x = position.x + (apothem * sqrt3 / 2 * dis);
					destination.y = position.y - (apothem / 2 * ASPECTSCALER * dis); 
					if(xIndex%2==0){
						xIndex += 1;
						yIndex +=1;
					}else{
						xIndex += 1;
					}
					
				} 
				if (m.inRange(self)){
					
					//System.out.println("attack");
					lastMove = Tick.getUpdateTick();

					m.attackHandler(self, target, index, player.getIndex());
				}
			}
		}
	}
	private int getProbableDirection(){
		//the worst ai ever
		int tX = player.getIndex()[0]-xIndex;
		int tY = player.getIndex()[1]-yIndex;
		//0a, 1s, 2d,3e,4w,5q
		if((tY==0) && (tX >0)){
			return 1;//s
		} else if((tY==0) && (tX <0)){
			return 4;//w
		}else if((tY>0) && (tX >0)){
			return 5;//d
		}else if((tY>0) && (tX <0)){
			return 3;//a
		}else if((tY<0) && (tX <0)){
			return 2;//q
		}else if((tY<0) && (tX >0)){
			return 0;//e
		}
		return 6;
	}
	private boolean checkDestination() {
		//System.out.println(xIndex + " " + yIndex);
		if((xIndex>0) && (yIndex> 0) &&(xIndex<(Map.HEXESACROSS)) &&(yIndex<(Map.HEXESDOWN))){
			if (biome.destinationTraversable(xIndex, yIndex)) {
				if( map.land[dx][dy] == Map.SEED){
					return true;
					//seeds are bs
				}else if(!(distance.eleDis(Map.elevation[xIndex][yIndex], Map.elevation[dx][dy])>20)){
					return true;
					//controls step up height
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

	//else{
	//return false;
	//this function ensures that is its land... or it would
	//}
	//}
	public int[] getIndex() {
		return this.index;
	}

}
