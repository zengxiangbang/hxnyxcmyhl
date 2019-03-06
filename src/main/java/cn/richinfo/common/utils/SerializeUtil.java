package cn.richinfo.common.utils;

import java.io.*;
import java.net.URLEncoder;

/**
 * 序列化和反序列化工具类
 * 
 *
 */
public class SerializeUtil {

	/**
	 * 序列化
	 * @param object
	 */
	public static byte[] serialize(Object object) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {}
		return null;
	}
	
	/**
	 * 将对象序列化成字符
	 * @param object
	 */
	public static String serializeToStr(Object object) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);) {
			oos.writeObject(object);
			String byteStr = baos.toString("ISO-8859-1");
			return URLEncoder.encode(byteStr, "UTF-8");
		} catch (Exception e) {}
		return null;
	}
	
	/**
	 * 将字符串反序列化
	 * @param object
	 */
	public static Object strToUnserialize(String encodeStr) {
		String deserStr = null;
		try {
			deserStr = java.net.URLDecoder.decode(encodeStr, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			return null;
		}
		if(deserStr!=null){
			try (ByteArrayInputStream bais = new ByteArrayInputStream(deserStr.getBytes("ISO-8859-1"));
					ObjectInputStream ois = new ObjectInputStream(bais);) {
				return ois.readObject();
			} catch (Exception e) {}
		}
		return null;
	}

	/**
	 * 反序列化流数据
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object unserialize(byte[] bytes) {
		if (bytes!=null && bytes.length>0) {
			try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
					ObjectInputStream ois = new ObjectInputStream(bais);) {
				return ois.readObject();
			} catch (Exception e) {}
		}
		return null;
	}

	/**
	 * 将序列号文件转换成对象
	 * 
	 * @param file
	 * @return
	 */
	public static Object unserialize(File file) {
		Object obj = null;
		try (RandomAccessFile raf = new RandomAccessFile(file, "r");
				FileInputStream fis = new FileInputStream(raf.getFD());
				ObjectInputStream ois = new ObjectInputStream(fis);) {
			obj = ois.readObject();
		} catch (Exception e) {}
		return obj;
	}

	/**
	 * 将类序列化为文件
	 * 
	 * @param obj
	 * @param file
	 */
	public static void serialize(Object obj, File file) {
		try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream oos = new ObjectOutputStream(fos);) {
			oos.writeObject(obj);
		} catch (Exception e) {}
	}

}