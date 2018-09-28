package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;
/**
 * 长度为1字节的utf-8编码字符串
 * @author Jing
 *
 */
public class Chars32Text extends Text<String>{

    @Override
    public int getType() {
	return 0x9c;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	byte[] bs=new byte[4];
	is.read(bs);
	int len=ByteArrayUtil.getIntByBytes(bs);
	byte[] name=new byte[len];
	is.read(name);
	value = new String(name,"UTF-8");
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {	
	super.toBytes(os);
	byte[] bs=value.getBytes("UTF-8");
	byte[] len=ByteArrayUtil.getBytesByInt(bs.length);
	os.write(len);
	os.write(bs);
    }
    
}
