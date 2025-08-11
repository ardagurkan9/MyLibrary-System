/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ardagurkan
 */
public class Author {
    private int authorId;
    private String name;
    private String surname;
    private String website;

    public Author(int authorId, String name, String surname,String website) {
        this.authorId = authorId;
        this.name = name;
        this.surname = surname;
        this.website = website;
    }
    public int getAuthorId() { return authorId; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getWebsite() { return website; }
    
    
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
        this.website = "website-" + authorId; 
    }
    public Author(){
        
    }

    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setWebsite(String website) { this.website = website; }
}

