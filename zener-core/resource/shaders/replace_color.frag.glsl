uniform mat4 g_ViewMatrix;
uniform vec4 MultipleColor;
uniform vec4 AddColor;
uniform LightInfo Light;
uniform MaterialInfo Material;
uniform vec3 ReplaceColor;

in vec4 position;
in vec2 texCoord;

#ifdef NORMAL_MAPPING
in mat3 TBN;
#else
in vec3 normal;
#endif

#ifdef SHADOW_MAPPING
uniform sampler2D depthMap;
in vec4 shadowCoord;
#endif

out vec4 colorOut;


void main(){

#ifdef NORMAL_MAPPING
    vec3 normal = normalize(TBN * normalize(texture2D(Material.NormalMap, texCoord).rgb * 2.0 - 1.0));
#endif

    vec4 texColor = texture2D(Material.DiffuseMap, texCoord);
    texColor = vec4(texColor.rgb * texColor.a + ReplaceColor.rgb * (1 - texColor.a), 1.0);

	vec3 colorLight = ambientModel(texColor.rgb, Light);

#ifdef SHADOW_MAPPING
	float visibility = shadowModel(shadowCoord, depthMap);
#else
	float visibility = 1.0;
#endif

	if (visibility > 0)
		colorLight += (diffuseModel(position, normal, texColor.rgb, Light, g_ViewMatrix) + specModel(position, normal, Light, Material, g_ViewMatrix)) * visibility;

	colorOut = vec4(colorLight, 1.0) * MultipleColor + AddColor;
}