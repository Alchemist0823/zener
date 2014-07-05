uniform vec4 MultipleColor;
uniform vec4 AddColor;
uniform LightInfo Light;
uniform MaterialInfo Material;

in vec2 texCoord;
in vec4 particleColor;

out vec4 colorOut;

void main(){

    vec4 texColor = texture2D(Material.DiffuseMap, texCoord) * particleColor * vec4(Light.La + Light.Ld, 1.0);
	colorOut = texColor * MultipleColor + AddColor;
}