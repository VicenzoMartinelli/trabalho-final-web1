package br.edu.utfpr.pb.trabalhofinalweb1.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class Order implements Serializable {
    private static final long serialVersionUID = -8727132008984758268L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Informe a data do pedido!")
    private LocalDate orderDate = LocalDate.now();

    @Column(nullable = true)
    private LocalDate deliveryDate;

    @ManyToOne()
    @JoinColumn(name = "userclient_id", referencedColumnName = "id")
    @NotNull(message = "Informe o cliente!")
    private UserClient userClient;

    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @NotNull(message = "Insira ao menos um produto!")
    private List<OrderItem> orderItems;

    public Double getTotalValue() {
        return orderItems.stream().mapToDouble(vp -> vp.getTotalValue()).sum();
    }

}





