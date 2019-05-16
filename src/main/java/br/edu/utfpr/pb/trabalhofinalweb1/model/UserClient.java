package br.edu.utfpr.pb.trabalhofinalweb1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
@ToString
public class UserClient extends User implements Serializable {
    private static final long serialVersionUID = 112312315123123L;

    @NotBlank(message = "Preencha o campo cnpj!")
    @Column(name = "cpf", length = 14, nullable = false)
    private String cpf;

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
    private Integer addressNumbers;
}
