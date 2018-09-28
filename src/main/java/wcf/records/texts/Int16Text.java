package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;
/**
 * short类型
 * @author Jing
 *
 */
public class Int16Text extends Text<Short> {

    @Override
    public int getType() {
	return 0x88;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	byte[] bs=new byte[2];
	is.read(bs);
	value=(short)ByteArrayUtil.getShortByBytes(bs);
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	os.write(ByteArrayUtil.getBytesByShort(value));
    }   

}
