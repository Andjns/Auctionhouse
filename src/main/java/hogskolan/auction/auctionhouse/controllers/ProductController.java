package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.Category;
import hogskolan.auction.auctionhouse.entity.Product;
import hogskolan.auction.auctionhouse.entity.User;
import hogskolan.auction.auctionhouse.repository.BidRepository;
import hogskolan.auction.auctionhouse.repository.CategoryRepository;
import hogskolan.auction.auctionhouse.repository.ProductRepository;
import hogskolan.auction.auctionhouse.repository.UserRepository;
import hogskolan.auction.auctionhouse.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @Autowired
    private SendEmailService sendEmailService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BidRepository bidRepository;

    //Show all products med paging
    @GetMapping("/products/page/{pageno}")
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
        model.addAttribute("pricedesc", bidRepository.findByOrderByPriceAsc());
        return "productallview";

    }

    //Show all products med paging
    @GetMapping("/products/user/{u_id}/page/{pageno}")
    public String showPageForUser(@PathVariable() Integer pageno, @PathVariable() Integer u_id, Model model) {

        if (pageno<0 || pageno == null) {
            pageno = 0;
        }
        final int PAGESIZE = 3; //number of product on each page
        //get the next page based on its pagenumber, zerobased
        //also set pagesize, the number of products on the page
        //Pageable all = PageRequest.of(pageno, PAGESIZE);
        //List<Product> allProductsByEmail = productRepository.findAllByEmail(sec.loggedInUser(), all);

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
        model.addAttribute("pricedesc", bidRepository.findByOrderByPriceAsc());
        return "productallview";

    }


    //add product
    @RequestMapping("/products/add")
    public String addProduct(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "productaddview";
    }

    @Autowired
    SecurityController sec = new SecurityController();

    @PostMapping("/product/add")
    public String addProductToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        Product product = new Product();
        product.setName(allFormRequestParams.get("name"));
        product.setDescription(allFormRequestParams.get("description"));
        product.setImg(allFormRequestParams.get("img"));
        product.setPrice(Integer.parseInt(allFormRequestParams.get("price")));
        product.setExpires(LocalDateTime.parse(allFormRequestParams.get("datetime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        product.setExpired(false);
        User user = userRepository.findByEmail(sec.loggedInUser());
        Category category = categoryRepository.findById(Integer.parseInt(allFormRequestParams.get("categoryId"))).get();
        product.setCategory(category);
        user.addProduct(product);
        System.out.println(user);
        userRepository.save(user);
        String title =
                "Your product with the name " +
                        product.getName() +
                        " has been put up to auction.";

        String body =
                "Your auction's details: \n" +
                        "Description: " + product.getDescription() +
                        "\nImage link: " + product.getImg() +
                        "\nStarting price: " + product.getPrice();
        sendEmailService.sendEmail(user.getEmail(), title, body);
        return "redirect:/admin";
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



}//end Controller class
