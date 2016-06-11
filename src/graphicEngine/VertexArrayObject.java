package graphicEngine;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL33.glVertexAttribDivisor;

import maths.Utilities;

public class VertexArrayObject {
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;

	private int vaoID;

	@Deprecated
	public VertexArrayObject(float[] vertices, byte[] indices) {
		createArrayObject(vertices, indices);
	}

	@Deprecated
	public VertexArrayObject(float[] vertices) {
		createArrayObject(vertices);
	}

	@Deprecated
	public VertexArrayObject(float[] vertices, boolean isOnlyCoords) {
		createArrayObject(vertices, isOnlyCoords);
	}

	@Deprecated
	public VertexArrayObject(float[] positions, float[] colours) {
		createArrayObject(positions, colours);
	}

	public VertexArrayObject(float[] positions, int numberOfVec3s) {
		createArrayObject(positions, numberOfVec3s);
	}

	public void createArrayObject(float[] positions, int numberOfVec3s) {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		createVerticesBuffer(positions, numberOfVec3s);
		glBindVertexArray(0);
	}

	@Deprecated
	public void createArrayObject(float[] positions, float[] colours) {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		createVerticesBuffer(positions, colours);
		glBindVertexArray(0);
	}

	private void createVerticesBuffer(float[] positions, int numberOfVec3s) {
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, Utilities.createFloatBuffer(positions), GL_STATIC_DRAW);
		for (int i = 0; i < numberOfVec3s; i++) {
			glEnableVertexAttribArray(i);
			glVertexAttribPointer(i, 3, GL_FLOAT, false, 4 * 3 * numberOfVec3s, i * 3 * 4); //send positions on pipe 0
		}
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Deprecated
	private void createVerticesBuffer(float[] positions, float[] colours) {
		int positionsBuffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, positionsBuffer);
		glBufferData(GL_ARRAY_BUFFER, Utilities.createFloatBuffer(positions), GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); //send positions on pipe 0
		int colorsBuffer = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, colorsBuffer);
		glBufferData(GL_ARRAY_BUFFER, Utilities.createFloatBuffer(colours), GL_STATIC_DRAW);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0); //send positions on pipe 0
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glVertexAttribDivisor(1, 1);
	}

	@Deprecated
	public void createArrayObject(float[] vertices, byte[] indices) {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		createVerticesBuffer(vertices);
		createIndicesBuffer(indices);

		glBindVertexArray(0);
	}

	@Deprecated
	public void createArrayObject(float[] vertices) {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		createVerticesBuffer(vertices);
		glBindVertexArray(0);
	}

	@Deprecated
	public void createArrayObject(float[] vertices, boolean isOnlyCoords) {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		createVerticesBuffer(vertices, isOnlyCoords);
		glBindVertexArray(0);
	}

	@Deprecated
	private void createVerticesBuffer(float[] vertices) {
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, Utilities.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 4 * 9, 0); //send positions on pipe 0
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 4 * 9, 3 * 4); //send colors on pipe 1
		glEnableVertexAttribArray(2);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 4 * 9, 6 * 4); //send normals on pipe 2
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Deprecated
	private void createVerticesBuffer(float[] vertices, boolean isOnlyCoords) {
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, Utilities.createFloatBuffer(vertices), GL_STATIC_DRAW);
		if (!isOnlyCoords) {
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 4 * 9, 0); //send positions on pipe 0
			glEnableVertexAttribArray(1);
			glVertexAttribPointer(1, 3, GL_FLOAT, false, 4 * 9, 3 * 4); //send colors on pipe 1
			glEnableVertexAttribArray(2);
			glVertexAttribPointer(2, 3, GL_FLOAT, false, 4 * 9, 6 * 4); //send normals on pipe 2
		} else {
			glEnableVertexAttribArray(0);
			System.out.println("ello");
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0); //send positions on pipe 0
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
	}

	@Deprecated
	private void createIndicesBuffer(byte[] indices) {
		int ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utilities.createByteBuffer(indices), GL_STATIC_DRAW);
	}

	public int getVaoID() {
		return this.vaoID;
	}
}
