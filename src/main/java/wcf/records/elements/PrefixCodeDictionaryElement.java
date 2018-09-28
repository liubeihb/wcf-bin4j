package wcf.records.elements;

import java.io.IOException;
import java.io.InputStream;

public class PrefixCodeDictionaryElement extends PrefixCodeElement {
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
		setPrefix(Character.toString((char)(type - 0x44 + 'a')));
	}
	
}
