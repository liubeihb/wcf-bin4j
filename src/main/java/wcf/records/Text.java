package wcf.records;

import java.io.OutputStream;

import wcf.Node;
/**
 * 文本节点
 * @author Jing
 *
 * @param <T>
 */
public abstract class Text<T> implements Node {
	protected T value;

	public T getValue() {
		return value;
	}
	public String toXML(){
	    return value.toString();
	}
	@Override
	public void toBytes(OutputStream os) throws Exception {
	    os.write(getType()+1);
	}
	
}
