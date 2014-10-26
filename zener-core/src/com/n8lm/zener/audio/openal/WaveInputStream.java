package com.n8lm.zener.audio.openal;

/**
 * Created on 2014/10/25.
 *
 * @author Alchemist
 */

import java.io.*;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class WaveInputStream extends InputStream implements AudioInputStream {
    public int audioFormat;
    public int numChannels;
    public int sampleRate;
    public int byteRate;
    private int blockAlign;
    public int bytesPerSample;

    private boolean endOfStream;

    public final DataInputStream input;
    public int sampleCount;

    public WaveInputStream() throws IOException {
        this.input = new DataInputStream(new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }

            @Override
            public int available() throws IOException {
                return 1;
            }
        });

        this.numChannels = 2;
        this.sampleRate = 44100;
        this.blockAlign = 4;
        this.bytesPerSample = 2;
        this.byteRate = sampleRate * numChannels * bytesPerSample;
        this.endOfStream = false;
    }

    public WaveInputStream(InputStream input) throws IOException {
        this.input = new DataInputStream(input);

        this.initWAV();
    }

    private void initWAV() throws IOException {

        endOfStream = false;
        while (true) {
            String chunkName = readString(this.input, 4);
            int chunkSize = swap32(this.input.readInt());

            // System.out.println("WAV chunk: [" + chunkName + "] size=" +
            // chunkSize);

            if (chunkName.equals("RIFF")) {
                if (!"WAVE".equals(readString(this.input, 4))) {

                    throw new IllegalStateException();
                }
            } else if (chunkName.equals("fmt ")) {
                this.audioFormat = swap16(this.input.readUnsignedShort());
                this.numChannels = swap16(this.input.readUnsignedShort());
                this.sampleRate = swap32(this.input.readInt());
                this.byteRate = swap32(this.input.readInt());
                this.blockAlign = swap16(this.input.readUnsignedShort());
                this.bytesPerSample = swap16(this.input.readUnsignedShort()) / 8;

                for (int off = 16; off < chunkSize; off++) {
                    this.input.readByte();
                }
            } else if (chunkName.equals("data")) {
                this.sampleCount = chunkSize / this.bytesPerSample / this.numChannels;
                break;
            } else {
                for (int off = 0; off < chunkSize; off++) {
                    this.input.readByte();
                }
            }
        }


        if (this.audioFormat != 1) {
            if (input instanceof Closeable)
                input.close();
            throw new IllegalStateException("can only parse uncompressed wav files: " + audioFormat);
        }
    }

    public int bytesPerSample() {
        return this.bytesPerSample;
    }

    public int sampleRate() {
        return this.sampleRate;
    }

    public int sampleCount() {
        return this.sampleCount;
    }

    @Override
    public int read() throws IOException {
        try {
            return input.read();
        } catch (EOFException e) {
            endOfStream = true;
            throw e;
        }
    }

    @Override
    public int read(byte[] b) throws IOException {
        try {
            return input.read(b);
        } catch (EOFException e) {
            endOfStream = true;
            throw e;
        }
    }

    @Override
    public int read(byte[] buf, int off, int len) throws IOException {
        try {
            if (len % bytesPerSample != 0) {
                throw new IllegalStateException();
            }
            return input.read(buf, off, len);
        } catch (EOFException e) {
            endOfStream = true;
            throw e;
        }
    }

    @Override
    public int available() {
        return endOfStream ? 0 : 1;
    }


    @Override
    public int getChannels() {
        return this.numChannels;
    }

    @Override
    public int getRate() {
        return this.sampleRate;
    }

    @Override
    public boolean atEnd() {
        return endOfStream;
    }

    //

    private static String readString(DataInputStream raf, int len) throws IOException {
        char[] cs = new char[len];
        for (int i = 0; i < len; i++)
            cs[i] = (char) (raf.readByte() & 0xFF);
        return new String(cs);
    }

    private static int swap16(int i) {
        int b = 0;
        b |= ((i >> 8) & 0xFF) << 0;
        b |= ((i >> 0) & 0xFF) << 8;
        return b;
    }

    private static int swap32(int i) {
        int b = 0;
        b |= ((i >> 24) & 0xFF) << 0;
        b |= ((i >> 16) & 0xFF) << 8;
        b |= ((i >> 8) & 0xFF) << 16;
        b |= ((i >> 0) & 0xFF) << 24;
        return b;
    }
}
