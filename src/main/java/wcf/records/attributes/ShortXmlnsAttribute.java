package wcf.records.attributes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Attribute;

public class ShortXmlnsAttribute extends Attribute {

    @Override
    public int getType() {
	return 0x08;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	name="xmlns";
	value=parseName(is);
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	nameBytes(value, os);
    }

    @Override
    public String toXML() {
	return name+"=\""+value+"\" ";
    }

}
