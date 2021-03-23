package hogskolan.auction.auctionhouse.savestrategy;

import hogskolan.auction.auctionhouse.entity.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TextSaveStrategy implements UserSaveStrategy {

    @Override
    public void save (User user) {

        BufferedWriter writer;
        try {
            writer = Files.newBufferedWriter(Paths.get("users.txt"), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            writer.write(user.getName() + ";" + user.getEmail() + ";" + user.getPassword() + ";" + user.getRole());
            writer.newLine();
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
