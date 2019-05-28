package br.edu.utfpr.pb.trabalhofinalweb1.converter.formatter;

import java.util.regex.Pattern;

public class CPFFormatter implements IFormatter {

    private final BaseFormatter base;
    public static final Pattern FORMATED = Pattern.compile("(\\d{3})[.](\\d{3})[.](\\d{3})-(\\d{2})");
    public static final Pattern UNFORMATED = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");

    public CPFFormatter() {
        this.base = new BaseFormatter(FORMATED,
                "$1.$2.$3-$4",
                UNFORMATED,
                "$1$2$3$4");
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
