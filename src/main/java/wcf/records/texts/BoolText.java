package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import wcf.records.Text;

public class BoolText extends Text<Boolean> {

    @Override
    public int getType() {
	return 0xB4;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	int i=is.read();
	value=(i==1);
    }

    @Override
    public void toBytes(OutputStream os) throws Exception {
	super.toBytes(os);
	os.write(value?1:0);
    }
    
    
    
}
