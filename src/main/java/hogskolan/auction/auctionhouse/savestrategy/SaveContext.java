package hogskolan.auction.auctionhouse.savestrategy;

import hogskolan.auction.auctionhouse.entity.User;

public class SaveContext {
    private UserSaveStrategy userSaveStrategy;
    public SaveContext(UserSaveStrategy userSaveStrategy){
        this.userSaveStrategy = userSaveStrategy;
    }

    public UserSaveStrategy getUserSaveStrategy() {
        return this.userSaveStrategy;
    }

    public void setUserSaveStrategy(UserSaveStrategy userSaveStrategy) {
        this.userSaveStrategy = userSaveStrategy;
    }

    public void save (User user){
        userSaveStrategy.save(user);

    }
}//end class
