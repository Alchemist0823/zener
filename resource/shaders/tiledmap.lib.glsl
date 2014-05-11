
layout(location = 7) in vec4 inTexIndices;

flat out int texI[4];
flat out int texP[4];

void vertex() {
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
}


uniform mat4 g_ViewMatrix;

struct TileSetInfo {
	sampler2D texture[10];
};

void alphaBlend (in vec4 src, in vec4 dst, out vec4 result) {
	result.a = src.a + dst.a * (1 - src.a);
	result.rgb = (src.rgb * src.a + dst.rgb * dst.a * (1 - src.a)) / result.a;
}

void fragment(out resultColor) {
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
}