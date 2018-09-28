package wcf.records.elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 前缀元素<br/>
 * [0x41][len(1)][prefix][len(1)][name]>> 0x410x01x0x04test >> &lt;x:test&gt;
 * 
 * @author Jing
 *
 */
public class PrefixElement extends ShortElement {
    protected String prefix;

    public String getPrefix() {
	return prefix;
    }

    public void setPrefix(String prefix) {
	this.prefix = prefix;
    }

    public int getType() {
	return 0x41;
    }
    protected void toNameBytes(OutputStream os)throws Exception{
        nameBytes(prefix, os);
        nameBytes(name, os);
    }
	
    @Override
    public void parse(InputStream is) throws IOException {
	parsePrefix(is);
	super.parse(is);
    }

    public void parsePrefix(InputStream is) throws IOException {
	prefix = parseName(is);
    }

}
