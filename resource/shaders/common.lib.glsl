#version 330

struct LightInfo {
	vec4 Position; // Light position in eye coords.
	vec3 La; // Ambient light intensity
	vec3 Ld; // Diffuse light intensity
	vec3 Ls; // Specular light intensity
};
	
struct MaterialInfo {
	sampler2D   DiffuseMap;
	sampler2D   NormalMap;
	vec3        Ka; // Ambient reflectivity
	vec3        Kd; // Diffuse reflectivity
	vec3        Ks; // Specular reflectivity
	float 		Shininess; // Specular shininess factor
};


vec3 specModel (in vec4 position, in vec3 normal, in LightInfo light, in MaterialInfo material, mat4 g_ViewMatrix)
{
	vec3 lightDir;
	if (light.Position.w == 1)
		lightDir = normalize(vec3(g_ViewMatrix * light.Position - position));
	else
		lightDir = normalize(vec3(g_ViewMatrix * light.Position));
	vec3 viewDir = normalize(-position.xyz);
	vec3 reflectDir = reflect(-lightDir, normal);
	float sDotN = max(dot(lightDir, normal), 0.0);
	vec3 spec = vec3(0.0);
	if (sDotN > 0.0 && material.Shininess > 0.0)
		spec = light.Ls * material.Ks *	pow( max( dot(reflectDir, viewDir), 0.0 ), 0.3 * material.Shininess );
	return spec;
}

vec3 ambientModel (in vec3 m_diffuse, LightInfo light)
{
	vec3 ambient = light.La * (m_diffuse);
	return ambient;
}

vec3 diffuseModel (in vec4 position, in vec3 norm, in vec3 m_diffuse, in LightInfo light, mat4 g_ViewMatrix)
{
	vec3 lightDir;
	if (light.Position.w == 1)
		lightDir = normalize(vec3(g_ViewMatrix * light.Position - position));
	else
		lightDir = normalize(vec3(g_ViewMatrix * light.Position));
	float sDotN = max(dot(lightDir, norm), 0.0);
	vec3 diffuse = light.Ld * (m_diffuse)* sDotN;
	
	return diffuse;
}

#ifdef SHADOW_MAPPING
float shadowModel (in vec4 shadowCoord,in sampler2D depthMap)
{
	float visibility = 1.0;
	float depth;
	float y, x;
	for (y = -0.5 ; y <= 0.5 ; y += 1.0)
		for (x = -0.5 ; x <= 0.5 ; x += 1.0)
		{
			depth = textureProj(depthMap, shadowCoord + vec4(x * shadowCoord.w * 1 / 1024, y * shadowCoord.w * 1 / 1024, 0, 0));
			if (depth < (shadowCoord.z / shadowCoord.w - 0.001))
				visibility -= 1.0 / 4;
		}
	return visibility;
}
#endif


#ifdef NORMAL_MAPPING
void calculateTBN(in vec3 inNormal, in vec4 inTangent, in mat4 g_ViewMatrix, in mat4 g_ModelMatrix, out mat3 TBN) {
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
}
#endif

void alphaBlend (in vec4 src, in vec4 dst, out vec4 result) {
	result.a = src.a + dst.a * (1 - src.a);
	result.rgb = (src.rgb * src.a + dst.rgb * dst.a * (1 - src.a)) / result.a;
}