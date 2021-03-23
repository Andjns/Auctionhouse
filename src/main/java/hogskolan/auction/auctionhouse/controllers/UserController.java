package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.Role;
import hogskolan.auction.auctionhouse.entity.User;
import hogskolan.auction.auctionhouse.repository.BidRepository;
import hogskolan.auction.auctionhouse.repository.CategoryRepository;
import hogskolan.auction.auctionhouse.repository.ProductRepository;
import hogskolan.auction.auctionhouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BidRepository bidRepository;

    //show all users
    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "usersallview";
    }

    //Visas i tabellen i adminview
    @RequestMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("cat", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "adminview";
    }


    //add user
    @RequestMapping("/admin/users/add")
    public String addUser(Model model) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER", true));
        roles.add(new Role("ROLE_ADMIN", false));
        model.addAttribute("roles", roles);
        return "useraddview";
    }

    @Autowired
    PasswordEncoder encoder;

    //add user
    @PostMapping("/admin/users/add")
    public String addUserToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        User user = new User();
        user.setName(allFormRequestParams.get("name"));
        user.setPassword(encoder.encode(allFormRequestParams.get("password")));
        user.setEmail(allFormRequestParams.get("email"));
        user.setRole(allFormRequestParams.get("role"));
        user.setStatus(1);

        userRepository.save(user);

        return "redirect:/admin";
    }

    //delete user
    @GetMapping("/admin/users/delete/{u_id}")
    public String deleteUserById(@PathVariable Integer u_id) {
        userRepository.deleteById(u_id);
        return "redirect:/admin";
    }


    //update
    @GetMapping("/admin/users/update/{u_id}")
    public String updateUserById(Model model, @PathVariable Integer u_id) {
        User user = userRepository.findById(u_id).get();
        model.addAttribute("user", user);
        List<Role> roles = new ArrayList<>();
        System.out.println(user);
        if (user.getRole().equals("ROLE_USER")) {
            roles.add(new Role("ROLE_USER", true));
            roles.add(new Role("ROLE_ADMIN", false));
        } else {
            roles.add(new Role("ROLE_USER", false));
            roles.add(new Role("ROLE_ADMIN", true));
        }
        model.addAttribute("roles", roles);
        return "userupdateview";
    }

    //update
    @PostMapping("/admin/users/update/{u_id}")
    public String updateUser(@RequestParam Map<String, String> allFormRequestParams, Integer u_id) {
        User user = userRepository.findById(u_id).get();
        user.setName(allFormRequestParams.get("name"));
        user.setEmail(allFormRequestParams.get("email"));
        user.setPassword(encoder.encode(allFormRequestParams.get("password")));
        user.setRole(allFormRequestParams.get("role"));
        System.out.println(user);
        userRepository.save(user);
        return "redirect:/admin";
    }

    //see seller info

    @GetMapping("/user/sellerinfo/{u_id}")
    public String sellerInfo(Model model, @PathVariable Integer u_id) {
        model.addAttribute("user", userRepository.findById(u_id).get());
        return "usersellerinfoview";
    }

}