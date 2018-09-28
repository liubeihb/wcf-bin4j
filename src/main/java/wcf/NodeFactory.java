package wcf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import wcf.records.PrefixCodeNode;
import wcf.records.attributes.ShortXmlnsAttribute;
import wcf.records.attributes.XmlnsAttribute;
import wcf.records.elements.PrefixCodeDictionaryElement;
import wcf.records.elements.PrefixCodeElement;
import wcf.records.elements.PrefixDictionaryElement;
import wcf.records.elements.PrefixElement;
import wcf.records.elements.ShortDictionaryElement;
import wcf.records.elements.ShortElement;
import wcf.records.texts.BoolText;
import wcf.records.texts.Bytes16Text;
import wcf.records.texts.Bytes32Text;
import wcf.records.texts.Bytes8Text;
import wcf.records.texts.Chars16Text;
import wcf.records.texts.Chars32Text;
import wcf.records.texts.Chars8Text;
import wcf.records.texts.DateTimeText;
import wcf.records.texts.DecimalText;
import wcf.records.texts.DoubleText;
import wcf.records.texts.EmptyText;
import wcf.records.texts.FalseText;
import wcf.records.texts.FloatText;
import wcf.records.texts.Int16Text;
import wcf.records.texts.Int32Text;
import wcf.records.texts.Int64Text;
import wcf.records.texts.Int8Text;
import wcf.records.texts.OneText;
import wcf.records.texts.TrueText;
import wcf.records.texts.UInt64Text;
import wcf.records.texts.UUIDText;
import wcf.records.texts.UnicodeChars16Text;
import wcf.records.texts.UnicodeChars32Text;
import wcf.records.texts.UnicodeChars8Text;
import wcf.records.texts.UniqueIdText;
import wcf.records.texts.ZeroText;
/**
 * 解析wcfbin工厂类，定义协议类型
 * @author Jing
 *
 */
public class NodeFactory {
    public final static String AIR_SERVICE="http://106.37.208.233:20035/ClientBin/Env-CnemcPublish-RiaServices-EnvCnemcPublishDomainService.svc/binary/";
//    @SuppressWarnings("unchecked")
    private final static Class<? extends Node>[] nodes = new Class[256];
    static {
	// 元素
	nodes[0x40] = ShortElement.class;//
	nodes[0x41] = PrefixElement.class;//
	nodes[0x42] = ShortDictionaryElement.class;//
	nodes[0x43] = PrefixDictionaryElement.class;//

	// 固定前缀元素
	for (int x = 0x5e; x <= 0x77; x++) {
	    nodes[x] = PrefixCodeElement.class;
	}
	for (int x = 0x44; x <= 0x5D; x++) {
	    nodes[x] = PrefixCodeDictionaryElement.class;
	}
	// 属性
	nodes[0x08] = ShortXmlnsAttribute.class;//
	nodes[0x09] = XmlnsAttribute.class;//

	// 文本
	nodes[0x80] = ZeroText.class;//
	nodes[0x82] = OneText.class;//
	nodes[0x84] = FalseText.class;//
	nodes[0x86] = TrueText.class;//
	nodes[0x88] = Int8Text.class;//
	nodes[0x8a] = Int16Text.class;//
	nodes[0x8c] = Int32Text.class;//
	nodes[0x8e] = Int64Text.class;//
	nodes[0x90] = FloatText.class;//
	nodes[0x92] = DoubleText.class;//
	nodes[0x94] = DecimalText.class;//
	nodes[0x96] = DateTimeText.class;//
	nodes[0x98] = Chars8Text.class;//
	nodes[0x9A] = Chars16Text.class;//
	nodes[0x9C] = Chars32Text.class;//
	nodes[0x9E] = Bytes8Text.class;//
	nodes[0xA0] = Bytes16Text.class;//
	nodes[0xA2] = Bytes32Text.class;//
	nodes[0xA4] = EmptyText.class;//StartList
	nodes[0xA6] = EmptyText.class;//EndList
	nodes[0xA8] = EmptyText.class;//
	nodes[0xAC] = UniqueIdText.class;//
	nodes[0xB0] = UUIDText.class;//
	nodes[0xB2] = UInt64Text.class;//
	nodes[0xB4] = BoolText.class;//
	nodes[0xB6] = UnicodeChars8Text.class;//
	nodes[0xB8] = UnicodeChars16Text.class;//
	nodes[0xBA] = UnicodeChars32Text.class;//

    }

    public static Node getNode(int type) {
	try {
	    Node node = nodes[type].newInstance();
	    if ((type >= 0x5e && type <= 0x77)
		    || (type >= 0x44 && type <= 0x5D)) {
		 ((PrefixCodeNode)node).setType(type);
	    }
	    return node;
	} catch (Exception e) {
	    return null;
	}
    }

    public static Node getEndNode(int type) {
	return null;
    }

    public static boolean hasType(int type) {
	return nodes[type] != null;
    }

    public static Node parseRoot(final InputStream is) throws IOException {
	int type = is.read();
	Node record = getNode(type);
	record.parse(is);
	return record;
    }

    public static Node parseRoot(byte[] bs) throws IOException {
	return parseRoot(new ByteArrayInputStream(bs));
    }
}
