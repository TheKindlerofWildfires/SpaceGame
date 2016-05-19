package classesSimonDoesntLike;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import maths.Vector3f;
import gameEngine.EntityManager;
import graphicEngine.VertexArrayObject;

public class Hexagon {

	public int vaoID;
	public int count;
	public static final float sqrt3 = 1.7320508075688772f;
	private VertexArrayObject vao;
	public Vector3f position;
	private boolean land;
	private float moisture;
	private float elevation;
	public int xIndex;
	public int yIndex;
	float apothem = EntityManager.APOTHEM;
	public float side = (float) (apothem * 2 / sqrt3);
	public static final float aspectScaler = 16 / 9f;
	float[] vertices = { side, 0, 0, //right 0
			side / 2, -apothem * aspectScaler, 0, // lower right 1
			-side / 2, -apothem * aspectScaler, 0, //lower left 2
			-side, 0, 0, //left 3
			-side / 2, apothem * aspectScaler, 0, //upper left 4
			side / 2, apothem * aspectScaler, 0, //upper right 5
			0, 0, 0 //center 6
	};

	public Hexagon(int xIndex, int yIndex, float moisture, float elevation) {
		this.moisture = moisture;
		this.elevation = elevation;
		this.xIndex = xIndex;
		this.yIndex = yIndex;
		byte[] indices = new byte[] { 0, 1, 2, 3, 4, 5, 0 };
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
		
		glBindVertexArray(this.vaoID);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLE_FAN, 7, GL_UNSIGNED_BYTE, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}

	public boolean checkBounds() {
		return false;
	}
	public void update() {
	}

	public void setLand(boolean land) {
		this.land = land;
	}

	public boolean isLand() {
		return land;
	}
}
