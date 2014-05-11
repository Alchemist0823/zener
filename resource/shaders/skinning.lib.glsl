// uniform mat4 BoneMatrices
// in 4 vec4 inIndex
// in 5 vec4 inWeight

// process inPosition outPosition
// process inNormal outNormal
#version 330

uniform mat4 BoneMatrices[30];

layout(location = 4) in vec4 inIndex;
layout(location = 5) in vec4 inWeight;

void skinning(in vec4 inPosition, in vec3 inNormal, out vec4 outPosition, out vec3 outNormal)
{
    mat4 mat = BoneMatrices[int(inIndex.x)] * inWeight.x;
	mat += BoneMatrices[int(inIndex.y)] * inWeight.y;
	mat += BoneMatrices[int(inIndex.z)] * inWeight.z;
	mat += BoneMatrices[int(inIndex.w)] * inWeight.w;
	
	mat3 rotMat = mat3(mat[0].xyz, mat[1].xyz, mat[2].xyz);
	
	outPosition = mat * inPosition;
	outNormal = rotMat * inNormal;
}
