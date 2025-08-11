/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ardagurkan
 */
public class User {
    private int userId;
    private String username;
    private String password;
    private int userType; // 1 veya 2

    public User(int userId, String username, String password, int userType) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }
    public User(){};
    
    public int getUserType() { return userType; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) {this.userId = userId;}
}
