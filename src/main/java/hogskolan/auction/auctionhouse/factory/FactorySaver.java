package hogskolan.auction.auctionhouse.factory;

import hogskolan.auction.auctionhouse.savestrategy.SaveStr;
import hogskolan.auction.auctionhouse.savestrategy.TextSave;

public class FactorySaver {
    public static SaveStr getSaveStrategy(SAVE_ENUM savetype) {
        SaveStr saveStrategy = null;
        switch (savetype) {
            case TEXT:
                saveStrategy = new TextSave();
                break;
        }
        return saveStrategy;
    }
}
