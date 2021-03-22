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

    //add bid
    @RequestMapping("/addbid/{p_id}")
    public String addBid(Model model, @PathVariable Integer p_id) {
        model.addAttribute("product", productRepository.findById(p_id).get());


        return "bidview";
    }



    /* fungerande bid kod, utan mailsender
    @PostMapping("/bidinitdb")
    public String addBidToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        Bid bid = new Bid();
        bid.setPrice(Integer.parseInt(allFormRequestParams.get("price")));
        Product product = productRepository.findById(Integer.parseInt(allFormRequestParams.get("p_id"))).get();
        bid.setUser(userRepository.findById(2).get());
        product.addBid(bid);
        productRepository.save(product);
        return "redirect:/userpage";
    }*/

    @PostMapping("/addbid/{p_id}")
    public String addBidToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        Bid bid = new Bid();
        bid.setPrice(Integer.parseInt(allFormRequestParams.get("price")));
        Product product = productRepository.findById(Integer.parseInt(allFormRequestParams.get("p_id"))).get();
        bid.setUser(userRepository.findByEmail(new SecurityController().loggedInUser()));
        product.addBid(bid);
        productRepository.save(product);

       /* List<String> Bidmails = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            Bidmails.add(user.getEmail());
            sendEmailService.sendBidEmail(user.getEmail(), product.getName(), product.getDescription(), bid.getPrice());
        }
        model.addAttribute("mail", Bidmails);*/
        return "redirect:/page/0";
    }



}//end class
