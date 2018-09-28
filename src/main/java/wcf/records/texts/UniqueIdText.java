package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;
/**
 * UUID [4bit](LE)[2bit](LE)[2bit](LE)[2bit](BE)[6bit](BE)<BR/>
 * HEX BYTES b'00112233445566778899aabbccddeeff' -> 33221100-5544-7766-8899-aabbccddeeff
 * @author Jing
 *
 */
public class UniqueIdText extends Text<UUID> {

    @Override
    public int getType() {
	return 0xAC;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	byte[] data=new byte[16];
	is.read(data);
	long msb = 0;
        long lsb = 0;
        ArrayUtils.reverse(data, 0, 4);
        ArrayUtils.reverse(data, 4, 6);
        ArrayUtils.reverse(data, 6, 8);
        for (int i=0; i<8; i++)
            msb = (msb << 8) | (data[i] & 0xff);
        for (int i=8; i<16; i++)
            lsb = (lsb << 8) | (data[i] & 0xff);
	this.value=new UUID(msb,lsb);
	
    }
    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	long msb = this.value.getMostSignificantBits();
        long lsb = this.value.getLeastSignificantBits();
	ByteBuffer bb=ByteBuffer.allocate(16);
	bb.putLong(msb);
	bb.putLong(lsb);
	byte[] bs=bb.array();
        ArrayUtils.reverse(bs, 0, 4);
        ArrayUtils.reverse(bs, 4, 6);
        ArrayUtils.reverse(bs, 6, 8);
	os.write(bs);
    }
    
    @Override
    public String toXML(){
	return "urn:uuid:"+this.value.toString();
    }
}
