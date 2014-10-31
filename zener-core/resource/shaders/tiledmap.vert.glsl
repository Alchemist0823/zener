uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;
layout(location = 6) in vec4 inColor;
layout(location = 7) in vec4 inTexIndices;


#ifdef NORMAL_MAPPING
layout(location = 3) in vec4 inTangent;
out mat3 TBN;

#else
out vec3 normal;

#endif

out vec4 position;
out vec4 color;
out vec2 texCoord;

#ifdef SHADOW_MAPPING
uniform mat4 depthBiasMVP;
out vec4 shadowCoord;
#endif
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
#ifdef NORMAL_MAPPING
	calculateTBN(inNormal, inTangent, g_ViewMatrix, g_ModelMatrix, TBN);
#else
	normal = normalize(vec3(g_ViewMatrix * g_ModelMatrix * vec4 (inNormal, 0.0)));
#endif
	
	gl_Position = g_ProjectionMatrix * position;
	
	texCoord = inTexCoord;
	//shadowCoord.x = shadowCoord.x / 2 + 0.5;
	//shadowCoord.y = shadowCoord.y / 2 + 0.5;
	
#ifdef SHADOW_MAPPING
	shadowCoord = depthBiasMVP * g_ModelMatrix * vec4(inPosition, 1.0);
#endif

	color = inColor;
}