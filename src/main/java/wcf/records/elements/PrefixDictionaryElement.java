package wcf.records.elements;

import java.io.OutputStream;

import wcf.records.Element;

public class PrefixDictionaryElement extends Element {

	@Override
	public int getType() {
		return 0x43;
	}

	@Override
	protected void toNameBytes(OutputStream os) throws Exception {
	    // TODO Auto-generated method stub
	    
	}

}
