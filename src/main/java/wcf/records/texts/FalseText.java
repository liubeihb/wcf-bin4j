package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;

import wcf.records.Text;

public class FalseText extends Text<Boolean> {

    @Override
    public int getType() {
	return 0x84;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	value=false;
    }

}
