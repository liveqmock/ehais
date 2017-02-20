package org.ehais.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import org.apache.commons.codec.digest.DigestUtils;


/**
 * 加密工具
 * <p>可以进行MD5和SHA1加密计算</p>
 * @author Miao
 * @version 0.21
 * @since 0.2
 */

public final class EncryptUtils {

	public EncryptUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
     * 16进制数值
     */
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 生成MD5加密校验码
     * @param string 待加密字符串
     * @return MD5加密校验码
     * @throws UnsupportedEncodingException 
     * @since 0.2
     */
    public static String md5(String string) throws UnsupportedEncodingException {
        return encryptString(getEncrypt("MD5"), string);
    }
    
    public static String md5(String string,String encodingType) throws UnsupportedEncodingException {
        return encryptString(getEncrypt("MD5"), string,encodingType);
    }
    
    

    /**
     * 生成MD5加密校验码
     * @param file 待加密文件
     * @return MD5加密校验码
     * @since 0.2
     */
    public static String md5(File file) {
        return encryptFile(getEncrypt("MD5"), file);
    }

    /**
     * 生成SHA1加密校验码
     * @param string 待加密字符串
     * @return SHA1加密校验码
     * @throws UnsupportedEncodingException 
     * @since 0.2
     */
    public static String sha1(String string) throws UnsupportedEncodingException {
        return encryptString(getEncrypt("SHA1"), string);
    }

    /**
     * 生成SHA1加密校验码
     * @param file 待加密文件
     * @return SHA1加密校验码
     * @since 0.2
     */
    public static String sha1(File file) {
        return encryptFile(getEncrypt("SHA1"), file);
    }

    /**
     * 获得指定的算法加密器
     * @param algorithm 算法
     * @throws CatGroupException 如果没有参数algorithm指定的加密算法则抛出此异常
     * @return 加密器
     * @since 0.2
     */
    private static MessageDigest getEncrypt(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException ex) {
        	ex.printStackTrace();
//            throw new BacException("无法创建" + algorithm + "算法加密器", ex);
        }
		return null;
    }

    /**
     * 计算结果转为16进制表示
     * @param bytes 待转换Byte数组
     * @return 转换结果
     * @since 0.2
     */
    public static String bytesToHex(byte[] bytes) {
        int length = bytes.length;
        StringBuilder sb = new StringBuilder(2 * length);
        for (int i = 0; i < length; i++) {
            sb.append(hexDigits[(bytes[i] & 0xf0) >> 4]);
            sb.append(hexDigits[bytes[i] & 0xf]);
        }
        return sb.toString();
    }

    /**
     * 使用加密器对目标字符串进行加密
     * @param digest 加密器
     * @param string 目标字符串
     * @return 计算结果
     * @throws UnsupportedEncodingException 
     * @since 0.2
     */
    private static String encryptString(MessageDigest digest, String string) throws UnsupportedEncodingException {
        return bytesToHex(digest.digest(string.getBytes("UTF-8")));
    }
    
    private static String encryptString(MessageDigest digest, String string,String encodingType) throws UnsupportedEncodingException {
        return bytesToHex(digest.digest(string.getBytes(encodingType)));
    }

    /**
     * 使用加密器对目标文件进行加密
     * @param digest 加密器
     * @param file 目标文件
     * @throws CatGroupException 当文件未找到或读取错误时抛出此异常
     * @return 计算结果
     * @since 0.2
     */
    private static String encryptFile(MessageDigest digest, File file) {
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                digest.update(buffer, 0, numRead);
            }
        } catch (FileNotFoundException ex) {
        	ex.printStackTrace();
//            log.error("文件" + file.getName() + "未找到", ex);
//            throw new BacException("文件" + file.getName() + "未找到", ex);
        } catch (IOException ex) {
        	ex.printStackTrace();
//            log.error("文件" + file.getName() + "发生I/O错误", ex);
//            throw new BacException("文件" + file.getName() + "发生I/O错误", ex);
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
            	ex.printStackTrace();
//                log.error("无法关闭文件" + file.getName(), ex);
//                throw new BacException("无法关闭文件" + file.getName(), ex);
            }
        }
        return bytesToHex(digest.digest());
    }

    
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
