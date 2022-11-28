package horsetailserver;


import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import Util.Protocol;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class OpenDict{
    public static String CheckWord(String word){
        try {
            String url = "https://stdict.korean.go.kr/api/search.do?key=C941E584FFED1B5739ACCC9A8B7D8BC8&type_search=search&q="+word;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(url);

            NodeList nl = doc.getElementsByTagName("item");

            String result = "";

            if(nl.getLength() == 0)
                return Protocol.WORDNOTEXIST;

            result = Protocol.SENDDEF;

            for (int i =0; i<nl.getLength(); i++) {
                Node node = nl.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    result += "//" + getValue("pos", element) + "%" + getValue("definition", element);
                }
            }

            return result;
        }catch(NullPointerException e) {
            return Protocol.WORDNOTEXIST;
        }catch(Exception e) {
            e.printStackTrace();
            return Protocol.SENDWORD_NO;
        }
    }

    private static String getValue(String tag, Element element) {
        NodeList nl = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node value = (Node) nl.item(0);
        return value.getNodeValue();
    }

}

