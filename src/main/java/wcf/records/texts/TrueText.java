package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;

import wcf.records.Text;

public class TrueText extends Text<Boolean> {

    @Override
    public int getType() {
	return 0x86;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	value=true;
    }

}
