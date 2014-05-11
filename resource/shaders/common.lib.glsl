vec3 phongModel (in vec4 position, in vec3 normal, in vec3 basecolor)
{
	vec3 lightDir;
	if (Light.Position.w == 1)
		lightDir = normalize(vec3(g_ViewMatrix * Light.Position - position));
	else
		lightDir = normalize(vec3(g_ViewMatrix * Light.Position));
	vec3 viewDir = normalize(-position.xyz);
	vec3 reflectDir = reflect(-lightDir, normal);
	float sDotN = max(dot(lightDir, normal, 0.0);
	vec3 spec = vec3(0.0);
	if (sDotN > 0.0 && Material.Shininess > 0.0)
		spec = Light.Ls * Material.Ks *	pow( max( dot(reflectDir, viewDir), 0.0 ), 0.3 * Material.Shininess );
	return spec;
}

vec3 ambientModel (in vec3 basecolor)
{
	vec3 ambient = Light.La * (basecolor);	
	return ambient;
}

vec3 diffuseModel (in vec4 position, in vec3 normal, in vec3 basecolor)
{
	vec3 lightDir;
	if (Light.Position.w == 1)
		lightDir = normalize(vec3(g_ViewMatrix * Light.Position - position));
	else
		lightDir = normalize(vec3(g_ViewMatrix * Light.Position));
	float sDotN = max(dot(lightDir, normal), 0.0);
	vec3 diffuse = Light.Ld * (basecolor)* sDotN;
	
	return diffuse;
}


//<variable> uniform sampler2D depthMap; </>

float shadowModel (in vec4 shadowCoord)
{
	vec4 positionOut = mat4(0.5,0,0,0,
							0,0.5,0,0,
							0,0,0.5,0,
							0.5,0.5,0.5,1
							) * shadowCoord;
	float visibility = 1.0;
	float depth;
	float y, x;
	for (y = -0.5 ; y <= 0.5 ; y += 1.0)
		for (x = -0.5 ; x <= 0.5 ; x += 1.0)
		{
			depth = textureProj(depthMap, positionOut + vec4(x * positionOut.w * 1 / 1024, y * positionOut.w * 1 / 1024, 0, 0));
			if (depth < (positionOut.z / positionOut.w - 0.001))
				visibility -= 1.0 / 4;
		}
	return visibility;
}

void alphaBlend (in vec4 src, in vec4 dst, out vec4 result) {
	result.a = src.a + dst.a * (1 - src.a);
	result.rgb = (src.rgb * src.a + dst.rgb * dst.a * (1 - src.a)) / result.a;
}