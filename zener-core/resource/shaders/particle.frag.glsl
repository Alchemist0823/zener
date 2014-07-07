uniform vec4 MultipleColor;
uniform vec4 AddColor;
uniform vec3 La;
uniform MaterialInfo Material;

in vec2 texCoord;
in vec4 particleColor;

out vec4 colorOut;

void main(){

    vec4 texColor = texture2D(Material.DiffuseMap, texCoord) * particleColor * vec4(La, 1.0);
	colorOut = texColor * MultipleColor + AddColor;
}