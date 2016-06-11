#version 400 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;

out vec3 VColor;
out vec3 Normal;
out vec3 FragPos;
flat out int Index;
flat out float Prop;


uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform mat4 lookAt;
uniform mat4 norm;

uniform float apothem;
uniform float side;

uniform int chunkSize;

uniform int chunkX;
uniform int chunkY;

uniform vec3 normal;

void main(void){
	int xPos = (gl_InstanceID / chunkSize) % chunkSize + chunkX;
	int zPos = gl_InstanceID / (chunkSize * chunkSize);
	int yPos = gl_InstanceID % chunkSize + chunkY;
	
	float xOfset = 1.2*(xPos*3*side/2);
	float yOfset;
	if(xPos%2==0){
		yOfset = 1.2*(yPos*2*apothem);
	} else{
		yOfset = 1.2*((yPos*2*apothem-apothem));
	}
	float zOfset=zPos*1.0f;
	VColor = color;
    gl_Position = projection * view * model * vec4(position.x+xOfset,position.y+yOfset,position.z+zOfset, 1.0f);
	FragPos = vec3(model*vec4(position.x+xOfset,position.y+yOfset,position.z+zOfset, 1.0f));
	Normal = vec3(norm * vec4(normal,0));  
	Index = gl_InstanceID;
	Prop = 1;
}