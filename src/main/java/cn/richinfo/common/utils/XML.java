package cn.richinfo.common.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author 123
 * 
 */
public class XML {
	/**
	 * 从指定的 URL 加载 XML 文档。
	 * 
	 * @param path 地址
	 * @return
	 * @throws Exception
	 */
	public static Document Load(String path) throws Exception {
		if (path == null || path == "") {
			throw new Exception("path不能为空");
		}
		SAXReader reader = new SAXReader();
		File file = new File(path);
		try {
			Document doc = reader.read(file);
			return doc;
		} catch (DocumentException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * 从指定的字符串加载 XML 文档。
	 * 
	 * @param xml
	 *            ,encoding
	 * @return
	 * @throws Exception
	 */
	public static Document LoadXml(String xml, String encoding)
			throws Exception {
		if (xml == null || xml == "") {
			throw new Exception("xml不能为空");
		}
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new ByteArrayInputStream(xml
					.getBytes(encoding)));
			return doc;
		} catch (DocumentException e) {
			throw new DocumentException(e);
		}
	}

	/**
	 * 从指定的字符串加载 XML 文档。
	 * 
	 * @param xml
	 *            ,encoding
	 * @return
	 * @throws Exception
	 */
	public static Document LoadXml(String xml) throws Exception {

		return LoadXml(xml, Charset.defaultCharset().name());
	}

	/**
	 * 获取Node
	 * 
	 * @param xmlDoc
	 * @param xPath
	 * @return
	 * @throws Exception
	 */
	public static org.dom4j.Node GetSingleNode(Document xmlDoc, String xPath)
			throws Exception {
		if (xmlDoc == null) {
			throw new Exception("xmlDoc不能为空");
		}

		if (xPath == null || xPath == "") {
			throw new Exception("path不能为空");
		}

		return xmlDoc.selectSingleNode(xPath);
	}

	/**
	 * 获取Node
	 * 
	 * @param xmlDoc
	 * @param xPath
	 * @return
	 * @throws Exception
	 */
	public static String GetSingleNodeText(Document xmlDoc, String xPath)
			throws Exception {
		if (xmlDoc == null) {
			throw new Exception("xmlDoc不能为空");
		}

		if (xPath == null || xPath == "") {
			throw new Exception("path不能为空");
		}

		org.dom4j.Node node= xmlDoc.selectSingleNode(xPath);
		if(node==null)
		{
			return "";
		}
		return node.getText();
	}
	
	/**
	 * 获取Node
	 * 
	 * @param xmlDoc
	 * @param xPath
	 * @return
	 * @throws Exception
	 */
	public static String GetSingleNodeText(Element element, String xPath)
			throws Exception {
		if (element == null) {
			throw new Exception("element不能为空");
		}

		if (xPath == null || xPath == "") {
			throw new Exception("path不能为空");
		}

		org.dom4j.Node node= element.selectSingleNode(xPath);
		if(node==null)
		{
			return "";
		}
		return node.getText();
	}
	
	/**
	 * 获取selectNodes
	 * 
	 * @param xmlDoc
	 * @param xPath
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List selectNodes(Document xmlDoc, String xPath)
			throws Exception {
		if (xmlDoc == null) {
			throw new Exception("xmlDoc不能为空");
		}

		if (xPath == null || xPath == "") {
			throw new Exception("path不能为空");
		}

		return xmlDoc.selectNodes(xPath);
	}
}
