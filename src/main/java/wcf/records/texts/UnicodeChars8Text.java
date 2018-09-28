package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Text;

public class UnicodeChars8Text extends Text<String> {

    @Override
    public int getType() {
	return 0xB6;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	int len=is.read();
	byte[] name=new byte[len];
	is.read(name);
	value= new String(name,"UTF-16LE");
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	byte[] vs=value.getBytes("UTF-16LE");
	os.write(vs.length);
	os.write(vs);
    }

}
