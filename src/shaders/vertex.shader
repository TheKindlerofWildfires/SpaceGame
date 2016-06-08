#version 400 core

layout(location=0) in vec3 position;
layout(location=1) in vec3 offset;

out vec3 colour;

uniform vec3 pos;

void main(void){
	
	gl_Position = vec4(position+offset, 1.0);
	colour = vec3(1,1,1);
	
}