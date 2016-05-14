#version 400 core

in vec3 position;

out vec3 colour;

uniform vec3 pos;

void main(void){
	
	gl_Position = vec4(position+pos, 1.0);
	colour = vec3(0.2, 0.4, 1.0);
	
}