
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author asus
 */
public class Thread {

    private String thread_id;
    private String title;
    private String content;
    private String author;
    private String createdby_id;
    private String deal_url;
    private String category;
    private Date thread_date;
    private int rating;
    private int price;
    private String show_best;
    
    private String comment;

    public Thread() {
    }

    public Thread(String author, String acc_id) {
        this.createdby_id = acc_id;
        this.author = author;
    }

    public Thread(String thread_id, String title, String content, String author, String createdby_id, String deal_url, String category, Date thread_date, int rating, int price, String show_best) {
        this.thread_id = thread_id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdby_id = createdby_id;
        this.deal_url = deal_url;
        this.category = category;
        this.thread_date = thread_date;
        this.rating = rating;
        this.price = price;
        this.show_best = show_best;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedby_id() {
        return createdby_id;
    }

    public void setCreatedby_id(String createdby_id) {
        this.createdby_id = createdby_id;
    }

    public String getDeal_url() {
        return deal_url;
    }

    public void setDeal_url(String deal_url) {
        this.deal_url = deal_url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getThread_date() {
        return thread_date;
    }

    public void setThread_date(Date thread_date) {
        this.thread_date = thread_date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getShow_best() {
        return show_best;
    }

    public void setShow_best(String show_best) {
        this.show_best = show_best;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
