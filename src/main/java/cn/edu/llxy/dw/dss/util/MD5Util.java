package cn.edu.llxy.dw.dss.util;
 
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util
{
  public static String ecodeByMD5(String originstr)
  {
    String result = null;
    
    char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    if (originstr != null) {
      try
      {
        MessageDigest md = MessageDigest.getInstance("MD5");
        
        byte[] source = originstr.getBytes("utf-8");
        
        md.update(source);
        
        byte[] tmp = md.digest();
        
        char[] str = new char[32];
        
        int i = 0;
        for (int j = 0; i < 16; i++)
        {
          byte b = tmp[i];
          
          str[(j++)] = hexDigits[(b >>> 4 & 0xF)];
          
          str[(j++)] = hexDigits[(b & 0xF)];
        }
        result = new String(str);
      }
      catch (NoSuchAlgorithmException e)
      {
        e.printStackTrace();
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
    }
    return result;
  }
  
  public static boolean checkPWDMd5(String md5PWD, String relPWD)
  {
    if (md5PWD.equals(ecodeByMD5(relPWD))) {
      return true;
    }
    return false;
  }
  
  public static boolean checkPWD(String typPWD, String relPWD)
  {
    if (ecodeByMD5(typPWD).equals(ecodeByMD5(relPWD))) {
      return true;
    }
    return false;
  }
  
  public static void main(String[] args)
  {
    String oldStr = "123456";
    System.out.println(ecodeByMD5(oldStr));
    System.out.println(checkPWDMd5("e10adc3949ba59abbe56e057f20f883e", "123456"));
  }
}

