package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.Category;
import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.entity.User;
import hogskolan.auction.auctionhouse.repository.CategoryRepository;
import hogskolan.auction.auctionhouse.repository.ProductRepository;
import hogskolan.auction.auctionhouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    //show all users
    @GetMapping("/allusers")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "allusers";
    }

    //Visas i tabellen i adminview
    @RequestMapping("/adminview")
    public String showAdmin(Model model) {
        model.addAttribute("add", productRepository.findAll());
        model.addAttribute("cat", categoryRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "adminview";
    }


    //add user
    @RequestMapping("/adduser")
    public String addUser(Model model) {
        model.addAttribute("user", userRepository.findAll());
        return "adduser";
    }

    //add user
    @PostMapping("/userinitdb")
    public String addUserToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        User user = new User();
        user.setName(allFormRequestParams.get("name"));
        user.setEmail(allFormRequestParams.get("email"));

        userRepository.save(user);

        return "redirect:/adminview";
    }

    //delete user
    @GetMapping("/deleteuser/{u_id}")
    public String deleteUserById(@PathVariable Integer u_id) {
        userRepository.deleteById(u_id);
        return "redirect:/adminview";
    }


    //update
    @GetMapping("/updateuser/{u_id}")
    public String updateUserById(Model model, @PathVariable Integer u_id) {
        model.addAttribute("user", userRepository.findById(u_id).get());
        return "updateuser";
    }

    //update
    @PostMapping("/updateduser")
    public String updateUser(@RequestParam Map<String, String> allFormRequestParams, Integer u_id) {
        User user = userRepository.findById(u_id).get();
        user.setName(allFormRequestParams.get("name"));
        user.setEmail(allFormRequestParams.get("email"));
        userRepository.save(user);
        return "redirect:/adminview";
    }

    //User Page
    //visa produkter i userpage
    @GetMapping("/userpage")
    public String getAllProducts(Model model) {
        model.addAttribute("add", productRepository.findAll());
        return "userpage";
    }


    //show by category
    @GetMapping("/findbeer")
    public String getAllBeer(Model model) {
        model.addAttribute("beer", categoryRepository.findById(1).get());

        return "beer";
    }



}//end class
//dunke