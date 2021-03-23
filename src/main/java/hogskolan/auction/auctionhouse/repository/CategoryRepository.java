package hogskolan.auction.auctionhouse.repository;

import hogskolan.auction.auctionhouse.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String category);

}
