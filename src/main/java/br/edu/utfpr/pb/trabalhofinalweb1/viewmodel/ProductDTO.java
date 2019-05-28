package br.edu.utfpr.pb.trabalhofinalweb1.viewmodel;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Brand;
import br.edu.utfpr.pb.trabalhofinalweb1.model.Category;
import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class ProductDTO {
    private Product domain;
    private MultipartFile[] file;

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Double value;

    @NotNull
    private Integer count;

    @NotNull(message = "A categoria não pode estar vazia!")
    private Category category;

    @NotNull(message = "A marca não pode estar vazia!")
    private Brand brand;

    public MultipartFile[] getFile() {
        Optional<MultipartFile[]> opt = Optional.ofNullable(file);
        return opt.orElse(new MultipartFile[]{});
    }

    public void setFile(MultipartFile[] file) {
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Product getDomain() {
        if (domain == null) {
            domain = new Product();
            domain.setId(id);
            domain.setName(name);
            domain.setDescription(description);
            domain.setValue(value);
            domain.setCount(count);
            domain.setCategory(category);
            domain.setBrand(brand);
        }

        return domain;
    }

    public void setDomain(Product domain) {
        this.domain = domain;
    }
}
