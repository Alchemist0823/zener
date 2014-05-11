#version 330

uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;
layout(location = 8) in vec3 inParticlePosition;
layout(location = 9) in float inParticleSize;
layout(location = 10) in vec4 inParticleColor;

out vec2 texCoord;
out vec4 particleColor;

void main() {
	gl_Position = g_ProjectionMatrix 
		* (g_ViewMatrix * g_ModelMatrix * vec4(inParticlePosition, 1.0) 
		+ vec4(inPosition.x * inParticleSize, inPosition.y * inParticleSize, 0.0, 0.0));
		
	/*gl_Position = g_ProjectionMatrix 
		* (g_ViewMatrix * g_ModelMatrix * vec4(inParticlePosition, 1.0) 
		+ vec4(inPosition.x * inParticleSize, inPosition.y * inParticleSize, 0.0, 0.0));
		*/
	texCoord = inTexCoord;
	particleColor = inParticleColor;
}