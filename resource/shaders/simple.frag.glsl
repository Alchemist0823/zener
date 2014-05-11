#version 330

in vec2 texCoord;

out vec4 colorOut;
	
struct MaterialInfo {
	sampler2D DiffuseMap;
	vec3 Kd; // Diffuse reflectivity
};

uniform MaterialInfo Material;

void main(){
    colorOut = texture2D(Material.DiffuseMap, texCoord);
}