package br.edu.utfpr.pb.trabalhofinalweb1.model;

import br.edu.utfpr.pb.trabalhofinalweb1.converter.BooleanConverter;
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
@Table(name = "providerorder")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@EntityListeners(AuditingEntityListener.class)
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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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
    @CreatedBy
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    private User user;

    @CreatedDate
    @Column
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate createdDate;

    @OneToMany(
            mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    @NotNull(message = "Insira ao menos um produto!")
    @NotEmpty(message = "Insira ao menos um produto!")
    private List<ProviderOrderItem> orderItems;

    public void setOrderItems(List<ProviderOrderItem> orderItems) {
        this.orderItems = orderItems;

        orderItems.forEach((x) -> x.setOrder(this));
    }

    public Double getTotalValue() {
        return orderItems.stream().mapToDouble(vp -> vp.getTotalValue()).sum();
    }

}





