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

layout(std140) uniform Properties{
	float properties[16*16*16];
};

uniform vec3 normal;

void main(void){
	float prop = properties[gl_InstanceID];
	int xPos = gl_InstanceID % chunkSize;
	int yPos = (gl_InstanceID / chunkSize) % chunkSize;
	int zPos = gl_InstanceID / (chunkSize * chunkSize);
	
	float xOfset = 1.2*(xPos*3*side/2);
	float yOfset;
	if(xPos%2==0){
		yOfset = 1.2*(yPos*2*apothem);
	} else{
		yOfset = 1.2*((yPos*2*apothem-apothem));
	}
	float zOfset=zPos*1.0f;
if(prop==100){
		VColor = vec3(0,1,0);
	}else if(prop == 128){
		VColor = vec3(0,0,1);
	}else if(prop ==50){
		VColor = vec3(1,0,1);
	} else if(prop == 1){
		VColor = vec3(0.18,0.75,0.38);
	}else if(prop == 2){
		VColor = vec3(0.3,0.3,0.12);
	}else if(prop == 3){
		VColor = vec3(0.21,0.6,0.34);
	}else if(prop == 4){
		VColor = vec3(0,0.3,0.12);
	}else if(prop == 5){
		VColor = vec3(0.92,0.78,0.68);
	}else if(prop == 6){
		VColor = vec3(0.98,0.70,0.60);
	}else if(prop == 7){
		VColor = vec3(0.5,0.5,0.4);
	}else if(prop == 8){
		VColor = vec3(0.3,0.3,0.3);
	}else if(prop == 9){
		VColor = vec3(0.15,0.56,0.36);
	}else if(prop == 10){
		VColor = vec3(0,0.16,0.1);
	}else if(prop == 11){
		VColor = vec3(0.5,1,0.1);
	}else if(prop == 12){
		VColor = vec3(0.16,0.43,0.16);
	}else if(prop == 13){
		VColor = vec3(0.6,0.5,0.5);
	}else if(prop == 14){
		VColor = vec3(0.1,0.83,0.66);
	}else if(prop == 15){
		VColor = vec3(1,1,1);
	}else if(prop == 16){
		VColor = vec3(0.2,0.93,0.76);
	}else if(prop == 17){
		VColor = vec3(0.8,0.7,0.7);
	}else if(prop == 18){
		VColor = vec3(0.1,0.83,0.66);
	}else if(prop == 19){
		VColor = vec3(0.9,0.83,0.66);
	}else if(prop == 20){
		VColor = vec3(1,0.53,0.56);
	}else if(prop == 21){
		VColor = vec3(0.9,0.7,0.7);
	}else{
		VColor = vec3(1,1,1);
	}	
    gl_Position = projection * view * model * vec4(position.x+xOfset,position.y+yOfset,position.z+zOfset, 1.0f);
	FragPos = vec3(model*vec4(position.x+xOfset,position.y+yOfset,position.z+zOfset, 1.0f));
	Normal = vec3(norm * vec4(normal,0));  
	Index = gl_InstanceID;
	Prop = prop;
}