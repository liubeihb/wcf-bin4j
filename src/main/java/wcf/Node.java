package wcf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 节点接口，节点有三种类型：元素、属性、文本，对应xml中的element、attribute、text<br/>
 * wcfbin的基本解析规则为[节点标识][长度][节点名称/内容][子节点...][0x01] 0x01为结束标识，遇到结束标识返回上一级节点<br/>
 * 解析时根据InputStream按位取值，分别解析出对应节点，采用递归方式构建出节点树<br/>
 * 元素:0x40 - 0x77 <br/>
 * 属性:0x04 - 0x3f <br/>
 * 文本:0x80 - 0xbc 文本标识全为偶数，获取到的标识值-1为映射的节点类型，此时没有结束标识也返回上一级节点<br/>
 * @author Jing
 *
 */
public interface Node {
	
	public int getType();
	public void parse(InputStream is)throws IOException;
	public String toXML();

	/**
	 * 转为字节流
	 * @param os
	 * @throws Exception
	 */
	default public void toBytes(OutputStream os)throws Exception{
	    os.write(getType());
	}
	default String parseName(InputStream is) throws IOException{
		int len=is.read();
		byte[] name=new byte[len];
		is.read(name);
		return new String(name,"UTF-8");
	}
	default void nameBytes(String name,OutputStream os)throws IOException{
	    byte[] bs=name.getBytes("UTF-8");
	    os.write(bs.length);
	    os.write(bs);
	}
}
