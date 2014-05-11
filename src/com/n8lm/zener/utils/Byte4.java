package com.n8lm.zener.utils;

public class Byte4 {
	public byte b1;
	public byte b2;
	public byte b3;
	public byte b4;
	
	public Byte4(int b1, int b2, int b3, int b4) {
		this.b1 = (byte) b1;
		this.b2 = (byte) b2;
		this.b3 = (byte) b3;
		this.b4 = (byte) b4;
	}

	public Byte4(byte[] bs) {
		this.b1 = bs[0];
		this.b2 = bs[1];
		this.b3 = bs[2];
		this.b4 = bs[3];
	}
	
	@Override
	public String toString() {
		return "[" + (b1 & 0x000000FF) + ", " + (b2 & 0x000000FF) + ", " + (b3 & 0x000000FF) + ", " + (b4 & 0x000000FF) + "]";
	}
}
