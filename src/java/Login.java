/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * created by RonyPrajapati
 *
 *
 */
@ManagedBean()
@SessionScoped
public class Login implements Serializable {

    private String acc_id;
    private String password;
    private OnlineAccount theLoginAccount;

    public DataStorage data = new SQL_Database();

    public String getAcc_id() {
        return acc_id;
    }

    public void setAcc_id(String acc_id) {
        this.acc_id = acc_id;
    }

    public String getPassword() {
        return password;
    }

    public OnlineAccount getTheLoginAccount() {
        return theLoginAccount;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        String fileName = data.login(acc_id, password);

        String[] loginvalues = fileName.split(",");
        //the array should be like    =   fullname, acc_id , type ;

        if (loginvalues.length > 1) {

            theLoginAccount = new OnlineAccount(loginvalues[0], loginvalues[1], loginvalues[2], data);

            return "welcome";

        } else {
            return fileName;
        }

    }

}
