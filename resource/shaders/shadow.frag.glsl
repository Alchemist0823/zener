#version 330

uniform mat4 g_ViewMatrix;

in vec4 position;
in vec2 texCoord;
in vec3 normal;

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
    float texColor = texture2D(Material.DiffuseMap, texCoord);
    texColor = pow(texColor, 64.0 );
	//if (texColor.a < 0.2)
	//	discard;
	//colorOut =  texColor;
    colorOut =  vec4(texColor, texColor, texColor, 1.0);
}