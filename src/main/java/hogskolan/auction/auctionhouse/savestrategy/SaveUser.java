package hogskolan.auction.auctionhouse.savestrategy;

import hogskolan.auction.auctionhouse.entity.User;

public class SaveUser {
    private SaveStr saveStrategy;

    public SaveUser(SaveStr saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public SaveStr getSaveStrategy() {
        return this.saveStrategy;
    }

    public void setSaveStrategy(SaveStr saveStrategy) {
        this.saveStrategy = saveStrategy;
    }

    public String saveStrategy(User user) {
        return this.saveStrategy.save(user);
    }
}
