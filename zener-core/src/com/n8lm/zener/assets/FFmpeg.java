/*
 * Copyright (c) 2012, Riven
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Riven nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.n8lm.zener.assets;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import com.n8lm.zener.utils.RegexUtil;
import com.n8lm.zener.utils.StringConverter;
import com.n8lm.zener.utils.SystemInformation;
import craterstudio.io.Streams;
import craterstudio.streams.NullOutputStream;
import com.n8lm.zener.assets.VideoMetadata;

public class FFmpeg {

	public static String FFMPEG_PATH;
	public static boolean FFMPEG_VERBOSE = false;

	static {
		String resourceName = "./lib/ffmpeg/ffmpeg";
		if (SystemInformation.isMac) {
			resourceName += "-mac";
		} else {
			resourceName += SystemInformation.is64bit ? "64" : "32";
			if (SystemInformation.isWindows) {
				resourceName += ".exe";
			}
		}

		FFMPEG_PATH = resourceName;

		if (!new File(FFMPEG_PATH).exists()) {
			throw new IllegalStateException("Failed to find ffmpeg: " + new File(FFMPEG_PATH).getAbsolutePath());
		}
	}

	public static VideoMetadata extractMetadata(File srcMovieFile) throws IOException {
		Process process = new ProcessBuilder().command(//
		   FFMPEG_PATH, //
		   "-i", srcMovieFile.getAbsolutePath(),//
		   "-f", "null"//
		).start();
		Streams.asynchronousTransfer(process.getInputStream(), System.out, true, false);

		int width = -1;
		int height = -1;
		float framerate = -1;

		try {
			InputStream stderr = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(stderr));
			for (String line; (line = br.readLine()) != null;) {
				//System.out.println("ffmpeg: " + line);

				// Look for:
				// "	Stream #0:0: Video: vp6f, yuv420p, 320x240, 314 kb/s, 30 tbr, 1k tbn, 1k tbc"
				// ----------------------------------------------------------^

				if (line.trim().startsWith("Stream #") && line.contains("Video:")) {
					framerate = Float.parseFloat(RegexUtil.findFirst(line, Pattern.compile("\\s(\\d+(\\.\\d+)?)\\stbr,"), 1));
					int[] wh = StringConverter.parseInts(RegexUtil.find(line, Pattern.compile("\\s(\\d+)x(\\d+)[\\s,]"), 1, 2));
					width = wh[0];
					height = wh[1];
				}
			}

			if (framerate == -1) {
				throw new IllegalStateException("failed to find framerate of video");
			}
			return new VideoMetadata(width, height, framerate);
		} finally {
			Streams.safeClose(process);
		}
	}

	public static InputStream extractVideoAsRGB24(File srcMovieFile, int seconds) throws IOException {
		return streamData(new ProcessBuilder().command(//
		   FFMPEG_PATH, //
		   "-ss", String.valueOf(seconds), //
		   "-i", srcMovieFile.getAbsolutePath(), //		   
		   "-f", "rawvideo", //
		   "-pix_fmt", "rgb24", //
		   "-" //
		));
	}

	//

	public static InputStream extractAudioAsWAV(File srcMovieFile, int seconds) throws IOException {
		return streamData(new ProcessBuilder().command(//
		   FFMPEG_PATH, //
		   "-ss", String.valueOf(seconds), //
		   "-i", srcMovieFile.getAbsolutePath(), //
		   "-acodec", "pcm_s16le", //
		   "-ac", "2", //		    
		   "-f", "wav", //
		   "-" //
		));
	}

	//

	private static InputStream streamData(ProcessBuilder pb) throws IOException {
		Process process = pb.start();
		Streams.asynchronousTransfer(process.getErrorStream(), FFMPEG_VERBOSE ? System.err : new NullOutputStream(), true, false);
		return process.getInputStream();
	}
}
