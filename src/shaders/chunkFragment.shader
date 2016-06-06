#version 400 core

in vec3 VColor;
in vec3 Normal;
in vec3 FragPos;
flat in int Index;
flat in float Prop;

out vec4 out_Color;

uniform vec3 lightPos;
uniform vec3 cameraPos;
uniform vec3 lightColor;

uniform float ambientStrength;
uniform float specularStrength;
uniform float shininess;

void main(void){
	if(Prop==0){
		discard;
	}
	vec3 norm = normalize(Normal);
	vec3 lightDir = normalize(lightPos-FragPos);
	vec3 viewDir = normalize(cameraPos - FragPos);
	vec3 reflectDir = reflect(-lightDir, norm);  
	float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
	vec3 specular = specularStrength * spec * lightColor;  
	float diff = max(dot(norm, lightDir), 0.0);
	vec3 diffuse = diff * lightColor;
	vec3 ambient = ambientStrength * lightColor;	
	vec3 result = (ambient+diffuse+specular)*VColor;
	out_Color = vec4(result,1.0f);
}