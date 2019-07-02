package br.edu.utfpr.pb.trabalhofinalweb1.viewmodel;

import br.edu.utfpr.pb.trabalhofinalweb1.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProductListViewModel {
    private Page<Product> products;
    private List<Integer> pageNumbers;
    private long totalCount;

    public ProductListViewModel(Page<Product> products, List<Integer> pageNumbers, long totalCount) {
        this.products    = products;
        this.pageNumbers = pageNumbers;
        this.totalCount  = totalCount;
    }

    public ProductListViewModel() {

    }

    public Page<Product> getProducts() {
        return products;
    }

    public void setProducts(Page<Product> products) {
        this.products = products;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(List<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
