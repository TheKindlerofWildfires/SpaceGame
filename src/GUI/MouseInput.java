package GUI;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseInput extends GLFWCursorPosCallback{
	@Override
	public void invoke(long window, double xpos, double ypos){
		//print pos to console
		System.out.println(xpos + "," +ypos);
	}
}
