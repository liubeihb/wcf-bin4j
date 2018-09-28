package wcf.records.elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Element;

/**
 * 简单元素<br/> 
 * [0x40][len][name] >> @0x04test >> &lt;test&gt;
 * @author Jing
 *
 */
public class ShortElement extends Element{
	
	@Override
	public int getType() {
		return 0x40;
	}

	@Override
	public void parse(InputStream is) throws IOException{
		name=parseName(is);
		//调用父类查询attributes和childs
		super.parse(is);
	}
	
	protected void toNameBytes(OutputStream os)throws Exception{
	    nameBytes(name, os);
	}
	
}
