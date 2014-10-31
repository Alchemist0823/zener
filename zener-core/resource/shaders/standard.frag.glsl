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
uniform sampler2D depthMap;
in vec4 shadowCoord;
#endif

out vec4 colorOut;


void main(){
    
#ifdef NORMAL_MAPPING
    vec3 normal = normalize(TBN * normalize(texture2D(Material_NormalMap, texCoord).rgb * 2.0 - 1.0));
#endif

    vec4 texColor = texture2D(Material_DiffuseMap, texCoord);
	if (texColor.a < 0.2)
		discard;
	
#ifdef SHADOW_MAPPING
	float visibility = shadowModel(shadowCoord, depthMap);
#else
	float visibility = 1.0;
#endif

    MaterialInfo m_material = Material;
    m_material.Kd = vec3(texColor);
    m_material.Ka = vec3(texColor);

    vec3 totalLighting = La * m_material.Ka + lightingModel(position, normal, Light, LightCount, m_material, visibility);

	colorOut = vec4(totalLighting, 1.0) * MultipleColor + AddColor;
}