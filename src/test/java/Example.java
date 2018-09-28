

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.zip.InflaterOutputStream;

import wcf.Dictionary;
import wcf.Node;
import wcf.NodeFactory;
import wcf.records.Attribute;
import wcf.records.Element;
import wcf.records.attributes.XmlnsAttribute;
import wcf.records.elements.ShortElement;
import wcf.util.ByteArrayUtil;
import wcf.util.HttpUtil;

public class Example {
	
	public static void main(String[] args) throws Exception {
	    //GetCityDayAqiHistoryByCondition
	    
	    ShortElement params=new ShortElement();
	    params.setName("GetProvincePublishLives");
	    params.getAttribute().add(Attribute.getXmlnsAttribute("http://tempuri.org/"));
	    params.getChilds().add(Element.getShortElement("pid", "4"));
	    java.io.ByteArrayOutputStream os=new java.io.ByteArrayOutputStream();
	    params.toBytes(os);
		Node root=HttpUtil.init(NodeFactory.AIR_SERVICE+"GetCityRealTimeAQIModelByCitycode?cityCode=110000")
			.setMethod(HttpUtil.GET)
			.addHeader("referer","http://106.37.208.233:20035/ClientBin/cnemc.xap")
			.setContentType("application/msbin1",null)
			.send("",is->{
			    return NodeFactory.parseRoot(is);
			});
//		
//		System.out.println(root.toXML());
//	    ByteArrayInputStream is=new ByteArrayInputStream(ByteArrayUtil.hexStringToByteArray("ff7f"));
//	    System.out.println(ByteArrayUtil.getMultiByteInt31(is));
	    System.out.println(Dictionary.getDictionary(0x02));
	}
}
