package cn.edu.llxy.dw.dss.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 对象处理工具类
 */
public class ObjectUtil {
	/**
	 * 将对象数组转换成byte[]
	 * @param obj
	 * @return
	 */
	public static byte[] toByteArray(Object obj) {
		byte[] result = null;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		writeObject(obj, bout);
		result = bout.toByteArray();
		return result;
	}
	
	/**
	 * bytes 反序列化为对象
	 * @param bytes               序列化对象bytes
	 * @param obj                 承载反序列化对象实例
	 * @return
	 * @throws IOException
	 */
	public static Object bytesToObject(byte[] bytes,Object obj) throws IOException{
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        try {
			obj = in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        in.close();
        return obj;
}
	
	private static Object ByteToObject(byte[] bytes){
        Object obj=null;
        try{
        //bytearray to object
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);

        obj = oi.readObject();

        bi.close();
        oi.close();
        }
        catch(Exception e){
            System.out.println("translation"+e.getMessage());
            e.printStackTrace();
        }
        return obj;
    }

	
	/**
	 * 将对象写入输出流
	 * @param o
	 * @param out
	 */
	public static void writeObject(Object o, OutputStream out) {
		ObjectOutputStream oout = null;
		try {
			oout = new ObjectOutputStream(out);
			oout.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(oout);
		}
	}
	
	/**
	 * 关闭输入出流
	 * @param out
	 */
	public static void close(OutputStream out) {
		if (out != null)
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 将序列化后的对象数组反序列化为对象
	 * @param params          序列化对象流
	 * @return
	 */
	public static Object[] deserializeSqlParams(byte[] params)
	{
		if((params==null)||(params.length==0)) return null;
		try{
		ByteArrayOutputStream byteout=new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteout);
		out.writeObject(params);
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(params));
         Object[] paramObjs = (Object[])in.readObject();
		return paramObjs;
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
