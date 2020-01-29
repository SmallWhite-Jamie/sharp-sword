package com.jamie.framework.conf.webmvc.converters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jamie.li
 */
public class StringToDateConverter implements Converter<String, Date> {
 
    @Override
    public Date convert(String source) {
        Date target = null;
        if(!StringUtils.isEmpty(source)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                target =  format.parse(source);
            } catch (ParseException e) {
                throw new RuntimeException(String.format("parser %s to Date fail", source));
            }
        }
        return target;
    }
}