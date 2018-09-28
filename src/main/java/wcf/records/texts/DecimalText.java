package wcf.records.texts;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

import wcf.records.Text;
import wcf.util.ByteArrayUtil;
/**
 * decimal[0][0][scal](1)[sign](1)[high](4)[low](8)
 * 
 * @author Jing
 *
 */
public class DecimalText extends Text<BigDecimal>{
    private final static BigInteger MAX_LONG=BigInteger.valueOf(2).pow(64);
    @Override
    public int getType() {
	return 0x94;
    }

    @Override
    public void parse(InputStream is) throws IOException {
	is.read(new byte[2]);//忽略前两位
	int scale=is.read();
	byte sign=(byte)(is.read() & 0x80);
	byte[] hbs=new byte[4];
	is.read(hbs);
	BigInteger high=ByteArrayUtil.getBigIntegerByBytes(hbs).multiply(MAX_LONG);
	byte[] lbs=new byte[8];
	is.read(lbs);
	BigInteger low=ByteArrayUtil.getBigIntegerByBytes(lbs);
	value=new BigDecimal(low.add(high),scale);	
	if(sign==(byte)0x80){	
	    value=value.multiply(BigDecimal.valueOf(-1));
	}
    }
    
    @Override
    public void toBytes(OutputStream os) throws Exception{
	super.toBytes(os);
	os.write(new byte[]{0,0});//占位
	byte sign=(byte)(value.compareTo(BigDecimal.valueOf(0))==-1?0x80:0x00);
	os.write(value.scale());
	os.write(sign);
	BigInteger ii=value.multiply(BigDecimal.valueOf(10).pow(value.scale())).toBigInteger();
	ii=(sign==(byte)0x80)?ii.multiply(BigInteger.valueOf(-1)):ii;
	BigInteger high=ii.divide(MAX_LONG).multiply(MAX_LONG);
	os.write(ByteArrayUtil.getByteArrayData(ByteArrayUtil.getBytesByBigInteger(high.divide(MAX_LONG)), 0, 4));
	os.write(ByteArrayUtil.getBytesByBigInteger(ii.subtract(high)));
    }

}
