package gen;

import gameEngine.Map;
import gameEngine.WorldGenerator;
import gameEngine.WorldType;

public class Structure {
	String[] ruins = new String[WorldType.interference.size()];
	private String worldType = Map.worldType;
	public Structure(){
		for(int i = 0; i<WorldType.interference.size();i++){
			ruins[i]=WorldType.interference.get(i);
			switch(ruins[i]){
			case "octividRuins1":
				octividRuins1();
				break;
			case "kinikariRuins1":
				kinikariRuins1();
				break;
			case "jeneunGates1":
				jeneunGates1();
				break;
			case "jeneunRuins1":
				jeneunRuins1();
				break;
			}
		}
	}
	private void jeneunRuins1() {
		// TODO Auto-generated method stub
		
	}
	private void jeneunGates1() {
		// TODO Auto-generated method stub
		
	}
	private void kinikariRuins1() {
		// TODO Auto-generated method stub
		
	}
	public static void octividRuins1() {
		//WorldGenerator.data[1][1][1] = 1;
		int cx = 0;
		int cy = 0;
		int cz = 40;
		for(int z = cz; z<cz+5; z++){
		WorldGenerator.data[cx+5][cy+1][z] = 20;
		WorldGenerator.data[cx+4][cy+1][z] = 20;
		WorldGenerator.data[cx+6][cy+1][z] = 20;
		WorldGenerator.data[cx+3][cy+2][z] = 20;
		WorldGenerator.data[cx+7][cy+2][z] = 20;
		WorldGenerator.data[cx+3][cy+1][z] = 20;
		WorldGenerator.data[cx+7][cy+1][z] = 20;
		
		
		WorldGenerator.data[cx+2][cy+2][z] = 20;
		WorldGenerator.data[cx+8][cy+2][z] = 20;
		WorldGenerator.data[cx+2][cy+3][z] = 20;
		WorldGenerator.data[cx+8][cy+3][z] = 20;
		WorldGenerator.data[cx+2][cy+4][z] = 20;
		WorldGenerator.data[cx+8][cy+4][z] = 20;
		WorldGenerator.data[cx+2][cy+5][z] = 20;
		WorldGenerator.data[cx+8][cy+5][z] = 20;
		WorldGenerator.data[cx+1][cy+4][z] = 20;
		WorldGenerator.data[cx+9][cy+4][z] = 20;
		
		
		WorldGenerator.data[cx+3][cy+7][z] = 20;
		WorldGenerator.data[cx+7][cy+7][z] = 20;
		WorldGenerator.data[cx+5][cy+7][z] = 20;
		WorldGenerator.data[cx+4][cy+6][z] = 20;
		WorldGenerator.data[cx+6][cy+6][z] = 20;
		WorldGenerator.data[cx+3][cy+6][z] = 20;
		WorldGenerator.data[cx+7][cy+6][z] = 20;
		WorldGenerator.data[cx+3][cy+6][z] = 20;
		WorldGenerator.data[cx+7][cy+6][z] = 20;
		}
		
		//for(int x = cx; x<cx+10; x++){
			//for(int y = cy; y<cy+10; y++){
			//	for(int z = cz; z<cz+10; z++){
			//		WorldGenerator.data[x][y][z] = 20;
			//	}
		//	}
	//	}
		
	}
}
