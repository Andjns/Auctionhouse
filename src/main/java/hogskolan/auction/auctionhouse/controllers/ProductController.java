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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
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
        for (Product product : productRepository.findAll()) {
            if (LocalDateTime.now().isAfter(product.getExpires()) && !product.getExpired()) {
                product.setExpired(true);
                List<Bid> bids = bidRepository.findAllByProduct(product);
                Collections.sort(bids, Collections.reverseOrder());
                Bid winningBid = bids.get(0);
                String subject = "You have won!";
                String body = "Auction with name: " + product.getName() + " have been won by you!\nAmount to pay is: " +
                        winningBid.getPrice();
                sendEmailService.sendWinnerEmail(product.getUser().getEmail(), subject, body);
                productRepository.save(product);
            }
        }

        if (pageno < 0 || pageno == null) {
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
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber() + 1);
        model.addAttribute("nextPageNumber", pageno + 1); //going forward to next page
        model.addAttribute("previousPageNumber", pageno - 1); //going backwards to previous page
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());


        //To show the top 3 bids, we go through each product we're showing
        //and reset their productlist, then we fetch the bids from the db into an
        //ArrayList, sort this list and add back the top x(decided by Math.max)
        // bids.
        for (Product product : listProducts) {
            product.setBids(new ArrayList<Bid>());
            List<Bid> bids = bidRepository.findAllByProduct(product);
            Collections.sort(bids, Collections.reverseOrder());
            for (Bid bid : bids.subList(0,Math.min(bids.size(), 3))) {
                product.addBid(bid);
            }
        }
        model.addAttribute("products", listProducts);//set the list with the 3 products
        model.addAttribute("categories", categoryRepository.findAll());
        return "productallview";

    }


    //Show all products med paging
    @GetMapping("/products/user/page/{pageno}")
    public String showPageForUser(@PathVariable() Integer pageno, Model model) {
        for (Product product : productRepository.findAll()) {
            if (LocalDateTime.now().isAfter(product.getExpires()) && !product.getExpired()) {
                product.setExpired(true);
                List<Bid> bids = bidRepository.findAllByProduct(product);
                Collections.sort(bids, Collections.reverseOrder());
                Bid winningBid = bids.get(0);
                String subject = "You have won!";
                String body = "Auction with name: " + product.getName() + " have been won by you!\nAmount to pay is: " +
                        winningBid.getPrice();
                sendEmailService.sendWinnerEmail(product.getUser().getEmail(), subject, body);
                productRepository.save(product);
            }
        }

        if (pageno < 0 || pageno == null) {
            pageno = 0;
        }
        final int PAGESIZE = 3; //number of product on each page
        //get the next page based on its pagenumber, zerobased
        //also set pagesize, the number of products on the page

        User user = userRepository.findByEmail(sec.loggedInUser());
        Pageable all = PageRequest.of(pageno, PAGESIZE);
        List<Product> allProductsByUser = user.getProducts();

        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Product> pagedResult = productRepository.findAllByUser(user, paging);
        List<Product> listProducts;
        //returns the page content our 3 products as List
        listProducts = pagedResult.getContent();
        model.addAttribute("currentPageNumber", pagedResult.getNumber()); //zerobased
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber() + 1);
        model.addAttribute("nextPageNumber", pageno + 1); //going forward to next page
        model.addAttribute("previousPageNumber", pageno - 1); //going backwards to previous page
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());

        for (Product product : listProducts) {
            product.setBids(new ArrayList<Bid>());
            List<Bid> bids = bidRepository.findAllByProduct(product);
            Collections.sort(bids, Collections.reverseOrder());
            for (Bid bid : bids.subList(0,Math.min(bids.size(), 3))) {
                product.addBid(bid);
            }
        }

        model.addAttribute("products", listProducts);//set the list with the 3 products
        model.addAttribute("categories", categoryRepository.findAll());
        return "productallview";

    }

    @GetMapping("/products/{category}/page/{pageno}")
    public String showPageForCategory(@PathVariable() Integer pageno, @PathVariable String category, Model model) {

        if (pageno < 0 || pageno == null) {
            pageno = 0;
        }
        final int PAGESIZE = 3; //number of product on each page
        //get the next page based on its pagenumber, zerobased
        //also set pagesize, the number of products on the page

        User user = userRepository.findByEmail(sec.loggedInUser());
        Pageable all = PageRequest.of(pageno, PAGESIZE);
        List<Product> allProductsByUser = user.getProducts();

        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Product> pagedResult = productRepository.findAllByCategory(categoryRepository.findByName(category), paging);
        List<Product> listProducts;
        //returns the page content our 3 products as List
        listProducts = pagedResult.getContent();
        model.addAttribute("currentPageNumber", pagedResult.getNumber()); //zerobased
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber() + 1);
        model.addAttribute("nextPageNumber", pageno + 1); //going forward to next page
        model.addAttribute("previousPageNumber", pageno - 1); //going backwards to previous page
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());

        for (Product product : listProducts) {
            product.setBids(new ArrayList<Bid>());
            List<Bid> bids = bidRepository.findAllByProduct(product);
            Collections.sort(bids, Collections.reverseOrder());
            for (Bid bid : bids.subList(0,Math.min(bids.size(), 3))) {
                product.addBid(bid);
            }
        }
        model.addAttribute("products", listProducts);//set the list with the 3 products
        model.addAttribute("categories", categoryRepository.findAll());
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

    @PostMapping("/products/add")
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

        if (user.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        } else {
            return "redirect:/products/page/0";
        }
    }

    //delete product
    @GetMapping("/products/delete/{p_id}")
    public String deleteProductById(@PathVariable Integer p_id) {
        productRepository.deleteById(p_id);
        return "redirect:/admin";
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
        return "redirect:/admin";
    }

    @GetMapping("/products/winner/{p_id}")
    public String seeWinner(@PathVariable Integer p_id, Model model) {
        Product product = productRepository.findById(p_id).get();
        if (product.getExpired()) {
            List<Bid> bids = bidRepository.findAllByProduct(product);
            Collections.sort(bids, Collections.reverseOrder());
            Bid winningBid = bids.get(0);
            User winner = winningBid.getUser();
            model.addAttribute("text", "This auction was won by user with email: " + winner.getEmail() +
                    "\nWinning bid: " + winningBid.getPrice());
        } else {
            model.addAttribute("text", "This auction is still ongoing.");
        }
        return "productwinnerview";
    }
}//end Controller class
