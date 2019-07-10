package br.edu.utfpr.pb.trabalhofinalweb1.repository;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RepositoryOrder extends JpaRepository<Order, Long> {
    Page<Order> findAllByUserClientUsernameOrderByOrderDateDesc(Pageable pageable, String username);

    @Query(value = "SELECT to_char(order_date, 'DD/MM/YYYY') as data, CAST(count(*) AS integer) as total " +
            "FROM \"order\" " +
            "GROUP BY order_date " +
            "ORDER BY ORDER_DATE DESC LIMIT 30",
            nativeQuery = true)
    List<Object[]> findOrdersGroupByDayLast30Days();

    @Query(value = "SELECT cat.name, CAST(sum(ordI.count)as integer) " +
            "FROM \"order\" ord " +
            "INNER JOIN orderitem ordI ON ord.id = ordI.order_id " +
            "INNER JOIN product prod ON ordI.product_id = prod.id " +
            "INNER JOIN category cat on cat.id = prod.category_id " +
            "GROUP BY cat.id, cat.name",
    nativeQuery = true)
    List<Object[]> findCountOfSellProductsPerCategories();
}