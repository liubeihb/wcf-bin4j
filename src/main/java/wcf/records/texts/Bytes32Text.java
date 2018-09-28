package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;

public class Bytes32Text extends Text<String> {

    @Override
    public int getType() {
	// TODO Auto-generated method stub
	return 0xA2;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	byte[] lenb=new byte[4];
	is.read(lenb);
	byte[] bs=new byte[ByteArrayUtil.getIntByBytes(lenb)];
	is.read(bs);
	this.value=Base64.getEncoder().encodeToString(bs);
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {	
	super.toBytes(os);
	byte[] bs=Base64.getDecoder().decode(value);
	os.write(ByteArrayUtil.getBytesByShort((short)bs.length));
	os.write(bs);
    }
}
