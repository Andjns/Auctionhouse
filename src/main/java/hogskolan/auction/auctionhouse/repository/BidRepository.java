package hogskolan.auction.auctionhouse.repository;

import hogskolan.auction.auctionhouse.entity.Bid;
import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Integer> {
   @Query(value= "SELECT s FROM Bid s ORDER BY s.price DESC")
   Bid findByOrderByPriceDesc();


   List<Bid> findTop3ByOrderByPriceDesc();

   List<Bid> findAllByUser(User user);

   List<Bid> findAllByProduct(Product product);






}
