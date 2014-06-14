#version 330

uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;
layout(location = 3) in vec4 inTangent;

out vec4 position;
out mat3 TBN;
out vec2 texCoord;

void main() {

	position = g_ViewMatrix * g_ModelMatrix * vec4 (inPosition, 1.0);
	//normal = normalize(vec3(g_ViewMatrix * g_ModelMatrix * vec4 (inNormal, 0.0)));
	
	vec3 tangent = normalize(inTangent.xyz);
	vec3 normal = normalize(inNormal);
	vec3 bitangent = cross(normal, tangent) * inTangent.w;
	
	mat3 normalMatrix = mat3(g_ViewMatrix * g_ModelMatrix);
	
	tangent = normalMatrix * tangent;
	bitangent = normalMatrix * bitangent;
	normal = normalMatrix * normal;

	TBN = transpose(mat3(
		tangent,
		bitangent,
		normal
		));
		
	//TBN = mat3(g_ViewMatrix * g_ModelMatrix) * TBN;
	
	gl_Position = g_ProjectionMatrix * position;
	
	texCoord = inTexCoord;
}