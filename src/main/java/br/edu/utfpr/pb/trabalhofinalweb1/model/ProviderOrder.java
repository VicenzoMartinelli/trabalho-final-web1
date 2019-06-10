package br.edu.utfpr.pb.trabalhofinalweb1.model;

import br.edu.utfpr.pb.trabalhofinalweb1.converter.BooleanConverter;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "providerorder")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class ProviderOrder implements Serializable {
    private static final long serialVersionUID = -8717132008984758268L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "provider_id", referencedColumnName = "id")
    @NotNull(message = "Informe o fornecedor do pedido!")
    private Provider provider;

    @Column(nullable = false)
    @NotNull(message = "Informe a data do pedido!")
    private LocalDate orderDate = LocalDate.now();

    @Convert(converter = BooleanConverter.class)
    @Column(nullable = false, columnDefinition = "char(1) default 'V'")
    private boolean delivered;

    @Convert(converter = BooleanConverter.class)
    @Column(nullable = false, columnDefinition = "char(1) default 'V'")
    private boolean canceled;

    @Column(nullable = false, length = 255)
    private String description;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(message = "Informe o usuário do pedido!")
    private User user;

    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @NotNull(message = "Insira ao menos um produto!")
    @NotEmpty(message = "Insira ao menos um produto!")
    private List<ProviderOrderItem> orderItems;

    public Double getTotalValue() {
        return orderItems.stream().mapToDouble(vp -> vp.getTotalValue()).sum();
    }

}





