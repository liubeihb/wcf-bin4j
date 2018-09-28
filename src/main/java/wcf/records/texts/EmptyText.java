package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;

import wcf.records.Text;

public class EmptyText extends Text<String> {

    @Override
    public int getType() {
	return 0xA8;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	value="";
    }

}
