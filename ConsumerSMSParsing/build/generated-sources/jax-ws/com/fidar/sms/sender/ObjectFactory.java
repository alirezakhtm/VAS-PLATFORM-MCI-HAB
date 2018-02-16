
package com.fidar.sms.sender;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fidar.sms.sender package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendSMS_QNAME = new QName("http://sender.sms.fidar.com/", "sendSMS");
    private final static QName _SendSMSResponse_QNAME = new QName("http://sender.sms.fidar.com/", "sendSMSResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fidar.sms.sender
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendSMS_Type }
     * 
     */
    public SendSMS_Type createSendSMS_Type() {
        return new SendSMS_Type();
    }

    /**
     * Create an instance of {@link SendSMSResponse }
     * 
     */
    public SendSMSResponse createSendSMSResponse() {
        return new SendSMSResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSMS_Type }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sender.sms.fidar.com/", name = "sendSMS")
    public JAXBElement<SendSMS_Type> createSendSMS(SendSMS_Type value) {
        return new JAXBElement<SendSMS_Type>(_SendSMS_QNAME, SendSMS_Type.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendSMSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sender.sms.fidar.com/", name = "sendSMSResponse")
    public JAXBElement<SendSMSResponse> createSendSMSResponse(SendSMSResponse value) {
        return new JAXBElement<SendSMSResponse>(_SendSMSResponse_QNAME, SendSMSResponse.class, null, value);
    }

}
