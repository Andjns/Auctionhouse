package hogskolan.auction.auctionhouse.repository;

import hogskolan.auction.auctionhouse.entity.Category;
import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByCategory(Category category, Pageable pageable);
    Page<Product> findAllByUser(User user, Pageable pageable);

    List<Product> findAllByExpiredIsFalse();
}
