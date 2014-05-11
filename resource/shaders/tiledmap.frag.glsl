#version 330

in vec4 position;
in vec3 normal;
in vec2 texCoord;
in vec4 color;
flat in int texI[4];
flat in int texP[4];

out vec4 colorOut;

uniform mat4 g_ViewMatrix;

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

vec3 lightModel (vec4 position, vec3 norm, vec3 m_diffuse)
{
	vec3 s;
	if (Light.Position.w == 1)
		s = normalize(vec3(g_ViewMatrix * Light.Position - position));
	else
		s = normalize(vec3(g_ViewMatrix * Light.Position));
	vec3 v = normalize(-position.xyz);
	vec3 r = reflect( -s, norm );
	vec3 ambient = Light.La * (m_diffuse);
	float sDotN = max( dot(s,norm), 0.0 );
	vec3 diffuse = Light.Ld * (m_diffuse)* sDotN;
	
	return ambient + diffuse;
}

void alphaBlend (in vec4 src, in vec4 dst, out vec4 result) {
	result.a = src.a + dst.a * (1 - src.a);
	result.rgb = (src.rgb * src.a + dst.rgb * dst.a * (1 - src.a)) / result.a;
}

void main(){
	
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
	
    colorOut = vec4(lightModel(position, normal, texColor.rgb), 1.0) * color * MultipleColor + AddColor;
}
