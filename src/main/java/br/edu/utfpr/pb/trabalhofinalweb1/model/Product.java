package br.edu.utfpr.pb.trabalhofinalweb1.model;

import br.edu.utfpr.pb.trabalhofinalweb1.converter.formatter.LocalDateDeserializer;
import br.edu.utfpr.pb.trabalhofinalweb1.converter.formatter.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 130, nullable = false)
    private String name;

    @Column(length = 512, nullable = false)
    private String description;

    @Column(nullable = false)
    private Double value = 0.0;

    @Column(nullable = false, columnDefinition = "integer default '0'")
    private Integer count = 0;

    @ManyToOne
    @NotNull(message = "A categoria não pode estar vazia!")
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @NotNull(message = "A marca não pode estar vazia!")
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @Column(name = "createdDate", nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdDate = LocalDate.now();

    @Column(name = "imgs", nullable = true)
    private String[] urlsImgs;
}