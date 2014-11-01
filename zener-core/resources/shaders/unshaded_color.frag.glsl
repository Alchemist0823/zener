uniform mat4 g_ViewMatrix;
uniform vec4 MultipleColor;
uniform vec4 AddColor;
uniform MaterialInfo Material;
uniform sampler2D Material_DiffuseMap;
uniform sampler2D Material_NormalMap;

in vec4 position;
in vec2 texCoord;

#ifdef NORMAL_MAPPING
in mat3 TBN;
#else
in vec3 normal;
#endif

out vec4 colorOut;


void main(){

#ifdef NORMAL_MAPPING
    vec3 normal = normalize(TBN * normalize(texture2D(Material_NormalMap, texCoord).rgb * 2.0 - 1.0));
#endif

	colorOut = vec4(Material.Kd, 1.0) * MultipleColor + AddColor;
}