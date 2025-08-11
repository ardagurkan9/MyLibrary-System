import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import java.sql.Statement;


public class DB {
    private static String SECRET_KEY = "L0ZRUIWjsQgPf74IEAHO5w==";
    private static final String URL = "jdbc:mysql://localhost:3306/MyLibrary";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    private User user; 

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public  boolean login(String name, String password) {
        String encryptedPassword = encryptText(password); 
        ResultSet rs;
        try (Connection connection = getConnection()) {
            
            PreparedStatement login = connection.prepareStatement(DB_COMMANDS.SQL_LOGIN.toString());
            PreparedStatement getUser = connection.prepareStatement(DB_COMMANDS.SQL_GET_USER.toString());

            login.setString(1, name);
            login.setString(2, encryptedPassword);
            getUser.setString(1, name);
            getUser.setString(2, encryptedPassword);

            rs = login.executeQuery();

            if (rs.next() && rs.getBoolean(1)) {
                rs = getUser.executeQuery();
                if (rs.next()) {
                    int userId = rs.getInt("userId");
                    String username = rs.getString("username");
                    String pwd = rs.getString("password");
                    int userType = rs.getInt("userType");

                    this.user = new User(userId, username, pwd, userType);
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
            System.err.println(DB_COMMANDS.ERROR_INVALID_LOGIN);
        }

        return false;
    }

    private String encryptText(String text) {
        return text;
    }

    public User getUser() {
        return user;
    }
    
    public boolean addBook(Book book, String authorName, String authorSurname, String authorWebsite, User user) {
    Connection conn = null;
    PreparedStatement checkStmt = null;
    PreparedStatement insertAuthorStmt = null;
    PreparedStatement insertBookStmt = null;
    ResultSet rs = null;

    try {
        conn = DB.getConnection();

        String checkSql = "SELECT authorId FROM authors WHERE name = ? AND surname = ?";
        checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, authorName);
        checkStmt.setString(2, authorSurname);
        rs = checkStmt.executeQuery();

        int authorId = -1;

        if (rs.next()) {
            authorId = rs.getInt("authorId");
        } else {
            String insertAuthorSql = "INSERT INTO authors (name, surname, website) VALUES (?, ?, ?)";
            insertAuthorStmt = conn.prepareStatement(insertAuthorSql, Statement.RETURN_GENERATED_KEYS);
            insertAuthorStmt.setString(1, authorName);
            insertAuthorStmt.setString(2, authorSurname);
            insertAuthorStmt.setString(3, authorWebsite);
            insertAuthorStmt.executeUpdate();

            rs = insertAuthorStmt.getGeneratedKeys();
            if (rs.next()) {
                authorId = rs.getInt(1);
            } else {
                return false;
            }
        }

        if (authorId <= 0) {
            return false;
        }

        String insertBookSql = "INSERT INTO books (authorId, title, year, numberOfPages, cover, about, `read`, rating, comments, releaseDate, userId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        insertBookStmt = conn.prepareStatement(insertBookSql);
        insertBookStmt.setInt(1, authorId);
        insertBookStmt.setString(2, book.getTitle());
        insertBookStmt.setInt(3, book.getYear());
        insertBookStmt.setInt(4, book.getNumberOfPages());
        insertBookStmt.setString(5, book.getCover());
        insertBookStmt.setString(6, book.getAbout());
        insertBookStmt.setInt(7, book.getRead());
        insertBookStmt.setInt(8, book.getRating());
        insertBookStmt.setString(9, book.getComments());

        if (book.getRead() == 3 && book.getReleaseDate() != null) {
            insertBookStmt.setDate(10, new java.sql.Date(book.getReleaseDate().getTime()));
        } else {
            insertBookStmt.setNull(10, java.sql.Types.DATE);
        }

        insertBookStmt.setInt(11, user.getUserId()); // userId eklendi

        int rows = insertBookStmt.executeUpdate();
        System.out.println("Kitap ekleme sonucu: " + rows);
        return rows > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    } finally {
        try {
            if (rs != null) rs.close();
            if (checkStmt != null) checkStmt.close();
            if (insertAuthorStmt != null) insertAuthorStmt.close();
            if (insertBookStmt != null) insertBookStmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

    public static boolean deleteBookById(int bookId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = DB.getConnection();

        
        String getAuthorIdSql = "SELECT authorId FROM books WHERE bookId = ?";
        ps = conn.prepareStatement(getAuthorIdSql);
        ps.setInt(1, bookId);
        rs = ps.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "Kitap bulunamadı.");
            return false;
        }

        int authorId = rs.getInt("authorId");

       
        String deleteBookSql = "DELETE FROM books WHERE bookId = ?";
        ps = conn.prepareStatement(deleteBookSql);
        ps.setInt(1, bookId);
        int deleted = ps.executeUpdate();

        if (deleted == 0) {
            JOptionPane.showMessageDialog(null, "Kitap silinemedi.");
            return false;
        }

        
        String checkOtherBooksSql = "SELECT COUNT(*) AS bookCount FROM books WHERE authorId = ?";
        ps = conn.prepareStatement(checkOtherBooksSql);
        ps.setInt(1, authorId);
        rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt("bookCount");

        if (count == 0) {
            
            String deleteAuthorSql = "DELETE FROM authors WHERE authorId = ?";
            ps = conn.prepareStatement(deleteAuthorSql);
            ps.setInt(1, authorId);
            ps.executeUpdate();
        }

        JOptionPane.showMessageDialog(null, "Book deleted succesfully.");
        return true;

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Hata: " + e.getMessage());
        return false;
    } 
}
    public static Book getBookById(int bookId) {
    Book book = null;
    try {
        Connection conn = getConnection();
        String sql = "SELECT * FROM books WHERE bookId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, bookId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int authorId = rs.getInt("authorId");
            String title = rs.getString("title");
            int year = rs.getInt("year");
            int numberOfPages = rs.getInt("numberOfPages");
            String cover = rs.getString("cover");
            String about = rs.getString("about");
            int read = rs.getInt("read");
            int rating = rs.getInt("rating");
            String comments = rs.getString("comments");
            int userId = rs.getInt("userID");

            java.sql.Date sqlDate = rs.getDate("releaseDate");
            Date releaseDate = (sqlDate != null) ? new Date(sqlDate.getTime()) : null;
            
            User user = new User();
            user.setUserId(userId);

            book = new Book(bookId, authorId, title, year, numberOfPages,
                            cover, about, read, rating, comments, releaseDate,user);
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return book;
}


   public static Author searchAuthorByName(String name, String surname) {
    Author author = null;
    String query = "SELECT * FROM authors WHERE name = ? AND surname = ?";

    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, name);
        stmt.setString(2, surname);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int authorId = rs.getInt("authorId");
            String website = rs.getString("website");
            author = new Author(authorId, name, surname, website);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return author;
}
   public static boolean updateBook(Book book) {
    String query = "UPDATE books SET title=?, year=?, numberOfPages=?, cover=?, about=?, `read`=?, rating=?, comments=?, releaseDate=?, userID=? WHERE bookId=?";

    try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setString(1, book.getTitle());
        ps.setInt(2, book.getYear());
        ps.setInt(3, book.getNumberOfPages());
        ps.setString(4, book.getCover());
        ps.setString(5, book.getAbout());
        ps.setInt(6, book.getRead());
        ps.setInt(7, book.getRating());
        ps.setString(8, book.getComments());

        if (book.getReleaseDate() != null) {
            ps.setDate(9, new java.sql.Date(book.getReleaseDate().getTime()));
        } else {
            ps.setNull(9, java.sql.Types.DATE);
        }

        if (book.getUserId() != null) {
            ps.setInt(10, book.getUserId().getUserId());
        } else {
            ps.setNull(10, java.sql.Types.INTEGER);
        }

        ps.setInt(11, book.getBookId());

        int updated = ps.executeUpdate();
        return updated > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

   
public static ArrayList<Book> getFavoriteBooks(int userId) {
    ArrayList<Book> favoriteBooks = new ArrayList<>();
    try {
        Connection conn = getConnection();
        String query = "SELECT * FROM books WHERE `read` = 1 AND rating >= 4 AND userID = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int bookId = rs.getInt("bookId");
            int authorId = rs.getInt("authorId");
            String title = rs.getString("title");
            int year = rs.getInt("year");
            int numberOfPages = rs.getInt("numberOfPages");
            String cover = rs.getString("cover");
            String about = rs.getString("about");
            int read = rs.getInt("read");
            int rating = rs.getInt("rating");
            String comments = rs.getString("comments");
            java.sql.Date sqlDate = rs.getDate("releaseDate");
            java.util.Date releaseDate = (sqlDate != null) ? new java.util.Date(sqlDate.getTime()) : null;

            // Kullanıcı nesnesi oluştur
            User user = new User();
            user.setUserId(userId);

            Book book = new Book(bookId, authorId, title, year, numberOfPages,
                                 cover, about, read, rating, comments, releaseDate, user);
            favoriteBooks.add(book);
        }

        rs.close();
        ps.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return favoriteBooks;
}


public static List<Author> getFavoriteAuthors(int userID) {
    List<Author> favorites = new ArrayList<>();
    String query = "SELECT a.authorId, a.name, a.surname, COUNT(b.bookId) AS bookCount " +
                   "FROM books b " +
                   "JOIN authors a ON b.authorId = a.authorId " +
                   "WHERE b.userID = ? " +
                   "GROUP BY a.authorId, a.name, a.surname " +
                   "HAVING COUNT(b.bookId) >= 3";

    try (Connection conn = DB.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, userID); // userId filtrelemesi

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Author author = new Author();
                author.setAuthorId(rs.getInt("authorId"));
                author.setName(rs.getString("name"));
                author.setSurname(rs.getString("surname"));
                favorites.add(author);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return favorites;
}



public static ArrayList<Book> getUnreadBooks(int userId) {
    ArrayList<Book> unreadBooks = new ArrayList<>();
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = getConnection();
        String query = "SELECT * FROM books WHERE `read` = 2 AND userId = ?";
        ps = conn.prepareStatement(query);
        ps.setInt(1, userId);
        rs = ps.executeQuery();

        while (rs.next()) {
            Book book = new Book();
            book.setBookId(rs.getInt("bookId"));
            book.setAuthorId(rs.getInt("authorId"));
            book.setTitle(rs.getString("title"));
            book.setYear(rs.getInt("year"));
            book.setNumberOfPages(rs.getInt("numberOfPages"));
            book.setCover(rs.getString("cover"));
            book.setAbout(rs.getString("about"));
            book.setRead(rs.getInt("read"));
            book.setRating(rs.getInt("rating"));
            book.setComments(rs.getString("comments"));
            book.setReleaseDate(rs.getDate("releaseDate"));

            User user = new User();
            user.setUserId(userId);
            book.setUserId(user);

            unreadBooks.add(book);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return unreadBooks;
}

public void notifyUpcomingWishlistBooks(int userId) {
    String query = "SELECT title, releaseDate FROM books WHERE `read` = 3 AND releaseDate IS NOT NULL AND releaseDate <= ? AND userId = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        ps.setDate(1, java.sql.Date.valueOf(nextWeek));
        ps.setInt(2, userId); 

        ResultSet rs = ps.executeQuery();
        StringBuilder sb = new StringBuilder();
        boolean found = false;

        while (rs.next()) {
            String title = rs.getString("title");
            Date releaseDate = rs.getDate("releaseDate");
            sb.append("• ").append(title).append(" (").append(releaseDate).append(")\n");
            found = true;
        }

        if (found) {
            JOptionPane.showMessageDialog(null, "Books releasing within a week from your wish list:\n\n" + sb.toString(), "Upcoming Books", JOptionPane.INFORMATION_MESSAGE);
        }

        rs.close(); 
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error while checking upcoming wishlist books.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

public String getCoverPathByBookId(int bookId) {
    String sql = "SELECT cover FROM books WHERE bookId = ?";
    try (Connection conn = getConnection(); 
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, bookId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("cover");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


}
