package wcf.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.log4j.Logger;

/**
 * http 工具类<br/>
 * 用法:<br/>
 * String result=HttpUtil.init("http://ip:port/xxxx").send("content");<br/>
 * 
 * @author JYang
 *
 */
public class HttpUtil {
	/**
	 * POST
	 */
	public static final String POST="POST";
	/**
	 * GET
	 */
	public static final String GET="GET";
	
	private HttpURLConnection connection;
	private HttpUtil(String url){
		try {
			if(StringUtils.isEmpty(url)||(!url.startsWith("http")&&!url.startsWith("https")))
				throw new ConnectException("url不正确,只能使用http/https！");
			connection= (HttpURLConnection) new URL(url).openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			//增加https支持
			if(url.startsWith("https://")){
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null,  new TrustManager[] {new MyTrustManager(null)},new java.security.SecureRandom());

				((HttpsURLConnection)connection).setSSLSocketFactory(sc.getSocketFactory());
				((HttpsURLConnection)connection).setHostnameVerifier(new HostnameVerifier(){
			    	public boolean verify(String hostname, SSLSession session) {
			            return true;
			        }
			    });
			}
		} catch (Exception e) {
			Logger.getLogger("wcf4j.error").error(e);
			connection= null;
		}
	}
	/**
	 * 初始化HTTPUtil对象
	 * @param url 链接地址，必须为http/https协议
	 * @return HTTPUtil实例
	 */
	public static HttpUtil init(String url){
		HttpUtil httpUtil=new HttpUtil(url);		
		if(httpUtil.connection==null)
			return null;
		return httpUtil.setMethod(POST);
	}
	/**
	 * 添加http请求头部参数信息<br/>
	 * 以键值对形式调用,eg:<br/>
	 *  addHeader("ContentType","application/json;charset=UTF-8")<br/>
	 *  addHeader("Pragma", "No-cache")<br/>
	 * @param type 请求头部参数类型
	 * @param value 请求头部参数值
	 * @return HTTPUtil实例
	 */
	public HttpUtil addHeader(String type,String value){
		connIsNull();
		connection.addRequestProperty(type, value);
		return this;
	}
	/**
	 * 添加http请求头部参数信息<br/>
	 * 以集合方式存储参数,eg:</br>
	 * Map<String, String> headers=new HashMap<String, String>();</br>
	 * headers.put("ContentType","application/json;charset=UTF-8");</br>
	 * headers.put("Pragma", "No-cache");<br/>
	 * addHeader(headers);
	 * @param headers header键值对集合
	 * @return HTTPUtil实例
	 */
	public HttpUtil addHeader(Map<String, String> headers){
		if(headers!=null&&headers.size()>0)
			for(Entry<String, String> entry:headers.entrySet())
				addHeader(entry.getKey(), entry.getValue());
		return this;
	}
	/**
	 * 添加http请求头部参数信息<br/>
	 * 以[HEADER_NAME]:[HEADER_VALUE]方式存储头部参数,eg:<br/>
	 * addHeader("ContentType:application/json;charset=UTF-8");
	 * @param header 键值对字符串
	 * @return HTTPUtil实例
	 */
	public HttpUtil addHeader(String header){
		if(StringUtils.isNotEmpty(header)&&header.contains(":"))
			addHeader(header.split(":")[0], header.split(":")[1]);
		return this;
	}
	/**
	 * 添加http请求头部参数信息<br/>
	 * 以[HEADER_NAME]:[HEADER_VALUE]数组方式存储头部参数,eg:<br/>
	 * addHeader("ContentType:application/json;charset=UTF-8","Pragma:No-cache");
	 * @param header 键值对数组
	 * @return HTTPUtil实例
	 */
	public HttpUtil addHeader(String ... header){
		if(header!=null&&header.length>0)
			for(String head:header)
				addHeader(head);
		return this;
	}
	/**
	 * 设置超时时间，单位毫秒
	 * @param rtm 响应超时
	 * @param ctm 连接超时
	 * @return HTTPUtil实例
	 */
	public HttpUtil setTimeout(int rtm,int ctm){
		connIsNull();
		connection.setReadTimeout(rtm);
		connection.setConnectTimeout(ctm);
		return this;
	}
	/**
	 * 设置ContentType
	 * @param type 类型,eg:application/json text/html
	 * @param encoding 编码
	 * @return HTTPUtil实例
	 */
	public HttpUtil setContentType(String type,String encoding){
		return this.addHeader("Content-Type",type + (StringUtils.isEmpty(encoding)?"":(";charset=" + encoding)));
	}
	/**
	 * 设置请求方式,目前支持POST/GET两种<br/>
	 * setMethod(HttpUtil.POST)<br/>
	 * setMethod(HttpUtil.GET)
	 * @param method
	 * @return HTTPUtil实例
	 */
	public HttpUtil setMethod(String method){
		connIsNull();
		try {
			connection.setRequestMethod(method);
		} catch (ProtocolException e) {
			Logger.getLogger("wcf4j.error").error(e);
			return null;
		}
		return this;
	}
	/**
	 * 以指定编码发送数据<br/>
	 * send("{\"message\":\"1\"}","UTF-8");<br/>
	 * 本方法将在请求成功后返回响应内容
	 * @param msg 发送数据
	 * @param encode 数据编码
	 * @return 响应数据
	 */
	public <T> T send(String msg,ExceptionFunction<InputStream,T> t){
		return send(msg, "UTF-8",t);
	}
	public <T> T send(String msg,String encode,ExceptionFunction<InputStream,T> t){
	    	try{
	    	    return send(StringUtils.isEmpty(msg)?null:msg.getBytes(encode), null,t);
	    	}catch(Exception e){
	    	    throw new RuntimeException(e);
	    	}
	}
	public <T> T send(byte[] msg,Map<String, List<String>> heads,ExceptionFunction<InputStream,T> t){
		connIsNull();
		try {
			if(msg!=null&&msg.length>0){
				IOUtils.write(msg, connection.getOutputStream());
			}
			if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
				if(heads!=null)
					heads.putAll(connection.getHeaderFields());
				return t.apply(connection.getInputStream());
			}else{
				throw new RuntimeException("接受数据失败！"+connection.getResponseCode()+" : "+connection.getResponseMessage());
			}
		} catch (Exception e) {
			throw new RuntimeException("网络异常，接受数据失败！",e);
		}finally{
			close();
		}
	}

	public String send(String msg,String encode){
		return send(msg ,encode, is->{
			return IOUtils.toString(connection.getInputStream(),encode);
		});
	}
	/**
	 * 以UTF-8编码发送数据
	 * @param msg 发送数据
	 * @return 响应数据
	 */
	public String send(String msg){
		return send(msg,"UTF-8");
	}
	public String send(String msg,Map<String, List<String>> heads){		
		return send(msg, "UTF-8", is->{
			return IOUtils.toString(connection.getInputStream(),"UTF-8");
		});
	}
	
	public URLConnection getConnection(){
		connIsNull();
		return connection;
	}
	
	public void close(){
		IOUtils.close(connection);
	}
	
	private void connIsNull(){
		if(connection==null){
			throw new RuntimeException("无法建立连接!");
		}
	}
}
/**
 * 用于支持https的信任证书管理器
 * @author JYang RocKontrol
 *
 */
class MyTrustManager implements X509TrustManager{
	KeyStore ks;
	public MyTrustManager(KeyStore ks){
		this.ks=ks;
	}
	public void checkClientTrusted(X509Certificate[] chain,String authType) 
		throws CertificateException {
	}
	public void checkServerTrusted(X509Certificate[] chain,
			String authType) throws CertificateException {
	}
	public X509Certificate[] getAcceptedIssuers() {
		if(ks==null)return null;
		try {
			Enumeration<String> e=ks.aliases();
	        List<X509Certificate> list=new ArrayList<X509Certificate>();
	        while(e.hasMoreElements()){
	           	list.add((X509Certificate) ks.getCertificate(e.nextElement()));
	        }
			return list.toArray(new X509Certificate[2]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

