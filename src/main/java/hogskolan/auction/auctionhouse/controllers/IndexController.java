package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.Category;
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
    private CategoryRepository categoryRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/initdb")
    public String init() {
        User user1 = new User();
        user1.setName("Adrian");
        user1.setPassword(encoder.encode("a"));
        user1.setEmail("a@a.a");
        user1.setRole("ROLE_ADMIN");
        user1.setStatus(1);

        User user2 = new User();
        user2.setName("Jens");
        user2.setPassword(encoder.encode("b"));
        user2.setEmail("b@b.b");
        user2.setRole("ROLE_USER");
        user2.setStatus(1);

        Category category1 = new Category("Beer");
        Category category2 = new Category("Wine");
        Category category3 = new Category("Spirit");

        userRepository.save(user1);
        userRepository.save(user2);
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        return "redirect:/";
    }
}
