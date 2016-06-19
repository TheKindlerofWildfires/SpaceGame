package maths;

public class Plane {

	float A;
	float B;
	float C;
	float D;

	private Vector3f normal;
	private Vector3f point;

	public Plane(float a, float b, float c, float d) {
		A = a;
		B = b;
		C = c;
		D = d;
	}

	public Plane(Vector3f point, Vector3f normal) {
		Vector3f normalized = normal.normalize();
		A = normalized.x;
		B = normalized.y;
		C = normalized.z;
		D = A * point.x + B * point.y + C * point.z;
		this.normal = normalized;
		this.point = point;
	}

	public Plane(Vector3f pt1, Vector3f pt2, Vector3f pt3) {
		System.out.println(pt1);
		this.point = pt1;
		Vector3f v1 = pt1.subtract(pt2);
		Vector3f v2 = pt1.subtract(pt3);
		Vector3f normal = v1.cross(v2);
		this.normal = normal.normalize();
	}

	public float signedDistance(Vector3f point) {
		float dist = normal.dot(point.subtract(this.point));
		return dist;
	}
	
	public boolean isInside(Vector3f pt){
		return 0<(A*pt.x+B*pt.y+C*pt.z+D);
	}

	public String toString() {
		return "Norm: " + normal + " Pt: " + point;
	}

	
	public static void main(String[] args){
		Matrix4f persp = Matrix4f.perspective(45, 16/9f, .1f, 300f);
		Matrix4f view = Matrix4f.gluLookAt(new Vector3f(0,0,0), new Vector3f(0,10,0), new Vector3f(0,0,1));
		Frustum frust = new Frustum(persp.multiply(view));
		Vector3f[] box = new Vector3f[8];
		int i=0;
		box[i++] = new Vector3f(0,0,0);
		box[i++] = new Vector3f(0,0,-1);
		box[i++] = new Vector3f(0,-1,-1);
		box[i++] = new Vector3f(0,-1,0);
		box[i++] = new Vector3f(-1,0,0);
		box[i++] = new Vector3f(-1,0,-1);
		box[i++] = new Vector3f(-1,-1,-1);
		box[i++] = new Vector3f(-1,-1,0);
		System.out.println(frust.boxIsInside(box));
	}
}
