
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import org.apache.jasper.tagplugins.jstl.ForEach;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author asus
 */
public class OnlineAccount {

    private Account account;

    private DataStorage data;
    private Thread newThread;
    private Thread threadDetail;

    private String searchParam = "";
    private ArrayList<String> selectedCategory;
    private String filterString = "";

    public OnlineAccount(String name, String id, String type, DataStorage data) {
        this.data = data;

        if (type.equals("EDIT")) {
            account = new Editor(name, id, type, data);
        } else {
            account = new User(name, id, type, data);
        }

        newThread = new Thread(name, id);

    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Thread getNewThread() {
        return newThread;
    }

    public void setNewThread(Thread newThread) {
        this.newThread = newThread;
    }

    public Thread getThreadDetail() {
        return threadDetail;
    }

    public void setThreadDetail(Thread threadDetail) {
        this.threadDetail = threadDetail;
    }

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    public ArrayList<String> getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(ArrayList<String> selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String signOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index.xhtml";
    }

    public ArrayList<Thread> getBestDeals() {
        return data.getBestDeals();
    }

    public String filterThreads() {
        
        for (String s : selectedCategory) {
            filterString = filterString + "'" + s + "' ,";
        }
        filterString = filterString.substring(0, filterString.length() - 1);
        
        return "forum";
    }

    public ArrayList<Thread> showThreads() {

        return data.getAllThreads(this.searchParam,this.filterString);
    }

    public String resetFilter() {
        this.filterString = "";
        selectedCategory.clear();
        return "forum";
    }

    public ArrayList<Thread> getBestQualifiedThreads() {
        return data.getBestQualifiedThreads();
    }

    public String createNewThread() {
        return this.account.createNewThread(newThread.getTitle(), newThread.getContent(), newThread.getDeal_url(), newThread.getCategory(), newThread.getPrice());
    }

    public void resetThreadForm() {
        // not to reset author and createdby_id as it would be constant, till user logout.
        this.newThread.setThread_id("");
        this.newThread.setTitle("");
        this.newThread.setContent("");
        this.newThread.setDeal_url("");
        this.newThread.setCategory("");
        this.newThread.setThread_date(null);
        this.newThread.setRating(0);
        this.newThread.setShow_best("");
        this.newThread.setPrice(0);
    }

    public String viewThreadDetail(String thread_id) {
        this.threadDetail = data.getThreadDetail(thread_id);
        return "viewThreadDetail";
    }

    public String createNewComment() {

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String todays_date = myDateObj.format(myFormatObj);

        if (threadDetail.getComment().length() > 3) {
            data.createNewComment(threadDetail.getComment(), threadDetail.getThread_id(), account.getAcc_id(), todays_date);
            threadDetail.setComment("");
        }
        return "viewThreadDetail";
    }

    public ArrayList<Comment> getThreadsComments() {
        return data.getThreadsComments(threadDetail.getThread_id());
    }

    public String changeRating(int value) {
        int newRating = data.changeRating(account.getAcc_id(), threadDetail.getThread_id(), value);
        threadDetail.setRating(newRating);
        return "viewThreadDetail";
    }

    public String makeBestDeal(String thread_id) {
        int result = data.makeBestDeal(thread_id);
        if (result == 0) {
            return "makeBestDealError";
        }
        return "makeBestDeals";
    }

    public ArrayList<String> getCategories() {
        return data.getCategories();
    }

}
