#version 400 core

in vec3 position;

out vec3 colour;

uniform vec3 pos;
uniform float side;
uniform float apothem;
uniform float aspect;
uniform int hexesAcross;	
uniform int hexesDown;

void main(void){
	int yPos = 0;
	int xPos = gl_InstanceID;
	while(xPos>hexesAcross){
		xPos-=hexesAcross;
		yPos++;
	}
	float xOfset = xPos*3*side;
	
	float yOfset = 0;
	gl_Position = vec4(position.x+pos.x+xOfset,position.y+pos.y-yOfset,position.z+pos.z, 1.0);
	colour = vec3(position.x +0.5, 1.0, position.y+0.5);
}