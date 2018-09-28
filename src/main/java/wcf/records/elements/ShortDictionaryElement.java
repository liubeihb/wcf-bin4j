package wcf.records.elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.Dictionary;
import wcf.records.Element;
import wcf.util.ByteArrayUtil;

public class ShortDictionaryElement extends Element {
	private int index;
	private String name;
	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public void parse(InputStream is) throws IOException {
	    index=ByteArrayUtil.getMultiByteInt31(is);
	    name=Dictionary.getDictionary(index);
	    super.parse(is);
	}

	@Override
	public int getType() {
		return 0x42;
	}

	@Override
	protected void toNameBytes(OutputStream os) throws Exception {
	    byte[] bs=ByteArrayUtil.MultiByteInt31ToBytes(index);
	    os.write(bs);
	}

}
