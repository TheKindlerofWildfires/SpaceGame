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

import maths.Utilities;

public class VertexArrayObject {
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;

	private int vaoID;

	public VertexArrayObject(float[] vertices, byte[] indices) {
		createArrayObject(vertices, indices);
	}

	public VertexArrayObject(float[] vertices) {
		createArrayObject(vertices);
	}

	public void createArrayObject(float[] vertices, byte[] indices) {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);

		createVerticesBuffer(vertices);
		createIndicesBuffer(indices);

		glBindVertexArray(0);
	}

	public void createArrayObject(float[] vertices) {
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		createVerticesBuffer(vertices);
		glBindVertexArray(0);
	}

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

	private void createIndicesBuffer(byte[] indices) {
		int ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utilities.createByteBuffer(indices), GL_STATIC_DRAW);
	}

	public int getVaoID() {
		return this.vaoID;
	}
}
