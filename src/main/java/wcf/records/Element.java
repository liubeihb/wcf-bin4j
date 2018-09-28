package wcf.records;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import wcf.Node;
import wcf.NodeFactory;
import wcf.records.elements.ShortElement;
import wcf.records.texts.Chars8Text;
/**
 * 元素基类，提供子节点和属性的基本实现
 * @author Jing
 *
 */
public abstract class Element implements Node{
	protected String name;
	protected List<Node> childs=new ArrayList<Node>();
	protected List<Node> attributes=new ArrayList<Node>();
	
	public List<Node> getChilds() {
		return childs;
	}

	public List<Node> getAttribute() {
		return attributes;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
	    this.name=name;
	}
	@Override
	public void parse(InputStream is) throws IOException{
		int type=is.read();
		while(type!=0x01){

		    try{
			if(type>=0x03&&type<0x40){//attribute
				Node attr=NodeFactory.getNode(type);
				attr.parse(is);
				attributes.add(attr);
			}else{//ELEMENT or TEXT
				Node child=null;
				if(NodeFactory.hasType(type)){
					child=NodeFactory.getNode(type);
					childs.add(child);
					child.parse(is);
				}else{
					child=NodeFactory.getNode(type-1);
					childs.add(child);
					child.parse(is);
					break;
				}
			}
		    }catch(NullPointerException e){
			System.err.println(type);
			throw new RuntimeException(type+"没有找到");
		    }
			type=is.read();
		}
	}
	protected abstract void toNameBytes(OutputStream os) throws Exception;

	@Override
	public void toBytes(OutputStream os) throws Exception {
	    Node.super.toBytes(os);
	    toNameBytes(os);

	    attrsBytes(os);
	    childsBytes(os);
	}
	public void attrsBytes(OutputStream os) throws Exception{
	    for(Node attr :attributes){
		attr.toBytes(os);
	    }
	}
	public void childsBytes(OutputStream os) throws Exception{
	    for(Node c :childs){
		c.toBytes(os);
	    }
	    if(childs.size()==0||!(childs.get(childs.size()-1) instanceof Text))
		os.write(0x01);
	}
	@Override
	public String toXML() {
		return "\r\n<"+name+getAttributeXml()+">"+getChildXml()+"</"+name+">";
	}
	public String getAttributeXml(){
	    String attrs="";
	    for(Node attr :attributes){
		attrs+=" "+attr.toXML();
	    }
	    return attrs;
	}
	public String getChildXml(){
	    String c="";
	    for(Node n :childs){
		c+=n.toXML();
	    }
	    if(childs.get(childs.size()-1) instanceof Element)
		    c+="\r\n";
	    return c;
	}
	
	public static Element getShortElement(String name,String value){
	    Element el=new ShortElement();
	    el.name=name;
	    Text<String> t=new Chars8Text();
	    t.value=value;
	    el.childs.add(t);
	    return el;
	}
}
