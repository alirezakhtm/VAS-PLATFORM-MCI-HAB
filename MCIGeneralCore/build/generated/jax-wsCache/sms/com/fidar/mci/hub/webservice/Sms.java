
package com.fidar.mci.hub.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "Sms", targetNamespace = "http://tempuri.org/", wsdlLocation = "file:/home/alirezakhtm/Projects/Company/NetBeanse/VAS%20PLATFORM%20MCI-%20HUB/MCIGeneralCore/sms.wsdl")
public class Sms
    extends Service
{

    private final static URL SMS_WSDL_LOCATION;
    private final static WebServiceException SMS_EXCEPTION;
    private final static QName SMS_QNAME = new QName("http://tempuri.org/", "Sms");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/home/alirezakhtm/Projects/Company/NetBeanse/VAS%20PLATFORM%20MCI-%20HUB/MCIGeneralCore/sms.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SMS_WSDL_LOCATION = url;
        SMS_EXCEPTION = e;
    }

    public Sms() {
        super(__getWsdlLocation(), SMS_QNAME);
    }

    public Sms(WebServiceFeature... features) {
        super(__getWsdlLocation(), SMS_QNAME, features);
    }

    public Sms(URL wsdlLocation) {
        super(wsdlLocation, SMS_QNAME);
    }

    public Sms(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SMS_QNAME, features);
    }

    public Sms(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Sms(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns SmsSoap
     */
    @WebEndpoint(name = "SmsSoap")
    public SmsSoap getSmsSoap() {
        return super.getPort(new QName("http://tempuri.org/", "SmsSoap"), SmsSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SmsSoap
     */
    @WebEndpoint(name = "SmsSoap")
    public SmsSoap getSmsSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "SmsSoap"), SmsSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns SmsSoap
     */
    @WebEndpoint(name = "SmsSoap12")
    public SmsSoap getSmsSoap12() {
        return super.getPort(new QName("http://tempuri.org/", "SmsSoap12"), SmsSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SmsSoap
     */
    @WebEndpoint(name = "SmsSoap12")
    public SmsSoap getSmsSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://tempuri.org/", "SmsSoap12"), SmsSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SMS_EXCEPTION!= null) {
            throw SMS_EXCEPTION;
        }
        return SMS_WSDL_LOCATION;
    }

}
