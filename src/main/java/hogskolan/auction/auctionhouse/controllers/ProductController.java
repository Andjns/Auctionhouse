package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.Category;
import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.entity.User;
import hogskolan.auction.auctionhouse.repository.CategoryRepository;
import hogskolan.auction.auctionhouse.repository.ProductRepository;
import hogskolan.auction.auctionhouse.repository.UserRepository;
import hogskolan.auction.auctionhouse.service.SendEmailService;
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
    @GetMapping("/allproducts")
    public String getAllProducts(Model model) {
        model.addAttribute("add", productRepository.findAll());
        return "allproduct";
    }



    //add product
    @RequestMapping("/addproduct")
    public String addProduct(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "addproduct";
    }


    @PostMapping("/productinitdb")
    public String addProductToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        Product product = new Product();
        product.setName(allFormRequestParams.get("name"));
        product.setDescription(allFormRequestParams.get("description"));
        product.setImg(allFormRequestParams.get("img"));
        product.setPrice(0);
        User user1 = userRepository.findById(1).get();
        Category category = categoryRepository.findById(Integer.parseInt(allFormRequestParams.get("categoryId"))).get();
        product.setCategory(category);
        user1.addProduct(product);
        userRepository.save(user1);



        List<String> mails = new ArrayList<>();
        for(User user : userRepository.findAll()) {
            mails.add(user.getEmail());
            sendEmailService.sendEmail(user.getEmail(), product.getName(), product.getDescription());
        }
        model.addAttribute("mail", mails);
        return "redirect:/allproducts";
    }

    //delete product
    @GetMapping("/deleteproduct/{p_id}")
    public String deleteProductById(@PathVariable Integer p_id) {
        productRepository.deleteById(p_id);
        return "redirect:/allproducts";
    }


    //update product
    @GetMapping("/update/{p_id}")
    public String updateProductById(Model model, @PathVariable Integer p_id) {
        model.addAttribute("product", productRepository.findById(p_id).get());
        return "updateview";
    }

    @PostMapping("/updated")
    public String updateProduct(@RequestParam Map<String, String> allFormRequestParams, Integer p_id) {
        Product product = productRepository.findById(p_id).get();
        product.setName(allFormRequestParams.get("name"));
        product.setDescription(allFormRequestParams.get("description"));
        productRepository.save(product);
        return "redirect:/allproducts";
    }

    //Paging
    @GetMapping("/page/{pageno}")
    public String showPage(@PathVariable() Integer pageno, Model model) {

        if (pageno<0 || pageno == null) {
            pageno = 0;
        }
        final int PAGESIZE = 3; //number of product on each page
        //get the next page based on its pagenumber, zerobased
        //also set pagesize, the number of products on the page
        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Product> pagedResult = productRepository.findAll(paging);
        List<Product> listProducts;
        //returns the page content our 3 products as List
        listProducts = pagedResult.getContent();
        model.addAttribute("currentPageNumber", pagedResult.getNumber()); //zerobased
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber()+1);
        model.addAttribute("nextPageNumber", pageno+1); //going forward to next page
        model.addAttribute("previousPageNumber", pageno-1); //going backwards to previous page
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());
        model.addAttribute("products", listProducts);//set the list with the 3 products
        return "productspage";

    }





}//end Controller class
