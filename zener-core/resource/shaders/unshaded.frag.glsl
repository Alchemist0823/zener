uniform mat4 g_ViewMatrix;
uniform vec4 MultipleColor;
uniform vec4 AddColor;
uniform MaterialInfo Material;

in vec4 position;
in vec2 texCoord;

#ifdef NORMAL_MAPPING
in mat3 TBN;
#else
in vec3 normal;
#endif

out vec4 colorOut;


void main(){

#ifdef NORMAL_MAPPING
    vec3 normal = normalize(TBN * normalize(texture2D(Material.NormalMap, texCoord).rgb * 2.0 - 1.0));
#endif

    vec4 texColor = texture2D(Material.DiffuseMap, texCoord);
	if (texColor.a < 0.2)
		discard;

	colorOut = texColor * MultipleColor + AddColor;
}