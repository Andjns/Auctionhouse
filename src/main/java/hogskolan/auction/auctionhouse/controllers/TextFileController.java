package hogskolan.auction.auctionhouse.controllers;

import hogskolan.auction.auctionhouse.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@Controller
public class TextFileController {

    @GetMapping("/textfile/saveuser")
    public String save(User user) {
        BufferedWriter writer;
        try {
            writer = Files.newBufferedWriter(Paths.get("users.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            writer.write(user.getName() + ";" + user.getEmail() + ";" + user.getPassword() + ";" + user.getRole());
            writer.newLine();
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return "redirect:/admin";
    }

    @PostMapping(path = "/textfile/saveuser")
    public String addUser(@ModelAttribute("user") User user, @RequestParam Map<String, String> allFormRequestParams) {
        this.save(user);
        return "redirect:/admin";
    }
}
