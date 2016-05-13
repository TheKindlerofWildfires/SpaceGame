#version 400 core

in vec3 position;

out vec3 colour;

uniform vec3 pos;
uniform float side;

void main(void){
	
	gl_Position = vec4(position.x+pos.x+(gl_InstanceID*side),position.y+pos.y,position.z+pos.z, 1.0);
	colour = vec3(position.x +0.5, 1.0, position.y+0.5);
	
}