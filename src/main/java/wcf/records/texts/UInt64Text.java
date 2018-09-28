package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;
/**
 * 无符号long类型
 * @author Jing
 *
 */
public class UInt64Text extends Text<BigInteger> {

    @Override
    public int getType() {
	return 0x8e;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	byte[] bs=new byte[8];
	is.read(bs);
	value=ByteArrayUtil.getBigIntegerByBytes(bs);
    }
    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	os.write(ByteArrayUtil.getBytesByBigInteger(value));
    }
    
}
