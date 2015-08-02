uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;
layout(location = 10) in vec4 inParticleColor;
layout(location = 16) in mat4 inParticleMatrix;
layout(location = 17) in vec4 inParticleCoord;

out vec2 texCoord;
out vec4 particleColor;

void main() {
	gl_Position = g_ProjectionMatrix
		* g_ViewMatrix * g_ModelMatrix * inParticleMatrix * vec4(inPosition, 1.0);

	texCoord = inTexCoord * (inParticleCoord.zw - inParticleCoord.xy) + inParticleCoord.xy;
	particleColor = inParticleColor;
}
