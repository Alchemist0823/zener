in vec4 position;
in vec2 texCoord[4];
in vec4 color;

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

uniform mat4 g_ViewMatrix;
uniform vec4 MultipleColor;
uniform vec4 AddColor;
uniform LightInfo Light[10];
uniform int LightCount;
uniform vec3 La;

uniform sampler2D texture[4];
uniform sampler2D normalMap[4];

void main(){

#ifdef NORMAL_MAPPING
    vec3 normal = normalize(TBN * normalize(texture2D(normal[i], texCoord).rgb * 2.0 - 1.0));
#endif

    vec4 texColor = vec4(0.0, 0.0, 0.0, 0.0);

    int i = 0;
    while (texCoord[i].x >= 0 && i < 4) {
        vec4 resultColor = texture2D(texture[i], texCoord[i]);
	    alphaBlend(resultColor, texColor, texColor);
        i ++;
    }

#ifdef SHADOW_MAPPING
	float visibility = shadowModel(shadowCoord, depthMap);
#else
	float visibility = 1.0;
#endif

    MaterialInfo m_material;
    m_material.Kd = vec3(texColor);
    m_material.Ka = vec3(texColor);
    m_material.Ks = vec3(0.0);

    vec3 totalLighting = La * m_material.Ka + lightingModel(position, normal, Light, LightCount, m_material, visibility);

	colorOut = vec4(totalLighting, 1.0) * color * MultipleColor + AddColor;
}