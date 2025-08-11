
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ardagurkan
 */
public class Book {
    private int bookId;
    private int authorId;
    private String title;
    private int year;
    private int numberOfPages;
    private String cover;
    private String about;
    private int read; 
    private int rating; 
    private String comments;
    private Date releaseDate;
    private User userId;

    public Book(int bookId, int authorId, String title, int year, int numberOfPages,String cover,String about,int read,int rating,String comments,Date releaseDate,User userId) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
        this.cover = cover;
        this.about = about;
        this.read = read;
        this.rating = rating;
        this.comments = comments;
        this.releaseDate = releaseDate;
        this.userId = userId;
    }
    public Book(){
        
    }
    public int getBookId() { return bookId; }
    public int getAuthorId() { return authorId; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public int getNumberOfPages() { return numberOfPages; }
    public String getCover() { return cover; }
    public String getAbout() { return about; }
    public int getRead() { return read; }
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public Date getReleaseDate() { return releaseDate; }
    public User getUserId() {return userId;}

    
     public void setBookId(int bookId) { this.bookId = bookId; }
     public void setAuthorId(int authorId) { this.authorId = authorId; }
     public void setTitle(String title) { this.title = title; }
     public void setYear(int year) { this.year = year; }
     public void setNumberOfPages(int numberOfPages) { this.numberOfPages = numberOfPages; }
     public void setCover(String cover) { this.cover = cover; }
     public void setAbout(String about) { this.about = about; }
     public void setRead(int read) { this.read = read; }
     public void setRating(int rating) { this.rating = rating; }
     public void setComments(String comments) { this.comments = comments; }
     public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; } 
     public void setUserId(User userId){this.userId = userId;}

}
