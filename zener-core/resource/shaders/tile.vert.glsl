uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;

layout(location = 0) in vec3 inPosition;
layout(location = 2) in vec3 inNormal;
layout(location = 6) in vec4 inColor;
layout(location = 11) in vec2 inTexCoord1;
layout(location = 12) in vec2 inTexCoord2;
layout(location = 13) in vec2 inTexCoord3;
layout(location = 14) in vec2 inTexCoord4;

#ifdef NORMAL_MAPPING
layout(location = 3) in vec4 inTangent;
out mat3 TBN;

#else
out vec3 normal;

#endif

out vec4 position;
out vec2 texCoord[4];
out vec4 color;

#ifdef SHADOW_MAPPING
uniform mat4 depthBiasMVP;
out vec4 shadowCoord;
#endif

void main() {

	position = g_ViewMatrix * g_ModelMatrix * vec4 (inPosition, 1.0);

#ifdef NORMAL_MAPPING
	calculateTBN(inNormal, inTangent, g_ViewMatrix, g_ModelMatrix, TBN);
#else
	normal = normalize(vec3(g_ViewMatrix * g_ModelMatrix * vec4 (inNormal, 0.0)));
#endif

	gl_Position = g_ProjectionMatrix * position;

	texCoord[0] = inTexCoord1;
	texCoord[1] = inTexCoord2;
	texCoord[2] = inTexCoord3;
	texCoord[3] = inTexCoord4;
	color = inColor;

#ifdef SHADOW_MAPPING
	shadowCoord = mat4(0.5,0,0,0,
						0,0.5,0,0,
						0,0,0.5,0,
						0.5,0.5,0.5,1
						) * depthBiasMVP * g_ModelMatrix * vec4(inPosition, 1.0);
#endif
}