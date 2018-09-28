package wcf.records;

import java.io.OutputStream;

import wcf.Node;
import wcf.records.attributes.ShortXmlnsAttribute;

/**
 * 属性节点
 * @author Jing
 *
 */
public abstract class Attribute implements Node{
	protected String name,value;

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public void toBytes(OutputStream os) throws Exception {
	    os.write(getType());
	}
	public static Attribute getXmlnsAttribute(String value){
	    Attribute attr=new ShortXmlnsAttribute();
	    attr.value=value;
	    return attr;
	}
}
