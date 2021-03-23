package hogskolan.auction.auctionhouse.repository;

import hogskolan.auction.auctionhouse.entity.Bid;
import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Integer> {
   List<Bid> findByProduct(Product product);
   List<Bid> findAllByUser(User user);

   List<Bid> findAllByProduct(Product product);
}
