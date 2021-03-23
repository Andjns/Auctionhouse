package hogskolan.auction.auctionhouse.entity;

import javax.persistence.*;

@Entity
public class Bid implements Comparable<Object>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer b_id;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Bid() {

    }

    public Bid(Integer price) {
        this.price = price;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getB_id() {
        return b_id;
    }

    public void setB_id(Integer b_id) {
        this.b_id = b_id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    @Override
    public String toString() {
        return "\nBidID:\t" + b_id +
                "\tPrice:\t" + price;
    }

    @Override
    public int compareTo(Object o) {
        Bid b = (Bid) o;
        return this.price - ((Bid) o).getPrice();
    }
}
