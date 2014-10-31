uniform vec4 MultipleColor;
uniform vec4 AddColor;
uniform vec3 La;
uniform MaterialInfo Material;
uniform sampler2D Material_DiffuseMap;
uniform sampler2D Material_NormalMap;


in vec2 texCoord;
in vec4 particleColor;

out vec4 colorOut;

void main(){

    vec4 texColor = texture2D(Material_DiffuseMap, texCoord) * particleColor/* * vec4(La, 1.0)*/;
	colorOut = texColor * MultipleColor + AddColor;
}