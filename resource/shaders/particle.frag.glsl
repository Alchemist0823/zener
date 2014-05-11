#version 330

in vec2 texCoord;
in vec4 particleColor;

out vec4 colorOut;
	
struct MaterialInfo {
	sampler2D DiffuseMap;
};

uniform MaterialInfo Material;

void main(){
    colorOut = texture2D(Material.DiffuseMap, texCoord) * particleColor;
}