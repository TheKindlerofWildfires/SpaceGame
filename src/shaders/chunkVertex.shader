#version 400 core

in vec3 position;

out vec3 colour;

uniform vec3 pos;
uniform float side;
uniform float apothem;
uniform float aspect;
uniform int chunkSize;
uniform int chunkX;
uniform int chunkY;
uniform usamplerBuffer land;

void main(void){
	int yPos = gl_InstanceID/chunkSize + chunkY;
	int xPos = gl_InstanceID%chunkSize + chunkX;
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
		color = vec3(0.15,0.56,0.36);
	}else if(land == 10){
		color = vec3(0,0.16,0.1);
	}else if(land == 11){
		color = vec3(0.5,1,0.1);
	}else if(land == 12){
		color = vec3(0.16,0.43,0.16);
	}else{
		color = vec3(0,0,1);
	}
	gl_Position = vec4(position.x*apothem+pos.x+xOfset,position.y*apothem+pos.y-yOfset,position.z*apothem+pos.z, 1.0);
	colour = color;
}