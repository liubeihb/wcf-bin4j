package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Text;
/**
 * byte
 * @author Jing
 *
 */
public class Int8Text extends Text<Byte> {

    @Override
    public int getType() {
	return 0x88;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	value=((byte)is.read());
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	os.write(value);
    }   

}
