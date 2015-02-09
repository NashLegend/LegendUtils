package net.nashlegend.legendutils.Tools;

import android.annotation.SuppressLint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * MD5和SHA-x或者HMAC对文件校验以及字符串加密
 * 
 * @author NashLegend
 */
@SuppressLint("TrulyRandom")
public class EncryptUtil {
	// digest算法
	public static final String MD5 = "MD5";
	public static final String SHA1 = "SHA-1";
	public static final String SHA256 = "SHA-256";
	public static final String SHA384 = "SHA-384";
	public static final String SHA512 = "SHA-512";

	// Mac算法
	public static final String HmacMD5 = "HmacMD5";
	public static final String HmacSHA1 = "HmacSHA1";
	public static final String HmacSHA256 = "HmacSHA256";
	public static final String HmacSHA384 = "HmacSHA384";
	public static final String HmacSHA512 = "HmacSHA512";

	/**
	 * 计算文件指定加密类型的加密结果,MD5和SHA-x系列
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileDigestEncrypt(File file, String algorithm)
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
	 * 计算文件指定加密类型和key的加密结果,Hmac算法
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMacEncrypt(File file, String algorithm,
			String keyString) throws IOException {
		SecretKey key = generateHmacKey(keyString, algorithm);
		return getFileMacEncrypt(file, key);
	}

	/**
	 * 计算文件指定加密SecretKey的加密结果,Hmac算法
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getFileMacEncrypt(File file, SecretKey key)
			throws IOException {
		String algorithm = key.getAlgorithm();
		Mac mac = null;
		try {
			mac = Mac.getInstance(algorithm);
			mac.init(key);
			FileInputStream in = new FileInputStream(file);
			byte[] buffer = new byte[1024 * 1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				mac.update(buffer, 0, len);
			}
			in.close();
			return bytes2String(mac.doFinal());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 计算字符串指定类型的加密结果,MD5和SHA-x系列
	 * 
	 * @param s
	 * @return
	 */
	public static String getStringDigestEncrypt(String s, String algorithm) {
		return getBytesDigestEncrypt(s.getBytes(), algorithm);
	}

	/**
	 * 计算字符串指定加密类型的密钥字符串的加密结果,Hmac算法
	 * 
	 * @param s
	 * @return
	 */
	public static String getStringMacEncrypt(String s, String algorithm,
			String keyString) {
		SecretKey key = generateHmacKey(keyString, algorithm);
		return getStringMacEncrypt(s, key);
	}

	/**
	 * 计算字符串指定加密密钥的加密结果,Hmac算法
	 * 
	 * @param s
	 * @return
	 */
	public static String getStringMacEncrypt(String s, SecretKey key) {
		return getBytesMacEncrypt(s.getBytes(), key);
	}

	/**
	 * 计算byte数组指定加密类型的加密结果,MD5或者SHA-x算法
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getBytesDigestEncrypt(byte[] bytes, String algorithm) {
		if (algorithm.equals(MD5) || algorithm.equals(SHA1)
				|| algorithm.equals(SHA256) || algorithm.equals(SHA384)
				|| algorithm.equals(SHA512)) {
			MessageDigest digest = null;
			try {
				digest = MessageDigest.getInstance(algorithm);
				digest.update(bytes);
				return bytes2String(digest.digest());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 计算byte数组指定Mac加密类型和key的加密结果,Hmac算法
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getBytesMacEncrypt(byte[] bytes, String algorithm,
			String keyString) {
		SecretKey key = generateHmacKey(keyString, algorithm);
		return getBytesMacEncrypt(bytes, key);
	}

	/**
	 * 计算byte数组指定Mac加密SecretKey的加密结果,Hmac算法
	 * 
	 * @param bytes
	 * @return
	 */
	public static String getBytesMacEncrypt(byte[] bytes, SecretKey key) {
		String algorithm = key.getAlgorithm();
		if (algorithm.equals(HmacMD5) || algorithm.equals(HmacSHA1)
				|| algorithm.equals(HmacSHA256) || algorithm.equals(HmacSHA384)
				|| algorithm.equals(HmacSHA512)) {
			Mac mac = null;
			try {
				mac = Mac.getInstance(algorithm);
				mac.init(key);
				return bytes2String(mac.doFinal(bytes));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 生成一个Hmac的随机密钥
	 * 
	 * @param algorithm
	 * @return
	 */
	@SuppressLint("TrulyRandom")
	public static SecretKey generateHmacKey(String algorithm) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
			SecretKey key = keyGenerator.generateKey();
			return key;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成一个指定key字符串的密钥
	 * 
	 * @param keyString
	 * @param algorithm
	 * @return
	 */
	public static SecretKey generateHmacKey(String keyString, String algorithm) {
		SecretKey key = new SecretKeySpec(keyString.getBytes(), algorithm);
		return key;
	}

	public static String bytes2String(byte digest[]) {
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