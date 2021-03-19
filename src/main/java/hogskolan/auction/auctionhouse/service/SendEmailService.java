package hogskolan.auction.auctionhouse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    JavaMailSender javaMailSender;

    //sendMail when add product
    public void sendEmail(String to, String body, String topic){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("h18jlagn@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
        System.out.println("sent email");
    }


    //Send mail for when add bid
    public void sendBidEmail(String to, String body, String topic, Integer price){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("h18jlagn@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        simpleMailMessage.setText(String.valueOf(price));
        javaMailSender.send(simpleMailMessage);
        System.out.println("sent email");
    }

    

}
