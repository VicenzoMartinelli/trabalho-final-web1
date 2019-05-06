package br.edu.utfpr.pb.trabalhofinalweb1.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class BooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return (Boolean.TRUE.equals(value) ? "V" : "F");
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "V".equals(value);
    }

}
