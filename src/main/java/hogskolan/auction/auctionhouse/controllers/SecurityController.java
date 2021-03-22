package hogskolan.auction.auctionhouse.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class SecurityController {
    public String loggedInUser() {
        String currentUserName = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
             currentUserName = auth.getName();
        }
        return currentUserName;
    }
}
