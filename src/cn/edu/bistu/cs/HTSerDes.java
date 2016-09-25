package cn.edu.bistu.cs;

import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.Base64;

import sun.misc.BASE64Encoder;

import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;

public class HTSerDes implements SerDes<HuffmanTree>{
	/**
	 * 将对象t序列化为字节数组
	 */
	/**序列化
	 * 先要创建某些OutputStream对象，
	 * 然后将其封装在一个ObjectOutputStream对象内
	 * 再调用writeObject()方法即可序列化一个对象
	 **/
	/**反序列化
	 * 先要创建某些InputStream对象，
	 * 然后将其封装在一个ObjectInputStream对象内
	 * 再调用writeObject()方法即可序列化一个对象
	 **/
	public byte[] serBin(HuffmanTree t){
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			//序列化
			baos = new ByteArrayOutputStream();
			oos= new ObjectOutputStream(baos);
			oos.writeObject(t);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 将对象t序列化为一个字符串。
	 * 提示：可以先使用serBin方法将对象t序列化为字节数组，
	 * 再将字节数组用Base64编码为字符串
	 */
	public String serText(HuffmanTree t) {
		BASE64Encoder encoder = new BASE64Encoder();//对字节数组Base64编码
        return encoder.encode(serBin(t));//返回Base64编码过的字节数组字符串
	}

	/**
	 * 将序列化后的字节数组反序列化为一个哈夫曼对象
	 */
	public HuffmanTree des(byte[] bin) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			bais = new ByteArrayInputStream(bin);
			ois= new ObjectInputStream(bais);
			HuffmanTree htree = (HuffmanTree) ois.readObject();
			return htree;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 将序列化后的字符串反序列化为一个哈夫曼对象，
	 * 字符串应该是使用serText方法序列化得到的
	 */
	public HuffmanTree des(String text) {
		HuffmanTree htree = (HuffmanTree)des(Base64.getDecoder().decode(text));
		return htree;
	}

	/**
	 * 将对象序列化并写入磁盘文件。
	 * 提示：可以使用serBin将对象t序列化，
	 * 然后将序列化后的字节数组写入文件
	 */
	public boolean serToFile(HuffmanTree t, String path, String file) {
		try {//自定义任何路径，filepath = file + path
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path+file)));
			oos.writeObject(t);
			System.out.println("HuffmanTree对象序列化成功！");
			oos.close();
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 将序列化后的对象从磁盘文件中读出。
	 * 提示：可以首先从磁盘中读出字节数组，
	 * 然后使用des方法将对象反序列化
	 */
	public HuffmanTree desFromFile(String path, String file) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(file + ".txt")));
			HuffmanTree htree = (HuffmanTree) ois.readObject();
			System.out.println("HuffmanTree对象反序列化成功！");
			ois.close();
			return htree;
		} catch (Exception e) {
		} 
		return null;
	}

}
