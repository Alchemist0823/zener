#version 330

in vec2 texCoord;
out vec4 colorOut;

uniform MaterialInfo Material;

void main(){
    colorOut = texture2D(Material.DiffuseMap, texCoord);
}