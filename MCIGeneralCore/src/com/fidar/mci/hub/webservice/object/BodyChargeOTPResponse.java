/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.mci.hub.webservice.object;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author alireza
 */
public class BodyChargeOTPResponse {
    
    private RecipientChargeOTPResponse value;

    public RecipientChargeOTPResponse getValue() {
        return value;
    }

    @XmlElement(name = "recipient")
    public void setValue(RecipientChargeOTPResponse value) {
        this.value = value;
    }
    
    
    
}
