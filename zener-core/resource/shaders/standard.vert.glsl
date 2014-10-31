uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;

#ifdef NORMAL_MAPPING
layout(location = 3) in vec4 inTangent;
out mat3 TBN;

#else
out vec3 normal;

#endif

out vec4 position;
out vec2 texCoord;

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
	
	texCoord = inTexCoord;
#ifdef SHADOW_MAPPING
	shadowCoord = depthBiasMVP * g_ModelMatrix * vec4(inPosition, 1.0);
#endif
}