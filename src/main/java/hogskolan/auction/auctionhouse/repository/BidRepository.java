package hogskolan.auction.auctionhouse.repository;

import hogskolan.auction.auctionhouse.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Integer> {
   @Query(value= "SELECT s FROM Bid s ORDER BY s.price DESC")
   List<Bid>findByOrderByPriceAsc();
}
