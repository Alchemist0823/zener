#version 330

in vec4 position;
in vec3 normal;
in vec2 texCoord;
in vec4 color;
in vec4 shadowCoord;
flat in int texI[4];
flat in int texP[4];

out vec4 colorOut;

uniform sampler2D depthMap;
uniform mat4 g_ViewMatrix;
uniform mat4 g_ProjectionMatrix;

uniform vec4 MultipleColor;
uniform vec4 AddColor;

struct LightInfo {
	vec4 Position; // Light position in eye coords.
	vec3 La; // Ambient light intensity
	vec3 Ld; // Diffuse light intensity
	vec3 Ls; // Specular light intensity
};
uniform LightInfo Light;
	
struct TileSetInfo {
	sampler2D texture[10];
};

uniform TileSetInfo TileSet;

vec3 ambientModel (in vec3 m_diffuse)
{
	vec3 ambient = Light.La * (m_diffuse);	
	return ambient;
}

vec3 diffuseModel (in vec4 position, in vec3 norm, in vec3 m_diffuse)
{
	vec3 lightDir;
	if (Light.Position.w == 1)
		lightDir = normalize(vec3(g_ViewMatrix * Light.Position - position));
	else
		lightDir = normalize(vec3(g_ViewMatrix * Light.Position));
	float sDotN = max(dot(lightDir, norm), 0.0);
	vec3 diffuse = Light.Ld * (m_diffuse)* sDotN;
	
	return diffuse;
}

float shadowModel (in vec4 shadowCoord)
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

void alphaBlend (in vec4 src, in vec4 dst, out vec4 result) {
	result.a = src.a + dst.a * (1 - src.a);
	result.rgb = (src.rgb * src.a + dst.rgb * dst.a * (1 - src.a)) / result.a;
}

vec4 getTextureColor() {
	
	float th = 1.0 / 4;
	float tw = 1.0 / 8;
	float tcx = texCoord.x / 8;
	float tcy = texCoord.y / 4;
	
	const int posc[4] = int[](1, 2, 4, 8);
	//const int posc[4] = int[](8, 2, 1, 4);
			
	vec4 texColor = vec4(0.0, 0.0, 0.0, 0.0);
	vec4 resultColor;
	vec2 ttc;
	
	int pos = 0;
	int first = 0;
	
	for (int j = 0; j < 4; j ++)
	{
		pos += posc[texP[j]];
		
		if (j == 3 || texI[j] != texI[j+1])
		{
			if (first == 0)
			{
				pos = 15;
				first = 1;
			}
			ttc.x = (pos % 4) * tw + tcx;
			ttc.y = (pos / 4) * th + tcy;
			resultColor = texture2D(TileSet.texture[texI[j]], ttc);
			alphaBlend(resultColor, texColor, texColor);
			pos = 0;
		}
	}
	return texColor;
}

void main(){
	
	vec4 texColor = getTextureColor();
	if (texColor.a < 0.1)
		discard;
	vec3 colorLight = ambientModel(texColor.rgb);
	
	float visibility = shadowModel(shadowCoord);
	
	if (visibility > 0)
		colorLight += diffuseModel(position, normal, texColor.rgb) * visibility;
    
	//vec4 positionOut = shadowCoord;
	//float depth2 = pow(depth, 64.0);
	//float depth2 = pow(shadowCoord.z / shadowCoord.w, 64);
	//float depth2 = pow(positionOut.z / positionOut.w, 64.0);
	//float depth2 = shadowCoord.x / shadowCoord.w;
	
	//colorOut = vec4(depth2, depth2, depth2, 1.0);
	colorOut = vec4(colorLight, 1.0) * color * MultipleColor + AddColor;
}
