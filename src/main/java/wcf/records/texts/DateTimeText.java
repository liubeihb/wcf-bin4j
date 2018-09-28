package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateFormatUtils;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;
/**
 * 日期
 * @author Jing
 *
 */
public class DateTimeText extends Text<Long>{
    public static long TIME_LINE=62135596800000l;//1-1-1 0:0:0 到1970-1-1 0:0:0 的毫秒数
    public static long TIME_ZONE=28800000;//8小时毫秒数
    private int tz;
    @Override
    public int getType() {
	return 0x96;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	byte[] bs = new byte[8];
	is.read(bs);
	ByteArrayUtil.reverse(bs);
	ByteBuffer bb = ByteBuffer.wrap(bs);
	long l=bb.getLong();
	tz=(int)(l>>62);
	long times = l & 0x3FFFFFFFFFFFFFFFL ;//传过来的是微秒，计算基数是从公元1年1月1日开始
	value = (times/ 10000) - TIME_LINE;
    }
    
    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	ByteBuffer bb = ByteBuffer.allocate(8);
	bb.putLong( ((tz & 0x3) << 62) | ((value+TIME_LINE) *10000) & 0x3FFFFFFFFFFFFFFFL);
	byte[] bs=bb.array();
	ByteArrayUtil.reverse(bs);
	os.write(bs);
    }
    
    public String toXML(){	
	return DateFormatUtils.format(value, "yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("GMT"));
    }
}
