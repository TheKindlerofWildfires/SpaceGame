#version 400 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec3 normal;

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

layout(std140) uniform Properties{
	float properties[16*16*16];
};

void main(void){
	float prop = properties[gl_InstanceID];
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
<<<<<<< HEAD
	float zOfset=zPos*1.0f;
	if(prop == 1){
		VColor = vec3(.4,.6,.2);
	} else if(prop == 2){
		VColor = vec3(.6,.6,.4);
	} else if(prop == 3){
		VColor= vec3(.2,.4,.6);
=======
	uint land = texelFetch(land,gl_InstanceID).x;
	vec3 color;
	if(land==100){
		color = vec3(0,1,0);
	} else if(land ==50){
		color = vec3(1,0,1);
	} else if(land == 1){
		color = vec3(0.18,0.75,0.38);
	}else if(land == 2){
		color = vec3(0.3,0.3,0.12);
	}else if(land == 3){
		color = vec3(0.21,0.6,0.34);
	}else if(land == 4){
		color = vec3(0,0.3,0.12);
	}else if(land == 5){
		color = vec3(0.92,0.78,0.68);
	}else if(land == 6){
		color = vec3(0.98,0.70,0.60);
	}else if(land == 7){
		color = vec3(0.5,0.5,0.4);
	}else if(land == 8){
		color = vec3(0.3,0.3,0.3);
	}else if(land == 9){
		color = vec3(0.25,0.66,0.46);
	}else if(land == 10){
		color = vec3(0.1,0.36,0.1);
	}else if(land == 11){
		color = vec3(0.5,1,0.1);
	}else if(land == 12){
		color = vec3(0.36,0.43,0.36);
	}else if(land == 13){
		color = vec3(0.5,1,0.25);
	}else if(land == 14){
		color = vec3(0.5,0.9,0.5);
	}else if(land == 15){
		color = vec3(0.2,1,0.5);
	}else if(land == 16){
		color = vec3(0.8,0.5,0.5);
	}else if(land == 17){
		color = vec3(0.4,0.2,0.2);
	}else if(land == 18){
		color = vec3(.9,0.9,0.7);
	}else{
		color = vec3(0,0,1);
>>>>>>> playerRework
	}
    gl_Position = projection * view * model * vec4(position.x+xOfset,position.y+yOfset,position.z+zOfset, 1.0f);
	FragPos = vec3(model*vec4(position.x+xOfset,position.y+yOfset,position.z+zOfset, 1.0f));
	Normal = vec3(norm * vec4(normal,0));  
	Index = gl_InstanceID;
	Prop = prop;
}