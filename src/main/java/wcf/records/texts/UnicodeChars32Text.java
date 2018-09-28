package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;

public class UnicodeChars32Text extends Text<String> {

    @Override
    public int getType() {
	return 0xBA;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	byte[] bs=new byte[2];
	is.read(bs);
	int len=ByteArrayUtil.getIntByBytes(bs);
	byte[] name=new byte[len];
	is.read(name);
	value= new String(name,"UTF-16LE");
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	byte[] vs=value.getBytes("UTF-16LE");
	byte[] len=ByteArrayUtil.getBytesByInt(vs.length);
	os.write(len);
	os.write(vs);
    }

}
