package br.edu.utfpr.pb.trabalhofinalweb1.viewmodel;

import java.util.List;

public class Dataset {

    public String name;
    public List<Integer> value;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Integer> getValue() {
        return value;
    }
    public void setValue(List<Integer> value) {
        this.value = value;
    }
}
