package hogskolan.auction.auctionhouse.repository;

import hogskolan.auction.auctionhouse.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Integer> {
}
