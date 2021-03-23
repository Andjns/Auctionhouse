package hogskolan.auction.auctionhouse.controllers;


import hogskolan.auction.auctionhouse.entity.User;
import hogskolan.auction.auctionhouse.factory.FactorySaver;
import hogskolan.auction.auctionhouse.factory.SAVE_ENUM;
import hogskolan.auction.auctionhouse.savestrategy.SaveStr;
import hogskolan.auction.auctionhouse.savestrategy.SaveUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SaveUserController {

    @GetMapping("/adminview")
    public String txtSave(Model model) {



        SaveStr saveStategy = FactorySaver.getSaveStrategy(SAVE_ENUM.TEXT);
        SaveUser context = new SaveUser(saveStategy);

        return "adminview";


    }
}
