package wcf.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 字节数组工具类
 * 
 * @author JYang
 *
 */
public class ByteArrayUtil {
    /**
     * 将一个字节数组做反转操作[1,2,3,4]>>[4,3,2,1]
     * @see org.apache.commons.lang3.ArrayUtils.reverse(byte[] array);
     * @param bs
     */
    @Deprecated
    public static void reverse(byte[] bs) {
	for (int i = 0; i < bs.length / 2; i++) {
	    byte temp = bs[i];
	    bs[i] = bs[bs.length - i - 1];
	    bs[bs.length - i - 1] = temp;
	}
//	ArrayUtils.reverse(bs);
    }

   
    /**
     * 从byte数组中截取指定位置和长度的数据，返回新数组
     * 
     * @param res
     *            原数组
     * @param begin
     *            起始位置
     * @param len
     *            截取长度
     * @return byte[]
     */
    public static byte[] getByteArrayData(byte[] res, int begin, int len) {
	byte[] result = new byte[len];
	for (int i = 0; i < len; i++) {
	    result[i] = res[begin + i];
	}
	return result;
    }

    /**
     * 从byte数组中截取指定位置和长度的数据，返回ascii编码的字符串
     * 
     * @param res
     *            原数组
     * @param begin
     *            起始位置
     * @param len
     *            截取长度
     * @return String
     */
    public static String getStringData(byte[] res, int begin, int len) {
	return new String(getByteArrayData(res, begin, len));
    }

    /**
     * 将soc插入res的指定位置
     * 
     * @param res
     *            目标数组
     * @param soc
     *            元数组
     * @param begin
     *            起始位置
     */
    public static void setByteArrayData(byte[] res, byte[] soc, int begin) {
	for (int i = 0; i < soc.length; i++) {
	    res[i + begin] = soc[i];
	}
    }

