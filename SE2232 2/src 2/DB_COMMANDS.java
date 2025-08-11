/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ardagurkan
 */
public enum DB_COMMANDS {
    
    SQL_LOGIN("SELECT EXISTS(SELECT * FROM userinfo WHERE username=? AND password=?)"),
    SQL_GET_USER("SELECT * FROM userinfo WHERE username=? AND password=?"),
    ERROR_INVALID_LOGIN("Invalid username or password!");

    private final String command;

    DB_COMMANDS(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return this.command;
    }
}

