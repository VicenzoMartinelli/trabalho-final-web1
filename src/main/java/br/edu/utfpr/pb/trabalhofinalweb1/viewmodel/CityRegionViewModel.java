package br.edu.utfpr.pb.trabalhofinalweb1.viewmodel;

public class CityRegionViewModel {
    private Integer value;
    private String label;

    public CityRegionViewModel() {
    }

    public CityRegionViewModel(Integer id, String cityName, String regionName) {
        this.value = id;
        this.label = cityName + " - " + regionName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
