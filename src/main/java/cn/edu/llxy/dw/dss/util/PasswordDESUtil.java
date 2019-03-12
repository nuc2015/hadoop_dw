package cn.edu.llxy.dw.dss.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.apache.commons.codec.binary.Base64;


public class PasswordDESUtil {
 
    static Key key ;
    
    static String KEY_WORD = "passwordDES";
 
    public static Key getKey() {
		return key;
	}

	public static void setKey(Key key) {
		PasswordDESUtil.key = key;
	}

    /**
      * 根据参数生成 KEY
      */
    public static void setKey(String strKey) {
       try {
           KeyGenerator _generator = KeyGenerator.getInstance("DES");
           //防止linux下 随机生成key    
           SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
           secureRandom.setSeed(strKey.getBytes());  
           _generator.init(56,secureRandom);  
           //_generator.init( new SecureRandom(strKey.getBytes()));
           key = _generator.generateKey();
           _generator = null ;
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       }
    }
 
    /**
      * 加密 String 明文输入 ,String 密文输出
      */
    public static String encryptStr(String strMing) {
       byte [] byteMi = null ;
       byte [] byteMing = null ;
       String strMi = "" ;
       Base64 base64en = new Base64();
       try {
           byteMing = strMing.getBytes("UTF8");
           byteMi = encryptByte(byteMing);
           //strMi = base64en..encodeToString(byteMi);
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       } finally {
           base64en = null ;
           byteMing = null ;
           byteMi = null ;
       }
       return strMi;
    }
 
    /**
      * 解密 以 String 密文输入 ,String 明文输出
      *
      * @param strMi
      * @return
      */
    public static String decryptStr(String strMi) {
       Base64 base64De = new Base64();
       byte [] byteMing = null ;
       byte [] byteMi = null ;
       String strMing = "" ;
       try {
           //byteMi = base64De.decode(strMi);
           byteMing = decryptByte(byteMi);
           strMing = new String(byteMing, "UTF8");
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       } finally {
           base64De = null ;
           byteMing = null ;
           byteMi = null ;
       }
       return strMing;
    }
 
    /**
      * 加密以 byte[] 明文输入 ,byte[] 密文输出
      *
      * @param byteS
      * @return
      */
    private static byte [] encryptByte( byte [] byteS) {
       byte [] byteFina = null ;
       Cipher cipher;
       try {
    	   setKey(KEY_WORD);
           cipher = Cipher.getInstance("DES");
           cipher.init(Cipher. ENCRYPT_MODE , key);
           byteFina = cipher.doFinal(byteS);
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       } finally {
           cipher = null ;
       }
       return byteFina;
    }
 
    /**
      * 解密以 byte[] 密文输入 , 以 byte[] 明文输出
      *
      * @param byteD
      * @return
      */
    private static byte [] decryptByte( byte [] byteD) {
       Cipher cipher;
       byte [] byteFina = null ;
       try {
    	   setKey(KEY_WORD);
           cipher = Cipher.getInstance("DES");
           cipher.init(Cipher. DECRYPT_MODE , key );
           byteFina = cipher.doFinal(byteD);
       } catch (Exception e) {
           throw new RuntimeException(
                  "Error initializing SqlMap class. Cause: " + e);
       } finally {
           cipher = null ;
       }
       return byteFina;
    }
 
    public static void main(String[] args) throws Exception {
       // DES 加密文件
       // des.encryptFile("G:/test.doc", "G:/ 加密 test.doc");
       // DES 解密文件
       // des.decryptFile("G:/ 加密 test.doc", "G:/ 解密 test.doc");
       String str1 = "adaaaaaa123456" ;
       // DES 加密字符串
       String str2 = PasswordDESUtil.encryptStr(str1);
       // DES 解密字符串
       String deStr = PasswordDESUtil.decryptStr(str2);
       System. out .println( " 加密前： " + str1);
       System. out .println( " 加密后： " + str2);
       System. out .println( " 解密后： " + deStr);
    }
} 