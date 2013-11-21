package com.ivan.encryption;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

public class Base64Encrypt {

	public static void main(String[] args) throws Exception {
		
		if(args.length != 1)
		{
			System.out.println("Base64Encrypt ?String to encrypted");
			return;
		}
		
		BASE64Encoder encoder  = new BASE64Encoder();
		BASE64Decoder decoder = new BASE64Decoder();
		
		String val = args[0];
		//System.out.println(val);
		
		val = encoder.encode(val.getBytes("utf-8"));
		System.out.println(val);
		
		val = new String(decoder.decodeBuffer(val), "utf-8");
		//System.out.println(val);

	}

}
