#version 330

uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;
uniform mat4 depthBiasMVP;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;
layout(location = 6) in vec4 inColor;
layout(location = 7) in vec4 inTexIndices;

out vec4 position;
out vec3 normal;
out vec4 color;
out vec2 texCoord;
out vec4 shadowCoord;
//flat out vec4 texIndices;
flat out int texI[4];
flat out int texP[4];

void main() {
	
	texI[0] = int(inTexIndices.x);
	texI[1] = int(inTexIndices.y);
	texI[2] = int(inTexIndices.z);
	texI[3] = int(inTexIndices.w);
	texP[0] = 3;
	texP[1] = 1;
	texP[2] = 0;
	texP[3] = 2;
	
	int tmp;
	
	for (int i = 0; i < 4; i ++)
		for (int j = 0; j < 3; j ++)
			if (texI[j] > texI[j+1])
			{
				tmp = texI[j];
				texI[j] = texI[j+1];
				texI[j+1] = tmp;
				
				tmp = texP[j];
				texP[j] = texP[j+1];
				texP[j+1] = tmp;
			}

	position = g_ViewMatrix * g_ModelMatrix * vec4 (inPosition, 1.0);
	shadowCoord = mat4(0.5,0,0,0,
						0,0.5,0,0,
						0,0,0.5,0,
						0.5,0.5,0.5,1
						) * depthBiasMVP * g_ModelMatrix * vec4(inPosition, 1.0);
	normal = normalize(vec3(g_ViewMatrix * g_ModelMatrix * vec4 (inNormal, 0.0)));
	
	gl_Position = g_ProjectionMatrix * position;
	
	texCoord = inTexCoord;
	//shadowCoord.x = shadowCoord.x / 2 + 0.5;
	//shadowCoord.y = shadowCoord.y / 2 + 0.5;
	
	color = inColor;
}