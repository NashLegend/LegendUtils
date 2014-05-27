package com.example.legendutils.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5和SHA-x对文件校验以及字符串加密
 * 
 * @author NashLegend
 */
public class EncryptUtil {

	public static final String MD5 = "MD5";
	public static final String SHA1 = "SHA-1";
	public static final String SHA256 = "SHA-256";
	public static final String SHA384 = "SHA-384";
	public static final String SHA512 = "SHA-512";

	/**
	 * 计算文件MD5
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMD5String(File file) throws IOException {
		return getFileEncryptString(file, MD5);
	}

	/**
	 * 计算文件指定加密类型的加密结果
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileEncryptString(File file, String algorithm)
			throws IOException {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(algorithm);
			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				digest.update(buffer, 0, len);
			}
			in.close();
			return bytes2String(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算字符串MD5值
	 * 
	 * @param s
	 * @return
	 */
	public static String getStringMD5(String s) {
		return getBytesMD5(s.getBytes());
	}

	/**
	 * 计算字符串指定类型的加密结果
	 * 
	 * @param s
	 * @return
	 */
	public static String getStringEncrypt(String s, String algorithm) {
		return getBytesEncrypt(s.getBytes(), algorithm);
	}

	/**
	 * 计算byte数组的MD5值
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getBytesMD5(byte[] bytes) {
		return getBytesEncrypt(bytes, MD5);
	}

	/**
	 * 计算byte数组指定加密类型的加密结果
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getBytesEncrypt(byte[] bytes, String algorithm) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(algorithm);
			digest.update(bytes);
			return bytes2String(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String bytes2String(byte digest[]) {
		String str = "";
		String tempStr = "";
		for (int i = 0; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str += tempStr;
			}
		}
		return str;
	}
}