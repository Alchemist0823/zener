/*
 * This file is part of Zener.
 *
 * Zener is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * Zener is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with Zener.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package com.n8lm.zener.assets;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.n8lm.zener.assets.IQMModel.Header;

public class IQMLoader {

	public IQMLoader() {
		// TODO Auto-generated constructor stub
	}

    public static Mesh loadTexturedModel(InputStream input) throws IOException {
    	BufferedInputStream reader = new BufferedInputStream(input);
    	
        byte[] result = new byte[Header.size];
        int totalBytesRead = 0;
        while(totalBytesRead < result.length){
          int bytesRemaining = result.length - totalBytesRead;
          //input.read() returns -1, 0, or more :
          int bytesRead = reader.read(result, totalBytesRead, bytesRemaining); 
          if (bytesRead > 0){
            totalBytesRead = totalBytesRead + bytesRead;
          }
        }
		return null;
    	
        
        
    }
    
    private static Mesh loadHeader(BufferedInputStream reader) {
		return null;
    	
    }
    
/*
bool loadiqmmeshes(const char *filename, const iqmheader &hdr, uchar *buf)
{
    if(meshdata) return false;

    lilswaplilswap((uint *)&buf[hdr.ofs_vertexarrays], hdr.num_vertexarrays*sizeof(iqmvertexarray)/sizeof(uint));
    lilswap((uint *)&buf[hdr.ofs_triangles], hdr.num_triangles*sizeof(iqmtriangle)/sizeof(uint));
    lilswap((uint *)&buf[hdr.ofs_meshes], hdr.num_meshes*sizeof(iqmmesh)/sizeof(uint));
    lilswap((uint *)&buf[hdr.ofs_joints], hdr.num_joints*sizeof(iqmjoint)/sizeof(uint));
    if(hdr.ofs_adjacency) lilswap((uint *)&buf[hdr.ofs_adjacency], hdr.num_triangles*sizeof(iqmtriangle)/sizeof(uint));

    meshdata = buf;
    nummeshes = hdr.num_meshes;
    numtris = hdr.num_triangles;
    numverts = hdr.num_vertexes;
    numjoints = hdr.num_joints;
    outposition = new float[3*numverts];
    outnormal = new float[3*numverts];
    outtangent = new float[3*numverts];
    outbitangent = new float[3*numverts];
    outframe = new Matrix3x4[hdr.num_joints];
    textures = new GLuint[nummeshes];
    memset(textures, 0, nummeshes*sizeof(GLuint));

    const char *str = hdr.ofs_text ? (char *)&buf[hdr.ofs_text] : "";
    iqmvertexarray *vas = (iqmvertexarray *)&buf[hdr.ofs_vertexarrays];
    for(int i = 0; i < (int)hdr.num_vertexarrays; i++)
    {
        iqmvertexarray &va = vas[i];
        switch(va.type)
        {
        case IQM_POSITION: if(va.format != IQM_FLOAT || va.size != 3) return false; inposition = (float *)&buf[va.offset]; lilswap(inposition, 3*hdr.num_vertexes); break;
        case IQM_NORMAL: if(va.format != IQM_FLOAT || va.size != 3) return false; innormal = (float *)&buf[va.offset]; lilswap(innormal, 3*hdr.num_vertexes); break;
        case IQM_TANGENT: if(va.format != IQM_FLOAT || va.size != 4) return false; intangent = (float *)&buf[va.offset]; lilswap(intangent, 4*hdr.num_vertexes); break;
        case IQM_TEXCOORD: if(va.format != IQM_FLOAT || va.size != 2) return false; intexcoord = (float *)&buf[va.offset]; lilswap(intexcoord, 2*hdr.num_vertexes); break;
        case IQM_BLENDINDEXES: if(va.format != IQM_UBYTE || va.size != 4) return false; inblendindex = (uchar *)&buf[va.offset]; break;
        case IQM_BLENDWEIGHTS: if(va.format != IQM_UBYTE || va.size != 4) return false; inblendweight = (uchar *)&buf[va.offset]; break;
        case IQM_COLOR: if(va.format != IQM_UBYTE || va.size != 4) return false; incolor = (uchar *)&buf[va.offset]; break;
        }
    }
    tris = (iqmtriangle *)&buf[hdr.ofs_triangles];
    meshes = (iqmmesh *)&buf[hdr.ofs_meshes];
    joints = (iqmjoint *)&buf[hdr.ofs_joints];
    if(hdr.ofs_adjacency) adjacency = (iqmtriangle *)&buf[hdr.ofs_adjacency];

    baseframe = new Matrix3x4[hdr.num_joints];
    inversebaseframe = new Matrix3x4[hdr.num_joints];
    for(int i = 0; i < (int)hdr.num_joints; i++)
    {
        iqmjoint &j = joints[i];
        baseframe[i] = Matrix3x4(Quat(j.rotate).normalize(), Vec3(j.translate), Vec3(j.scale));
        inversebaseframe[i].invert(baseframe[i]);
        if(j.parent >= 0) 
        {
            baseframe[i] = baseframe[j.parent] * baseframe[i];
            inversebaseframe[i] *= inversebaseframe[j.parent];
        }
    }

    for(int i = 0; i < (int)hdr.num_meshes; i++)
    {
        iqmmesh &m = meshes[i];
        printf("%s: loaded mesh: %s\n", filename, &str[m.name]);
        textures[i] = loadtexture(&str[m.material], 0);
        if(textures[i]) printf("%s: loaded material: %s\n", filename, &str[m.material]);
    }

    return true;
}

bool loadiqmanims(const char *filename, const iqmheader &hdr, uchar *buf)
{
    if((int)hdr.num_poses != numjoints) return false;

    if(animdata)
    {
        if(animdata != meshdata) delete[] animdata;
        delete[] frames;
        animdata = NULL;
        anims = NULL;
        frames = 0;
        numframes = 0;
        numanims = 0;
    }        

    lilswap((uint *)&buf[hdr.ofs_poses], hdr.num_poses*sizeof(iqmpose)/sizeof(uint));
    lilswap((uint *)&buf[hdr.ofs_anims], hdr.num_anims*sizeof(iqmanim)/sizeof(uint));
    lilswap((ushort *)&buf[hdr.ofs_frames], hdr.num_frames*hdr.num_framechannels);
    if(hdr.ofs_bounds) lilswap((uint *)&buf[hdr.ofs_bounds], hdr.num_frames*sizeof(iqmbounds)/sizeof(uint));

    animdata = buf;
    numanims = hdr.num_anims;
    numframes = hdr.num_frames;

    const char *str = hdr.ofs_text ? (char *)&buf[hdr.ofs_text] : "";
    anims = (iqmanim *)&buf[hdr.ofs_anims];
    poses = (iqmpose *)&buf[hdr.ofs_poses];
    frames = new Matrix3x4[hdr.num_frames * hdr.num_poses];
    ushort *framedata = (ushort *)&buf[hdr.ofs_frames];
    if(hdr.ofs_bounds) bounds = (iqmbounds *)&buf[hdr.ofs_bounds];

    for(int i = 0; i < (int)hdr.num_frames; i++)
    {
        for(int j = 0; j < (int)hdr.num_poses; j++)
        {
            iqmpose &p = poses[j];
            Quat rotate;
            Vec3 translate, scale;
            translate.x = p.channeloffset[0]; if(p.mask&0x01) translate.x += *framedata++ * p.channelscale[0];
            translate.y = p.channeloffset[1]; if(p.mask&0x02) translate.y += *framedata++ * p.channelscale[1];
            translate.z = p.channeloffset[2]; if(p.mask&0x04) translate.z += *framedata++ * p.channelscale[2];
            rotate.x = p.channeloffset[3]; if(p.mask&0x08) rotate.x += *framedata++ * p.channelscale[3];
            rotate.y = p.channeloffset[4]; if(p.mask&0x10) rotate.y += *framedata++ * p.channelscale[4];
            rotate.z = p.channeloffset[5]; if(p.mask&0x20) rotate.z += *framedata++ * p.channelscale[5];
            rotate.w = p.channeloffset[6]; if(p.mask&0x40) rotate.w += *framedata++ * p.channelscale[6];
            scale.x = p.channeloffset[7]; if(p.mask&0x80) scale.x += *framedata++ * p.channelscale[7];
            scale.y = p.channeloffset[8]; if(p.mask&0x100) scale.y += *framedata++ * p.channelscale[8];
            scale.z = p.channeloffset[9]; if(p.mask&0x200) scale.z += *framedata++ * p.channelscale[9];
            // Concatenate each pose with the inverse base pose to avoid doing this at animation time.
            // If the joint has a parent, then it needs to be pre-concatenated with its parent's base pose.
            // Thus it all negates at animation time like so: 
            //   (parentPose * parentInverseBasePose) * (parentBasePose * childPose * childInverseBasePose) =>
            //   parentPose * (parentInverseBasePose * parentBasePose) * childPose * childInverseBasePose =>
            //   parentPose * childPose * childInverseBasePose
            Matrix3x4 m(rotate.normalize(), translate, scale);
            if(p.parent >= 0) frames[i*hdr.num_poses + j] = baseframe[p.parent] * m * inversebaseframe[j];
            else frames[i*hdr.num_poses + j] = m * inversebaseframe[j];
        }
    }
 
    for(int i = 0; i < (int)hdr.num_anims; i++)
    {
        iqmanim &a = anims[i];
        printf("%s: loaded anim: %s\n", filename, &str[a.name]);
    }
    
    return true;
}

bool loadiqm(const char *filename)
{
    FILE *f = fopen(filename, "rb");
    if(!f) return false;

    uchar *buf = NULL;
    iqmheader hdr;
    if(fread(&hdr, 1, sizeof(hdr), f) != sizeof(hdr) || memcmp(hdr.magic, IQM_MAGIC, sizeof(hdr.magic)))
        goto error;
    lilswap(&hdr.version, (sizeof(hdr) - sizeof(hdr.magic))/sizeof(uint));
    if(hdr.version != IQM_VERSION)
        goto error;
    if(hdr.filesize > (16<<20)) 
        goto error; // sanity check... don't load files bigger than 16 MB
    buf = new uchar[hdr.filesize];
    if(fread(buf + sizeof(hdr), 1, hdr.filesize - sizeof(hdr), f) != hdr.filesize - sizeof(hdr))
        goto error;

    if(hdr.num_meshes > 0 && !loadiqmmeshes(filename, hdr, buf)) goto error;
    if(hdr.num_anims > 0 && !loadiqmanims(filename, hdr, buf)) goto error;
 
    fclose(f);
    return true;

error:
    printf("%s: error while loading\n", filename);
    if(buf != meshdata && buf != animdata) delete[] buf;
    fclose(f);
    return false;
}*/
}
