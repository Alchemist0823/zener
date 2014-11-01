#version 330

in vec2 texCoord;
out vec4 colorOut;

uniform MaterialInfo Material;
uniform sampler2D Material_DiffuseMap;
uniform sampler2D Material_NormalMap;

void main(){
    colorOut = texture2D(Material_DiffuseMap, texCoord);
}