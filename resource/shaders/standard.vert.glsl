#version 330

uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;

out vec4 position;
out vec3 normal;
out vec2 texCoord;

void main() {

	position = g_ViewMatrix * g_ModelMatrix * vec4 (inPosition, 1.0);
	normal = normalize(vec3(g_ViewMatrix * g_ModelMatrix * vec4 (inNormal, 0.0)));
	
	gl_Position = g_ProjectionMatrix * position;
	
	texCoord = inTexCoord;
}