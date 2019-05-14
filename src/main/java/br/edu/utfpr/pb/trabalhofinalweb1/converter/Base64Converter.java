package br.edu.utfpr.pb.trabalhofinalweb1.converter;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
public class Base64Converter {

    public String encode(String value) {
        return Base64Utils.encodeToString(value.getBytes());
    }

    public String decode(String value) {
        return new String(Base64Utils.decodeFromString(value));
    }
}
