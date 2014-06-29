uniform mat4 g_ViewMatrix;
uniform mat4 g_ModelMatrix;
uniform mat4 g_ProjectionMatrix;

uniform mat4 BoneMatrices[40];

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec2 inTexCoord;
layout(location = 2) in vec3 inNormal;
layout(location = 4) in vec4 inIndex;
layout(location = 5) in vec4 inWeight;


#ifdef NORMAL_MAPPING
layout(location = 3) in vec4 inTangent;
out mat3 TBN;

#else
out vec3 normal;

#endif

#ifdef SHADOW_MAPPING
uniform mat4 depthBiasMVP;
out vec4 shadowCoord;
#endif

out vec4 position;
out vec2 texCoord;
out vec4 colorIn;

void main()
{
    mat4 mat = BoneMatrices[int(inIndex.x)] * inWeight.x;
	mat += BoneMatrices[int(inIndex.y)] * inWeight.y;
	mat += BoneMatrices[int(inIndex.z)] * inWeight.z;
	mat += BoneMatrices[int(inIndex.w)] * inWeight.w;
	
	mat3 rotMat = mat3(mat[0].xyz, mat[1].xyz, mat[2].xyz);
	//m44
	position = g_ViewMatrix * g_ModelMatrix * mat * vec4(inPosition, 1.0);
	//normal = normalize(vec3(g_ViewMatrix * g_ModelMatrix * vec4 (inNormal, 0.0)));
	
#ifdef NORMAL_MAPPING
	calculateTBN(rotMat * inNormal, vec4(rotMat * inTangent.xyz, inTangent.w), g_ViewMatrix, g_ModelMatrix, TBN);
#else
	normal = normalize(vec3(g_ViewMatrix * g_ModelMatrix * vec4 (rotMat * inNormal, 0.0)));
#endif
	
#ifdef SHADOW_MAPPING
	shadowCoord = mat4(0.5,0,0,0,
						0,0.5,0,0,
						0,0,0.5,0,
						0.5,0.5,0.5,1
						) * depthBiasMVP * g_ModelMatrix * mat * vec4(inPosition, 1.0);
#endif

    gl_Position = g_ProjectionMatrix * position;
	
	texCoord = inTexCoord;
	//colorIn = BoneMatrices[0][1];
	//colorIn.a = 1.0;
}
