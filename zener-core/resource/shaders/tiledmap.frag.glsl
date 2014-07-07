in vec4 position;
#ifdef NORMAL_MAPPING
in mat3 TBN;
#else
in vec3 normal;
#endif
in vec2 texCoord;
in vec4 color;

flat in int texI[4];
flat in int texP[4];

out vec4 colorOut;

#ifdef SHADOW_MAPPING
in vec4 shadowCoord;
uniform sampler2D depthMap;
#endif

uniform mat4 g_ViewMatrix;
uniform mat4 g_ProjectionMatrix;

uniform vec4 MultipleColor;
uniform vec4 AddColor;

uniform LightInfo Light[10];
uniform int LightCount;
uniform vec3 La;
	
struct TileSetInfo {
	sampler2D texture[10];
	sampler2D normalMap[10];
};

uniform TileSetInfo TileSet;

int textureNum;

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
	
	for (int j = 0; j < 4; j ++) {
		pos += posc[texP[j]];
		
		if (j == 3 || texI[j] != texI[j+1]) {
			if (first == 0) {
				pos = 15;
				first = 1;
			}
			ttc.x = (pos % 4) * tw + tcx;
			ttc.y = (pos / 4) * th + tcy;
			resultColor = texture2D(TileSet.texture[texI[j]], ttc);
			if (resultColor.a > 0.2)
				textureNum = texI[j];
			alphaBlend(resultColor, texColor, texColor);
			pos = 0;
		}
	}
	return texColor;
}

void main(){
	
	vec4 texColor = getTextureColor();
	
#ifdef NORMAL_MAPPING
    vec3 normal = normalize(TBN * normalize(texture2D(TileSet.normalMap[textureNum], texCoord).rgb * 2.0 - 1.0));
#endif

	#ifdef SHADOW_MAPPING
	float visibility = shadowModel(shadowCoord, depthMap);
	#else
	float visibility = 1.0;
	#endif

    MaterialInfo m_material;
    m_material.Kd = vec3(texColor);
    m_material.Ka = vec3(texColor);
    m_material.Ks = vec3(0.0);

    vec3 totalLighting = La * m_material.Ka + lightingModel(position, normal, Light, LightCount, m_material, visibility);
    
	//vec4 positionOut = shadowCoord;
	//float depth2 = pow(depth, 64.0);
	//float depth2 = pow(shadowCoord.z / shadowCoord.w, 64);
	//float depth2 = pow(positionOut.z / positionOut.w, 64.0);
	//float depth2 = shadowCoord.x / shadowCoord.w;
	
	//colorOut = vec4(depth2, depth2, depth2, 1.0);
	colorOut = vec4(totalLighting, 1.0) * color * MultipleColor + AddColor;
}
