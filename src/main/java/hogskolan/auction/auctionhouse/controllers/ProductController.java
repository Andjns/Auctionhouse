package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.Category;
import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.entity.User;
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
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private CategoryRepository categoryRepository;

    //show all products
    @GetMapping("/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "productallview";
    }


    //add product
    @RequestMapping("/products/add")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "productaddview";
    }

    //delete product
    @GetMapping("/products/delete/{p_id}")
    public String deleteProductById(@PathVariable Integer p_id) {
        productRepository.deleteById(p_id);
        return "redirect:/products";
    }


    //update product
    @GetMapping("/products/update/{p_id}")
    public String updateProductById(Model model, @PathVariable Integer p_id) {
        model.addAttribute("product", productRepository.findById(p_id).get());
        return "productupdateview";
    }

    @PostMapping("/products/update/{p_id}")
    public String updateProduct(@RequestParam Map<String, String> allFormRequestParams, Integer p_id) {
        Product product = productRepository.findById(p_id).get();
        product.setName(allFormRequestParams.get("name"));
        product.setDescription(allFormRequestParams.get("description"));
        productRepository.save(product);
        return "redirect:/products";
    }


@Autowired
SecurityController sec = new SecurityController();


    @PostMapping("/products/add")
    public String addProductToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        Product product = new Product();
        product.setName(allFormRequestParams.get("name"));
        product.setDescription(allFormRequestParams.get("description"));
        product.setImg(allFormRequestParams.get("img"));
        product.setPrice(0);
        User user = userRepository.findByName(sec.loggedInUser());
        Category category = categoryRepository.findById(Integer.parseInt(allFormRequestParams.get("categoryId"))).get();
        product.setCategory(category);
        user.addProduct(product);
        userRepository.save(user);

        /*
        List<String> mails = new ArrayList<>();
        for(User user : userRepository.findAll()) {
            mails.add(user.getEmail());
            sendEmailService.sendEmail(user.getEmail(), product.getName(), product.getDescription());
        }
        model.addAttribute("mail", mails);
        */
        return "redirect:/products";
    }


}//end Controller class
