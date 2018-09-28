package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;

public class Int64Text extends Text<Long> {

    @Override
    public int getType() {
	return 0x8e;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	byte[] bs=new byte[8];
	is.read(bs);
	value=ByteArrayUtil.getLongByBytes(bs);
    }
    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	os.write(ByteArrayUtil.getBytesByLong(value));
    }
}
