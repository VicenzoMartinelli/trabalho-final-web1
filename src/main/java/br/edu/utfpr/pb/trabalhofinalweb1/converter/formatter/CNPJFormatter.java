package br.edu.utfpr.pb.trabalhofinalweb1.converter.formatter;

import java.util.regex.Pattern;

public class CNPJFormatter implements IFormatter {
    public static final Pattern FORMATED = Pattern.compile("(\\d{2})[.](\\d{3})[.](\\d{3})/(\\d{4})-(\\d{2})");
    public static final Pattern UNFORMATED = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
    private final BaseFormatter base;

    public CNPJFormatter() {
        this.base = new BaseFormatter(FORMATED, "$1.$2.$3/$4-$5", UNFORMATED, "$1$2$3$4$5");
    }

    @Override
    public String format(String value) {
        return base.format(value);
    }

    @Override
    public String unformat(String value) {
        return base.unformat(value);
    }

    @Override
    public boolean isFormatted(String value) {
        return base.isFormatted(value);
    }

    @Override
    public boolean canBeFormatted(String value) {
        return base.canBeFormatted(value);
    }

}