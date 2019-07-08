package br.edu.utfpr.pb.trabalhofinalweb1.model;

import br.edu.utfpr.pb.trabalhofinalweb1.converter.formatter.LocalDateDeserializer;
import br.edu.utfpr.pb.trabalhofinalweb1.converter.formatter.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "`order`")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Order implements Serializable {
    private static final long serialVersionUID = -8727132008984758268L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Informe a data do pedido!")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate orderDate = LocalDate.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = true)
    private LocalDate deliveryDate;

    @ManyToOne()
    @JoinColumn(name = "userclient_id", referencedColumnName = "id", updatable = false)
    @CreatedBy
    private User userClient;

    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @NotNull(message = "Insira ao menos um produto!")
    @NotEmpty(message = "Insira ao menos um produto!")
    private List<OrderItem> orderItems;

    public Double getTotalValue() {
        return orderItems.stream().mapToDouble(vp -> vp.getTotalValue()).sum();
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;

        orderItems.forEach((x) -> x.setOrder(this));
    }
}





