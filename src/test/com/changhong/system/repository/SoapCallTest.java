package com.changhong.system.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.xmlpull.v1.XmlPullParserException;

/**
 * User: Jack Wang
 * Date: 15-8-20
 * Time: 下午12:16
 */
public class SoapCallTest {

    public static void main(String[] args) throws Exception {
//        String ns = "urn:com.zznode.se.detector.master.commons:Detector-Master-Service-Adapter:1.0.0";
//        String wsdlUrl = "http://218.6.168.200:8181/cxf/preMachineWS?wsdl";
//
//        //1、创建服务(Service)
//        URL url = new URL(wsdlUrl);
//        QName sname = new QName(ns,"PreMachineWS");
//        Service service = Service.create(url,sname);
//
//        //2、创建Dispatch
//        Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(ns,"PreMachineWSPort"),SOAPMessage.class,Service.Mode.MESSAGE);
//
//        //3、创建SOAPMessage
//        SOAPMessage msg = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
//        SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
//        SOAPBody body = envelope.getBody();
//
//        //4、创建QName来指定消息中传递数据
//        QName ename = new QName(ns,"getPreMachineInfoList","axis");//<nn:add xmlns="xx"/>
//        SOAPBodyElement ele = body.addBodyElement(ename);
//        // 传递参数
////        ele.addChildElement("a").setValue("22");
////        ele.addChildElement("b").setValue("33");
//        msg.writeTo(System.out);
//        System.out.println("\n invoking.....");
//
//        //5、通过Dispatch传递消息,会返回响应消息
//        SOAPMessage response = dispatch.invoke(msg);
//        FileOutputStream out = new FileOutputStream(new File("D:/text.xml"));
//        response.writeTo(out);
////        response.writeTo(System.out);
//        System.out.println();
//
//        //6、响应消息处理,将响应的消息转换为dom对象
//        Document doc = response.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
//        String str = doc.getElementsByTagName("ns:return").item(0).getTextContent();
//        System.out.println(str);

//        // 调用 的方法
//        String serviceUrl = "http://218.6.168.200:8181/cxf/preMachineWS?wsdl";
//        String namespace = "urn:com.zznode.se.detector.master.commons:Detector-Master-Service-Adapter:1.0.0";
//        String methodName = "getPreMachineInfoList";
//        // 创建HttpTransportSE传输对象
//        HttpTransportSE ht = new HttpTransportSE(serviceUrl);
//        try {
//            ht.debug = true;
//            // 使用SOAP1.1协议创建Envelop对象
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            // 实例化SoapObject对象
//            SoapObject soapObject = new SoapObject(namespace, methodName);
//            envelope.bodyOut = soapObject;
//            // 设置与.NET提供的webservice保持较好的兼容性
//            envelope.dotNet = false;
//
//            // 调用webservice
//            ht.call(namespace + methodName, envelope);
//            Object response = envelope.getResponse();
////            if (envelope.getResponse() != null) {
////                // 获取服务器响应返回的SOAP消息
////                SoapObject result = (SoapObject) envelope.bodyIn;
////                SoapObject detail = (SoapObject) result.getProperty(methodName
////                        + "Result");
////                // 解析服务器响应的SOAP消息
////                return parseProvinceOrCity(detail);
////            }
//            System.out.println(11);
//        } catch (SoapFault e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        }

        // WSDL文档的URL，192.168.17.156为PC的ID地址
//        String serviceUrl = "http://218.6.168.200:8181/cxf/preMachineWS?wsdl";
//        // 定义调用的WebService方法名
//        String methodName = "getPreMachineInfoList";
//
//        // 第1步：创建SoapObject对象，并指定WebService的命名空间和调用的方法名
//        SoapObject request = new SoapObject("urn:com.zznode.se.detector.master.commons:Detector-Master-Service-Adapter:1.0.0", methodName);
//
//        // 第2步：设置WebService方法的参数
//
//        // 第3步：创建SoapSerializationEnvelope对象，并指定WebService的版本
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//
//        // 设置bodyOut属性
//        envelope.bodyOut = request;
//
//        // 第4步：创建HttpTransportSE对象，并指定WSDL文档的URL
//        HttpTransportSE ht = new HttpTransportSE(serviceUrl);
//        try {
//            // 第5步：调用WebService
//            ht.call(null, envelope);
//            if (envelope.getResponse() != null) {
//                // 第6步：使用getResponse方法获得WebService方法的返回结果
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // ��1��������SoapObject���󣬲�ָ��WebService������ռ�͵��õķ�����
        SoapObject req = new SoapObject("urn:com.zznode.se.detector.master.commons:Detector-Master-Service-Adapter:1.0.0", "sendDetectResult");

        SoapObject properties1 = new SoapObject();
        properties1.addProperty("host", "218.6.168.200:8080");
        properties1.addProperty("mac", "3434850485048504");
        properties1.addProperty("successCount", "3");
        properties1.addProperty("failureCount", "0");
        properties1.addProperty("averageResponseTime", "500");
        properties1.addProperty("maximumResponseTime", "700");
        properties1.addProperty("minimumResponseTime", "300");

        SoapObject properties2 = new SoapObject();
        properties2.addProperty("host", "218.6.168.201:8080");
        properties2.addProperty("mac", "3434850485048504");
        properties2.addProperty("successCount", "3");
        properties2.addProperty("failureCount", "0");
        properties2.addProperty("averageResponseTime", "500");
        properties2.addProperty("maximumResponseTime", "700");
        properties2.addProperty("minimumResponseTime", "300");

        SoapObject properties3 = new SoapObject();
        properties3.addProperty("host", "218.6.168.202:8080");
        properties3.addProperty("mac", "3434850485048504");
        properties3.addProperty("successCount", "3");
        properties3.addProperty("failureCount", "0");
        properties3.addProperty("averageResponseTime", "500");
        properties3.addProperty("maximumResponseTime", "700");
        properties3.addProperty("minimumResponseTime", "300");

        SoapObject results = new SoapObject();
        results.addProperty("result", properties1);
        results.addProperty("result", properties2);
        results.addProperty("result", properties3);
        req.addProperty("results", results);

        SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelop.bodyOut = req;
        HttpTransportSE htt = new HttpTransportSE("http://218.6.168.200:8181/cxf/preMachineWS?wsdl");
        try {
            htt.call(null, envelop);
            if (envelop.bodyIn != null) {
                System.out.println(envelop.bodyIn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
