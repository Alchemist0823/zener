uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;
uniform int AtlasRowCount;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;
layout(location = 7) in float inTexIndex;
layout(location = 8) in vec3 inParticlePosition;
layout(location = 9) in float inParticleSize;
layout(location = 10) in vec4 inParticleColor;
layout(location = 15) in vec4 inParticleRot;


out vec2 texCoord;
out vec4 particleColor;

void main() {
	float cosRot = cos( inParticleRot );
	float sinRot = sin( inParticleRot );

	gl_Position = g_ProjectionMatrix 
		* (g_ViewMatrix * g_ModelMatrix * vec4(inParticlePosition, 1.0) 
		+ vec4((cosRot * inPosition.x - sinRot * inPosition.y) * inParticleSize,
		       (sinRot * inPosition.x + cosRot * inPosition.y) * inParticleSize, 0.0, 0.0));

	int num = AtlasRowCount;
	int texIndex = int(inTexIndex);
	texCoord.x = (texIndex % num) * (1.0 / num);
	texCoord.y = (texIndex / num) * (1.0 / num);
	/*gl_Position = g_ProjectionMatrix 
		* (g_ViewMatrix * g_ModelMatrix * vec4(inParticlePosition, 1.0) 
		+ vec4(inPosition.x * inParticleSize, inPosition.y * inParticleSize, 0.0, 0.0));
		*/
	texCoord += inTexCoord / num;
	particleColor = inParticleColor;
}