package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.User;
import hogskolan.auction.auctionhouse.repository.CategoryRepository;
import hogskolan.auction.auctionhouse.repository.ProductRepository;
import hogskolan.auction.auctionhouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String showIndex(Model model) {
        return "indexview";
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/initdb")
    public String init() {
        User user = new User();
        user.setName("Adrian");
        user.setPassword(encoder.encode("a"));
        user.setEmail("a@a.a");
        user.setRole("ROLE_ADMIN");
        user.setStatus(1);
        userRepository.save(user);

        User user1 = new User();
        user1.setName("Jens");
        user1.setPassword(encoder.encode("b"));
        user1.setEmail("b@b.b");
        user1.setRole("ROLE_USER");
        user1.setStatus(1);
        userRepository.save(user1);
        return "redirect:/";
    }
}
