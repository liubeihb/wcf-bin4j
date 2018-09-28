package wcf.records.elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.PrefixCodeNode;
/**
 * 固定前缀元素<br/>
 * [0x5E-0x77][len(1)][name] >> 0x5E0x04test >> &lt;a:test&gt;<br/>
 * 前缀标识映射关系：<br/>
 * 0x5E : a <br/>
 * 0x5F : b <br/>
 * ...<br/>
 * 0x77 : z 
 * @author Jing
 *
 */
public class PrefixCodeElement extends PrefixElement implements PrefixCodeNode{
	private int type;
	@Override
	public int getType() {
		return type;
	}
	public void setType(int type){
		this.type=type;
	}
	
	@Override
	public void parsePrefix(InputStream is) throws IOException{
		setPrefix(Character.toString((char)(type+3)));
	}
	
	@Override
	public String toXML() {
		return "\r\n<"+prefix+":"+name+getAttributeXml()+">"+getChildXml()+"</"+prefix+":"+name+">";
	}
	@Override
	protected void toNameBytes(OutputStream os) throws Exception {
	    nameBytes(name, os);
	}
}
