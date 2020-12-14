/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQL_Database implements DataStorage {

    final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/vanpariyac7089?useSSL=false";
    final String db_id = "vanpariyac7089";
    final String db_psw = "1908369";

    Connection connection = null;
    Statement st = null;
    ResultSet rs = null;

    //Ashwitha
    @Override
    public String signUpNewAccount(String fullname, String accountNum, String password, String type) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);
            int r = st.executeUpdate("insert into account values "
                    + "('" + accountNum + "', '" + password + "', '" + fullname + "', '" + type + "')");

            connection.commit();
            connection.setAutoCommit(true);

            return ("Your account is created with the Id : " + accountNum + ".");

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return ("Account creation failed. \nPlease retry with another account ID.");

        } finally {
            //close the database
            try {
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Rony
    @Override
    public String login(String id, String password) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            rs = st.executeQuery("Select * from account "
                    + "where acc_id = '" + id + "'");

            if (rs.next()) {
                //the id is found, check the password
                if (password.equals(rs.getString("password"))) {
                    //password is good
                    return rs.getString("fullname") + "," + rs.getString("acc_id") + "," + rs.getString("type") + "," + "welcome";
                    //go to the welcome page 
                } else {
                    //password is not correct
                    return "loginNotOK";

                }
            } else {
                return "loginNotOK";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError";
        } finally {
            //close the database
            try {
                connection.close();
                st.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //rony
    @Override
    public String createNewThread(String title, String content, String author, String createdby_id, String deal_url, String category, int rating, String thread_date, Boolean show_best, int price) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            rs = st.executeQuery("select count(*)as threadcount from thread;");

            int totalthreads = 101;
            String thread_id = "th#";
            if (rs.next()) {
                totalthreads = totalthreads + Integer.parseInt(rs.getString("threadcount"));
            }
            thread_id = thread_id + totalthreads;

            //insert a record into bankAccount Table
            int r1 = st.executeUpdate("insert into thread values ( '"
                    + thread_id + "', '" + title + "', '" + content + "', '" + author
                    + "', '" + createdby_id + "', '" + deal_url + "', '" + category + "', '"
                    + rating + "', '" + thread_date + "', '" + show_best + "', '" + price + "' )"
            );

            connection.commit();
            connection.setAutoCommit(true);

            if (r1 == 1) {
                //display msg
                return ("Thread created successfully !!");
            } else {
                return ("Thread creation failed !!");
            }

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return ("Thread creation failed !!");

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Ashwitha
    @Override
    public ArrayList<Thread> getAllThreads(String searchParam, String filterString) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            if (filterString.length() > 0) {
                rs = st.executeQuery("select * from thread where category in (" + filterString + ") "
                        + " and (title like '%" + searchParam + "%' or content like '%" + searchParam + "%' ) order by thread_date desc ;");
            } else {
                rs = st.executeQuery("select * from thread where title like '%" + searchParam + "%' or content like '%" + searchParam + "%' order by thread_date desc;");
            }

            ArrayList<Thread> threadarray = new ArrayList<Thread>();

            while (rs.next()) {
                try {
                    threadarray.add(
                            new Thread(rs.getString("thread_id"), rs.getString("title"),
                                    rs.getString("content"), rs.getString("author"), rs.getString("createdby_id"),
                                    rs.getString("deal_url"), rs.getString("category"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("thread_date")),
                                    rs.getInt("rating"), rs.getInt("price"), rs.getString("show_best")));
                } catch (ParseException ex) {
                    Logger.getLogger(SQL_Database.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            connection.commit();
            connection.setAutoCommit(true);

            return threadarray;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return null;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Ashwitha
    @Override
    public Thread getThreadDetail(String id) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            rs = st.executeQuery("select * from thread where thread_id = '" + id + "'");

            Thread threadDetail = new Thread();

            if (rs.next()) {
                try {
                    threadDetail = new Thread(rs.getString("thread_id"), rs.getString("title"),
                            rs.getString("content"), rs.getString("author"), rs.getString("createdby_id"),
                            rs.getString("deal_url"), rs.getString("category"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("thread_date")),
                            rs.getInt("rating"), rs.getInt("price"), rs.getString("show_best"));
                } catch (ParseException ex) {
                    Logger.getLogger(SQL_Database.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            connection.commit();
            connection.setAutoCommit(true);

            return threadDetail;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return null;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int changeRating(String acc_id, String thread_id, int value) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            ResultSet outerResultSet = st.executeQuery("select * from user_rating where acc_id = '" + acc_id + "' and thread_id = '" + thread_id + "'");

            if (outerResultSet.next()) {

                ResultSet innerResultSet = st.executeQuery("select * from user_rating "
                        + " where acc_id = '" + acc_id + "' and thread_id = '" + thread_id + "' and value_change = '" + value * -1 + "'");

                if (innerResultSet.next()) {

                    int r1 = st.executeUpdate("update thread "
                            + "set rating = rating + '" + value * 2 + "'"
                            + " where thread_id = '" + thread_id + "'");

                    int r2 = -1;

                    if (r1 == 1) {
                        r2 = st.executeUpdate("update user_rating "
                                + "set value_change =  '" + value + "'"
                                + " where thread_id = '" + thread_id + "' and acc_id = '" + acc_id + "' ");
                    }

                }
                rs = st.executeQuery("select rating from thread where thread_id = '" + thread_id + "'");
                innerResultSet.close();

            } else {

                int r1 = st.executeUpdate("update thread "
                        + "set rating = rating + '" + value + "'"
                        + " where thread_id = '" + thread_id + "'");

                int r2 = st.executeUpdate("insert into user_rating values ( '" + acc_id + "' ,'" + thread_id + "', '" + value + "') ");

                rs = st.executeQuery("select rating from thread where thread_id = '" + thread_id + "'");

            }

            outerResultSet.close();
            connection.commit();
            connection.setAutoCommit(true);

            int current_rating = 0;
            if (rs.next()) {
                return rs.getInt("rating");

            }

            return current_rating;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return 0;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Thread> getBestDeals() {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            rs = st.executeQuery("select * from thread where show_best = 'true' order by thread_date desc");

            ArrayList<Thread> threadarray = new ArrayList<Thread>();

            while (rs.next()) {
                try {
                    threadarray.add(
                            new Thread(rs.getString("thread_id"), rs.getString("title"),
                                    rs.getString("content"), rs.getString("author"), rs.getString("createdby_id"),
                                    rs.getString("deal_url"), rs.getString("category"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("thread_date")),
                                    rs.getInt("rating"), rs.getInt("price"), rs.getString("show_best")));
                } catch (ParseException ex) {
                    Logger.getLogger(SQL_Database.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            connection.commit();
            connection.setAutoCommit(true);

            return threadarray;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return null;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Thread> getBestQualifiedThreads() {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            rs = st.executeQuery("select * from thread "
                    + " where show_best = 'false' and rating > 1 "
                    + " order by thread_date desc;");

            ArrayList<Thread> threadarray = new ArrayList<Thread>();

            while (rs.next()) {
                try {
                    threadarray.add(
                            new Thread(rs.getString("thread_id"), rs.getString("title"),
                                    rs.getString("content"), rs.getString("author"), rs.getString("createdby_id"),
                                    rs.getString("deal_url"), rs.getString("category"), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("thread_date")),
                                    rs.getInt("rating"), rs.getInt("price"), rs.getString("show_best")));
                } catch (ParseException ex) {
                    Logger.getLogger(SQL_Database.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            connection.commit();
            connection.setAutoCommit(true);

            return threadarray;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return null;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int makeBestDeal(String thread_id) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            int r = st.executeUpdate("update thread"
                    + " set show_best= 'true' "
                    + "where thread_id = '" + thread_id + "'");

            connection.commit();
            connection.setAutoCommit(true);

            return r;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return 0;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //rony
    @Override
    public int createNewComment(String comment, String thread_id, String acc_id, String thread_date) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            rs = st.executeQuery("select count(*)as commentcount from comment;");

            int totalcomments = 101;
            String comment_id = "ct#";
            if (rs.next()) {
                totalcomments = totalcomments + Integer.parseInt(rs.getString("commentcount"));
            }
            comment_id = comment_id + totalcomments;

            //insert a record into bankAccount Table
            int r1 = st.executeUpdate("insert into comment values ( '" + comment_id + "', '" + comment + "', '" + thread_id + "', '" + acc_id + "', '" + thread_date + "' )"
            );

            connection.commit();
            connection.setAutoCommit(true);

            return r1;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return 0;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Ashwitha
    @Override
    public ArrayList<Comment> getThreadsComments(String thread_id) {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            rs = st.executeQuery("select * from comment where thread_id = '" + thread_id + "' order by comment_date desc;");

            ArrayList<Comment> commentsarray = new ArrayList<Comment>();

            while (rs.next()) {
                try {
                    commentsarray.add(
                            new Comment(rs.getString("comment_id"), rs.getString("comment"), rs.getString("thread_id"), rs.getString("acc_id"),
                                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("comment_date")))
                    );
                } catch (ParseException ex) {
                    Logger.getLogger(SQL_Database.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            connection.commit();
            connection.setAutoCommit(true);

            return commentsarray;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return null;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<String> getCategories() {
        try {

            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            st = connection.createStatement();
            connection.setAutoCommit(false);

            rs = st.executeQuery("select distinct category from thread order by category");

            ArrayList<String> categories = new ArrayList<String>();

            while (rs.next()) {
                categories.add(rs.getString("category"));
            }

            connection.commit();
            connection.setAutoCommit(true);

            return categories;

        } catch (SQLException e) {
            //handle the exceptions
            e.printStackTrace();
            return null;

        } finally {
            //close the database
            try {
                rs.close();
                st.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
