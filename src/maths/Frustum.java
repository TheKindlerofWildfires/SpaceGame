package maths;

public class Frustum {

	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int RIGHT = 2;
	public static final int LEFT = 3;
	public static final int NEAR = 4;
	public static final int FAR = 5;

	public static final int INSIDE = 0;
	public static final int INTERSECT = 1;
	public static final int OUTSIDE = 2;

	public Plane[] planes = new Plane[6];
	Vector3f ntl, ntr, nbl, nbr, ftl, ftr, fbl, fbr;
	float nearD, farD, ratio, angle, tang;
	float nw, nh, fw, fh;

	public Frustum(Matrix4f mat) {
		setAll(mat);
	}

	public void updateMatrix(Matrix4f mat) {
		setAll(mat);
	}

	private void setAll(Matrix4f mat) {
		float a = mat.m30 + mat.m00;
		float b = mat.m31 + mat.m01;
		float c = mat.m32 + mat.m02;
		float d = mat.m33 + mat.m03;
		planes[LEFT] = new Plane(a, b, c, d);
		a = mat.m30 - mat.m00;
		b = mat.m31 - mat.m01;
		c = mat.m32 - mat.m02;
		d = mat.m33 - mat.m03;
		planes[RIGHT] = new Plane(a, b, c, d);
		a = mat.m30 + mat.m10;
		b = mat.m31 + mat.m11;
		c = mat.m32 + mat.m12;
		d = mat.m33 + mat.m13;
		planes[BOTTOM] = new Plane(a, b, c, d);
		a = mat.m30 - mat.m10;
		b = mat.m31 - mat.m11;
		c = mat.m32 - mat.m12;
		d = mat.m33 - mat.m13;
		planes[TOP] = new Plane(a, b, c, d);
		a = mat.m30 + mat.m20;
		b = mat.m31 + mat.m21;
		c = mat.m32 + mat.m22;
		d = mat.m33 + mat.m23;
		planes[NEAR] = new Plane(a, b, c, d);
		a = mat.m30 - mat.m20;
		b = mat.m31 - mat.m21;
		c = mat.m32 - mat.m22;
		d = mat.m33 - mat.m23;
		planes[FAR] = new Plane(a, b, c, d);
	}

	public boolean ptIsInside(Vector3f pt) {
		for (int i = 0; i < 6; i++) {
			if (!planes[i].isInside(pt)) {
				return false;
			}
		}
		return true;
	}

	public boolean boxIsInside(Vector3f[] box) {
		//for each plane
		for (int i = 0; i < 6; i++) {
			int numberInside = 0;
			for (int j = 0; j < 8; j++) {
				if (planes[i].isInside(box[j])) {
					numberInside++;
				}
			}
			if (numberInside == 0) {
				return false;
			}
		}
		return true;
	}
}