    /**
     * 这是将16进形式的字符串要转化为byte[],就是要以两位的形式读取
     */
    public static final byte[] hexStringToByteArray(String s) {
	if (StringUtils.isEmpty(s))
	    return null;
	byte[] bytes;
	int len = s.length();
	if (s.endsWith("\n"))
	    len--;
	bytes = new byte[len / 2];
	for (int i = 0; i < bytes.length; i++) {
	    bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
		    16);
	}
	return bytes;
    }

    /**
     * 字节数组转为16进制表示的字符串
     * @param b 字节数组
     * @return 16进制字符串
     */
    public static final String byteArrayToHexString(byte[] b) {

	return byteArrayToHexString(b, "");
    }

    /**
     * 字节数组转为16进制表示的字符串
     * @param b 字节数组
     * @param siff 字节分割符
     * @return 16进制字符串
     */
    public static final String byteArrayToHexString(byte[] b, String siff) {
	if (b == null || b.length == 0)
	    return null;
	StringBuffer ret = new StringBuffer();
	for (int i = 0; i < b.length; i++) {
	    String hex = Integer.toHexString(b[i] & 0xFF);
	    if (hex.length() == 1) {
		ret.append(0);
	    }
	    ret.append(hex.toUpperCase());
	    if (i < b.length - 1)
		ret.append(siff);
	}
	return ret.toString();
    }

    /**
     * 字节数组转short/ubyte
     * @param bs
     * @return
     */
    public static short getShortByBytes(byte[] bs) {
	ByteBuffer bb = ByteBuffer.allocate(2)
		.order(ByteOrder.LITTLE_ENDIAN)
		.put(bs);
	bb.flip();
	return bb.getShort();
    }
    
    /**
     *  short转字节数组
     * @param port
     * @return
     */
    public static byte[] getBytesByShort(short port) {
	return ByteBuffer.allocate(2)
		.order(ByteOrder.LITTLE_ENDIAN)
		.putShort(port)
		.array();
    }

    /**
     *  字节数组转int/ushort
     * @param bs
     * @return
     */
    public static int getIntByBytes(byte[] bs) {
	ByteBuffer bb = ByteBuffer.allocate(4)
		.order(ByteOrder.LITTLE_ENDIAN)
		.put(bs);
	bb.flip();
	return bb.getInt();
    }

    /**
     *  int转字节数组
     * @param port
     * @return
     */
    public static byte[] getBytesByInt(int port) {
	return ByteBuffer.allocate(4)
		.order(ByteOrder.LITTLE_ENDIAN)
		.putInt(port)
		.array();
    }

    /**
     *  字节数组转long/uint
     * @param bs
     * @return
     */
    public static long getLongByBytes(byte[] bs) {
	ByteBuffer bb = ByteBuffer.allocate(8)
		.order(ByteOrder.LITTLE_ENDIAN)
		.put(bs);
	bb.flip();
	return bb.getLong();
    }

    /**
     *  long转字节数组
     * @param port
     * @return
     */
    public static byte[] getBytesByLong(long port) {
	return ByteBuffer.allocate(8)
		.order(ByteOrder.LITTLE_ENDIAN)
		.putLong(port)
		.array();
    }

    /**
     *  无符号long转byte数组,无符号long使用BigInteger
     * @param i
     * @return
     */
    public static byte[] getBytesByBigInteger(BigInteger i) {
	if (i.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 1)
	    return ByteArrayUtil.getBytesByLong(i.longValue());
	else {
	    long n = i.subtract(BigInteger.valueOf(1))
		    .subtract(BigInteger.valueOf(Long.MAX_VALUE)).longValue() | 0x8000000000000000L;
	    return ByteArrayUtil.getBytesByLong(n);
	}
    }

    /**
     *  byte数组转无符号long
     * @param bs
     * @return
     */
    public static BigInteger getBigIntegerByBytes(byte[] bs) {
	long low = ByteArrayUtil.getLongByBytes(bs);
	if (low >= 0)
	    return BigInteger.valueOf(low);
	else
	    return BigInteger.valueOf(low & 0x7fffffffffffffffL)
		    .add(BigInteger.valueOf(Long.MAX_VALUE))
		    .add(BigInteger.valueOf(1));
    }

    public static int getMultiByteInt31(InputStream is)throws IOException{
	int value=0;
	for(int i=0;i<4;i++){
	    int b=is.read();
	    value |= (b & 0x7F) << 7*i;
	    if((b & 0x80) == 0)
		break;
	}
	return value;
    }
    
    public static byte[] MultiByteInt31ToBytes(int value){
	int value_a = value & 0x7F;
	int value_b = (value >> 7) & 0x7F;
	int value_c = (value >> 14) & 0x7F;
	int value_d = (value >> 21) & 0x7F;
	int value_e = (value >> 28) & 0x03;
	if(value_e != 0)
	     return new byte[]{(byte)(value_a | 0x80),
		(byte) (value_b | 0x80),
		(byte) (value_c | 0x80),
		(byte) (value_d | 0x80),
		(byte) value_e};
	else if( value_d != 0)
	     return new byte[]{
		(byte) (value_a | 0x80),
		(byte) (value_b | 0x80),
		(byte) (value_c | 0x80),
		(byte) value_d };
	else if(value_c != 0)
	     return new byte[]{
		(byte) (value_a | 0x80),
		(byte) (value_b | 0x80),
		(byte) value_c };
	else if(value_b != 0)
	     return new byte[]{
		(byte) (value_a | 0x80),
		(byte) value_b  };
	else
	     return new byte[]{(byte)value_a};
    }
    /**
     * 解压
     * 
     * @param m
     *            base64编码的字符串
     * @return 解压后的字节数组
     * @throws IOException
     */
    public static byte[] unzip(String m) throws IOException {
	byte[] bs = Base64.getDecoder().decode(m);
	return unzip(bs);
    }

    public static byte[] unzip(byte[] bs) throws IOException {
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	InflaterOutputStream os = new InflaterOutputStream(bos);
	os.write(bs);
	return bos.toByteArray();
    }

    /**
     * 压缩
     * 
     * @param m
     * @return
     * @throws IOException
     */
    public static byte[] zip(String m) throws IOException {
	byte[] bs = Base64.getDecoder().decode(m);
	return zip(bs);
    }

    public static byte[] zip(byte[] bs) throws IOException {
	ByteArrayOutputStream bos = new ByteArrayOutputStream();
	DeflaterOutputStream os = new DeflaterOutputStream(bos);
	os.write(bs);
	return bos.toByteArray();
    }
}