package br.edu.utfpr.pb.trabalhofinalweb1.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "providerorderitem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
public class ProviderOrderItem implements Serializable {
    private static final long serialVersionUID = -8727132000184758268L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Double value = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = true)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "providerorder_id", referencedColumnName = "id")
    private ProviderOrder order;

    public Double getTotalValue() {
        return this.product.getValue() * this.count;
    }
}
