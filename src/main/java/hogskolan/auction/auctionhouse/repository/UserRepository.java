package hogskolan.auction.auctionhouse.repository;

import hogskolan.auction.auctionhouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
