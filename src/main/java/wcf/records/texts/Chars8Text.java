package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Text;
/**
 * 长度为1字节的utf-8编码字符串
 * @author Jing
 *
 */
public class Chars8Text extends Text<String> {

    @Override
    public int getType() {
	return 0x98;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	value=(parseName(is));
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {	
	super.toBytes(os);
	nameBytes(value,os);
    }
    
    
}
