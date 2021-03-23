package hogskolan.auction.auctionhouse.savestrategy;

import hogskolan.auction.auctionhouse.entity.User;

public class TextSave implements SaveStr {

    @Override
    public String save (User user) {

        return user.getEmail();
    }
}
