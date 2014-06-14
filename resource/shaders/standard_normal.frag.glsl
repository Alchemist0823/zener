#version 330

uniform mat4 g_ViewMatrix;

in vec4 position;
in vec2 texCoord;
in mat3 TBN;

out vec4 colorOut;

uniform vec4 MultipleColor;
uniform vec4 AddColor;

struct LightInfo {
	vec4 Position; // Light position in eye coords.
	vec3 La; // Ambient light intensity
	vec3 Ld; // Diffuse light intensity
	vec3 Ls; // Specular light intensity
};

uniform LightInfo Light;
	
struct MaterialInfo {
	sampler2D DiffuseMap;
	sampler2D NormalMap;
	vec3 Ka; // Ambient reflectivity
	vec3 Kd; // Diffuse reflectivity
	vec3 Ks; // Specular reflectivity
	float Shininess; // Specular shininess factor
};

uniform MaterialInfo Material;

vec3 phongModel (vec4 position, vec3 norm, vec3 m_diffuse)
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
	vec3 spec = vec3(0.0);
	if( sDotN > 0.0 && Material.Shininess > 0.0)
		spec = Light.Ls * Material.Ks *	pow( max( dot(r,v), 0.0 ), 0.3 * Material.Shininess );
	
	return ambient + diffuse + spec;
}

void main(){
	vec3 normal = normalize(TBN * normalize(texture2D(Material.NormalMap, texCoord).rgb * 2.0 - 1.0));
    vec4 texColor = texture2D(Material.DiffuseMap, texCoord);
	if (texColor.a < 0.2)
		discard;
	//colorOut = texture2D(Material.NormalMap, texCoord);
	//colorOut = vec4(normal, 1.0) * MultipleColor + AddColor;//vec4(mix(m_Color.rgb, texColor.rgb, texColor.a), 1.0);
    //colorOut = position * MultipleColor + AddColor;//vec4(mix(m_Color.rgb, texColor.rgb, texColor.a), 1.0);
    
    //colorOut = texColor;
    //colorOut = vec4(TBN[0][0], TBN[1][0], TBN[2][0], 1.0);
    //colorOut = vec4(TBN[0][1], TBN[1][1], TBN[2][1], 1.0);
    //colorOut = vec4(TBN[0][2], TBN[1][2], TBN[2][2], 1.0);
    //colorOut = vec4(normal, 1.0);
    
    //colorOut = vec4(TBN[0][1], TBN[1][1], TBN[2][1], 1.0);
    //colorOut = vec4(TBN[0][2], TBN[1][2], TBN[2][2], 1.0);
    colorOut = vec4(phongModel(position, normal, texColor.rgb), 1.0) * MultipleColor + AddColor;//vec4(mix(m_Color.rgb, texColor.rgb, texColor.a), 1.0);
}