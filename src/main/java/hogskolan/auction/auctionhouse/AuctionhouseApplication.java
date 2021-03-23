package hogskolan.auction.auctionhouse;

import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AuctionhouseApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuctionhouseApplication.class, args);
	}

}
