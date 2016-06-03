#version 400 core

in vec3 position;

out vec3 colour;

uniform vec3 pos;
uniform float side;
uniform float apothem;
uniform float aspect;
uniform int death;
uniform int xcoord;
uniform int ycoord;

void main(void){
	int xPos = xcoord;
	int yPos = ycoord;
	float xOfset = 1.2*(xPos*3*side/2);
	float yOfset;
	if(xPos%2==0){
		yOfset = 1.2*(yPos*2*aspect*apothem);
	} else{
		yOfset = 1.2*(aspect*(yPos*2*apothem-apothem));
	}
	vec3 color;
	if(death==1){
		color = vec3(0.2,.2f,.2f);
	} else{
		color = vec3(1,0,0);
	}
	gl_Position = vec4(position.x*apothem+pos.x+xOfset,position.y*apothem+pos.y-yOfset,position.z+pos.z, 1.0);
	colour = color;
}