package hogskolan.auction.auctionhouse.entity;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer u_id;
    private String name;
    private String password;
    private String email;
    private String role;
    private Integer status;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> addprod = new ArrayList<>();

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Bid> addbid = new ArrayList<>();

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void addProduct(Product product) {
        addprod.add(product);
        product.setUser(this);
    }
    public void removeProduct(Product product) {
        addprod.remove(product);
        product.setUser(this);
    }

    public void addBid(Bid bid) {
        addbid.add(bid);
        bid.setUser(this);
    }
    public void removeBid(Bid bid) {
        addbid.remove(bid);
        bid.setUser(this);
    }



    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
