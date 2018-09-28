package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;

import wcf.records.Text;
/**
 * 长度为1字节的utf-8编码字符串
 * @author Jing
 *
 */
public class OneText extends Text<Integer> {

    @Override
    public int getType() {
	return 0x82;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	value=1;
    }
    
}
