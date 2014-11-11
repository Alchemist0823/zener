uniform mat4 g_ViewMatrix;
uniform vec4 MultipleColor;
uniform vec4 AddColor;
uniform LightInfo Light[10];
uniform int LightCount;
uniform vec3 La;
uniform MaterialInfo Material;
uniform sampler2D Material_DiffuseMap;
uniform sampler2D Material_NormalMap;

in vec4 position;
in vec2 texCoord;

#ifdef NORMAL_MAPPING
in mat3 TBN;
#else
in vec3 normal;
#endif

#ifdef SHADOW_MAPPING
uniform sampler2DShadow depthMap;
in vec4 shadowCoord;
#endif

out vec4 colorOut;


void main(){

#ifdef NORMAL_MAPPING
    vec3 normal = normalize(TBN * normalize(texture2D(Material_NormalMap, texCoord).rgb * 2.0 - 1.0));
#endif

#ifdef SHADOW_MAPPING
	float visibility = shadowModel(shadowCoord, depthMap);
#else
	float visibility = 1.0;
#endif

    vec3 totalLighting = La * Material.Ka + lightingModel(position, normal, Light, LightCount, Material, visibility);

	colorOut = vec4(totalLighting, 1.0) * MultipleColor + AddColor;
}