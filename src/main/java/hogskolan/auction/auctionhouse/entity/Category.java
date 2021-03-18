package hogskolan.auction.auctionhouse.entity;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer c_id;
    private String name;

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> addprod = new ArrayList<>();


    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public void addProduct(Product product) {
        addprod.add(product);
        product.setCategory(this);
    }
    public void removeProduct(Product product) {
        addprod.remove(product);
        product.setCategory(this);
    }


    public Integer getC_id() {
        return c_id;
    }

    public void setC_id(Integer c_id) {
        this.c_id = c_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
