package wcf.records;

import java.io.IOException;
import java.io.InputStream;

public interface PrefixCodeNode {
	public void setPrefix(String prefix);
	public void setType(int type);
	public void parsePrefix(InputStream is) throws IOException;
}
