package wcf.records.attributes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Attribute;

public class XmlnsAttribute extends Attribute{

    @Override
    public int getType() {
	return 0x09;
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	nameBytes(name, os);
	nameBytes(value, os);
    }
    @Override
    public void parse(InputStream is) throws IOException {
	name=parseName(is);
	value=parseName(is);
    }
    @Override
    public String toXML() {
	return "xmlns:"+name+"=\""+value+"\" ";
    }
}
