/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author chintan vanpariya
 */
public interface DataStorage {

    String signUpNewAccount(String fullname, String accountNum, String password, String type);
    String login(String id, String password);

    String createNewThread(String title, String content, String author, String createdby_id, String deal_url, String category, int rating, String thread_date, Boolean show_best, int price);
    ArrayList<Thread> getAllThreads(String searchParam, String filterString);
    
    Thread getThreadDetail(String id) ;
    int changeRating(String acc_id, String thread_id, int value);
    ArrayList<Thread> getBestDeals();
    ArrayList<Thread> getBestQualifiedThreads();
    
    int makeBestDeal(String thread_id);
    
    int createNewComment(String comment, String acc_id, String thread_id, String thread_date);
    ArrayList<Comment> getThreadsComments(String thread_id) ;
    ArrayList<String> getCategories();
}
