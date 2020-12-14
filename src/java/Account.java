
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Chintan
 */
public class Account {

    private String fullname;
    private String acc_id;
    private String type;
    private DataStorage data;

    public Account(String name, String id, String type, DataStorage d) {
        this.fullname = name;
        this.acc_id = id;
        this.type = type;
        this.data = d;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String createNewThread(String title, String content, String deal_url, String category, int price) {
        //load the Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todays_date = myDateObj.format(myFormatObj);

        String fileName = data.createNewThread(title, content, getFullname(), getAcc_id(), deal_url, category, 0, todays_date, false, price);
        return fileName;

    }

}
