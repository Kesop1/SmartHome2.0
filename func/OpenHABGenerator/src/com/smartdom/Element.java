package com.smartdom;

import org.apache.commons.lang3.StringUtils;

public interface Element {

    String printElement();

    default String formatElement(int length, String element){
        if(element.length()>length) throw new RuntimeException("Element is too long. Please modify it in the config file");
        return StringUtils.rightPad(element, length);
    }
}
