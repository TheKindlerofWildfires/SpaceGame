#version 400 core

in vec3 colour;
in vec2 UV;

out vec4 out_Color;

uniform sampler2D myTextureSampler;

void main(void){
	vec3 color = vec3(1,1,1);
	out_Color = vec4(colour , 1.0);
}