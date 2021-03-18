package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.Bid;
import hogskolan.auction.auctionhouse.entity.Category;
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

import java.util.ArrayList;
import java.util.List;
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


    //update product
    @GetMapping("/updatebid/{p_id}")
    public String updateProductById(Model model, @PathVariable Integer p_id) {
        model.addAttribute("product", productRepository.findById(p_id).get());
        return "bidview";
    }

    @PostMapping("/updatedbid")
    public String updateProduct(@RequestParam Map<String, String> allFormRequestParams, Integer p_id) {
        Product product = productRepository.findById(p_id).get();
        product.setName(allFormRequestParams.get("name"));
        product.setDescription(allFormRequestParams.get("description"));
        product.setPrice(Integer.parseInt(allFormRequestParams.get("price")));
        productRepository.save(product);
        return "redirect:/userpage";
    }


/*
    //add bid
    @RequestMapping("/addbid/{p_id}")
    public String addBid(Model model, @PathVariable Integer p_id) {
        model.addAttribute("bid", productRepository.findById(p_id).get());

        return "userpage";
    }

    @PostMapping("/bidinitdb")
    public String addBidToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        Bid bid = new Bid();
        bid.setPrice(Integer.parseInt(allFormRequestParams.get("price")));
        Product product = productRepository.findById(1).get();
        product.addBid(bid);
        User user2 = userRepository.findById(2).get();
        user2.addBid(bid);
        user2.addProduct(product);
        userRepository.save(user2);



        List<String> mails = new ArrayList<>();
        for(User user : userRepository.findAll()) {
            mails.add(user.getEmail());
            sendEmailService.sendEmail(user.getEmail(), user.getName(), user.getName());
        }
        model.addAttribute("mail", mails);
        return "redirect:/addbid/{p_id}";
    }

 */
}
