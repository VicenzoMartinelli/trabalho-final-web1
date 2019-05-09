package br.edu.utfpr.pb.trabalhofinalweb1.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "region")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Region implements Serializable {
    private static final long serialVersionUID = -8727132008984758268L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Preencha o campo nome!")
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotBlank(message = "Preencha o campo sigla!")
    @Column(name = "initials", length = 2, nullable = false)
    private String initials;
}





