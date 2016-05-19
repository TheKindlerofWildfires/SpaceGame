#version 400 core

in vec3 position;

out vec3 colour;

uniform vec3 pos;
uniform float side;
uniform float apothem;
uniform float aspect;
uniform int hexesAcross;
uniform usamplerBuffer land;

void main(void){
	int yPos = gl_InstanceID/hexesAcross;
	int xPos = gl_InstanceID%hexesAcross;
	float xOfset = 1.2*(xPos*3*side/2);
	float yOfset;
	if(xPos%2==0){
		yOfset = 1.2*(yPos*2*aspect*apothem);
	} else{
		yOfset = 1.2*(aspect*(yPos*2*apothem-apothem));
	}
	uint land = texelFetch(land,gl_InstanceID).x;
	vec3 color;
	if(land==100){
		color = vec3(0,1,0);
	} else if(land ==50){
		color = vec3(1,0,1);
	} else{
		color = vec3(0,0,1);
	}
	gl_Position = vec4(position.x+pos.x+xOfset,position.y+pos.y-yOfset,position.z+pos.z, 1.0);
	colour = color;
}