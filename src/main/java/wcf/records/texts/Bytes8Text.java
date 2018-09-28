package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import wcf.records.Text;

public class Bytes8Text extends Text<String> {

    @Override
    public int getType() {
	// TODO Auto-generated method stub
	return 0xA0;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	int len=is.read();
	byte[] bs=new byte[len];
	is.read(bs);
	this.value=Base64.getEncoder().encodeToString(bs);
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {	
	super.toBytes(os);
	byte[] bs=Base64.getDecoder().decode(value);
	os.write((byte)bs.length);
	os.write(bs);
    }
}
