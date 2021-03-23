package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.Bid;
import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.entity.User;
import hogskolan.auction.auctionhouse.repository.BidRepository;
import hogskolan.auction.auctionhouse.repository.CategoryRepository;
import hogskolan.auction.auctionhouse.repository.ProductRepository;
import hogskolan.auction.auctionhouse.repository.UserRepository;
import hogskolan.auction.auctionhouse.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class BidController {

@Autowired
    BidRepository bidRepository;
@Autowired
    UserRepository userRepository;
@Autowired
    CategoryRepository categoryRepository;
@Autowired
    ProductRepository productRepository;
@Autowired
    SendEmailService sendEmailService;

    //show all bids
    @GetMapping("/user/allbids/{p_id}")
    public String getAllBids(Model model, @PathVariable Integer p_id) {
        model.addAttribute("bids", bidRepository.findAllByProduct(productRepository.findById(p_id).get()));
        return "productallbidsview";
    }

    //add bid
    @RequestMapping("/addbid/{p_id}")
    public String addBid(Model model, @PathVariable Integer p_id) {
        model.addAttribute("product", productRepository.findById(p_id).get());


        return "bidview";
    }
    @Autowired
    SecurityController sec = new SecurityController();

    @PostMapping("/addbid/{p_id}")
    public String addBid(@PathVariable Integer p_id, @RequestParam Map<String, String> allFormRequestParams) {
        User user = userRepository.findByEmail(new SecurityController().loggedInUser());
        Bid bid = new Bid();
        bid.setPrice(Integer.parseInt(allFormRequestParams.get("price")));
        bid.setUser(user);

        Product product = productRepository.findById(p_id).get();
        product.addBid(bid);

        bidRepository.save(bid);
        productRepository.save(product);
        String title =
                "You bid on a product with the name " +
                        product.getName();
        String body =
                "Your bid: \n" +
                        "Bid: " + bid.getPrice();
        sendEmailService.sendEmail(user.getEmail(), title, body);
        return "redirect:/products/page/0";
    }



}//end class
