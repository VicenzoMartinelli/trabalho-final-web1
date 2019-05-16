package br.edu.utfpr.pb.trabalhofinalweb1.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "provider")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Provider implements Serializable {
    private static final long serialVersionUID = -8727112008184758268L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Preencha o campo nome!")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "Preencha o campo cnpj!")
    @Column(name = "cnpj", length = 14, nullable = false)
    private String cnpj;

    @ManyToOne()
    @NotNull(message = "Preencha o campo city!")
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(length = 30, nullable = false)
    @NotNull(message = "Preencha o campo telefone!")
    private String phone;

    @Column(length = 130, nullable = false)
    @NotNull(message = "Preencha o campo endereço!")
    private String address;

    @Column(length = 80, nullable = false)
    @NotNull(message = "Preencha o campo bairro!")
    private String burgh;

    @Column(length = 15, nullable = false)
    @NotNull(message = "Preencha o campo CEP!")
    private String cepCode;

    @Column(nullable = false)
    @NotNull(message = "Preencha o campo Nº !")
    private Integer addressNumber;
}





