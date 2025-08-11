
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ardagurkan
 */
public class UpdateBookInfo extends javax.swing.JFrame {
   private JTextField txtBookId, txtTitle, txtYear, txtPages, txtCover, txtAbout, txtRead, txtRating, txtComments, txtReleaseDate,txtUserId;
    private JButton btnUpdate;


    public UpdateBookInfo() {
        initUİ();
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void initUİ() {
        setTitle("Update Book Information");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Close only this window
        setSize(400, 600);
        setLayout(new GridLayout(12, 2));

        txtBookId = new JTextField(); // Book ID - güncellenecek kitabın ID'si
        txtTitle = new JTextField();
        txtYear = new JTextField();
        txtPages = new JTextField();
        txtCover = new JTextField();
        txtAbout = new JTextField();
        txtRead = new JTextField(); // 1: Okundu, 2: Okunmadı, 3: Okunacak
        txtRating = new JTextField(); // 0-5
        txtComments = new JTextField();
        txtReleaseDate = new JTextField();
        txtUserId = new JTextField();// yyyy-mm-dd
        btnUpdate = new JButton("Update Book");

        add(new JLabel("Book ID:")); add(txtBookId);
        add(new JLabel("Title:")); add(txtTitle);
        add(new JLabel("Year:")); add(txtYear);
        add(new JLabel("Pages:")); add(txtPages);
        add(new JLabel("Cover Path:")); add(txtCover);
        add(new JLabel("About:")); add(txtAbout);
        add(new JLabel("Read (1/2/3):")); add(txtRead);
        add(new JLabel("Rating (0-5):")); add(txtRating);
        add(new JLabel("Comments:")); add(txtComments);
        add(new JLabel("Release Date (yyyy-mm-dd):")); add(txtReleaseDate);
        add(new JLabel("User ID:")); add(txtUserId);
        add(btnUpdate);

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateBook();
            }
        });

        setLocationRelativeTo(null); 
        setVisible(true);
    }
    private void updateBook() {
        try {
            Book book = new Book();
            book.setBookId(Integer.parseInt(txtBookId.getText()));
            book.setTitle(txtTitle.getText());
            book.setYear(Integer.parseInt(txtYear.getText()));
            book.setNumberOfPages(Integer.parseInt(txtPages.getText()));
            book.setCover(txtCover.getText());
            book.setAbout(txtAbout.getText());
            book.setRead(Integer.parseInt(txtRead.getText()));
            book.setRating(Integer.parseInt(txtRating.getText()));
            book.setComments(txtComments.getText());

            String releaseDateStr = txtReleaseDate.getText().trim();
            if (!releaseDateStr.isEmpty()) {
                book.setReleaseDate(Date.valueOf(releaseDateStr));
            } else {
                book.setReleaseDate(null);
            }
            String userIdStr = txtUserId.getText().trim();
            if (!userIdStr.isEmpty()) {
                User user = new User();
                user.setUserId(Integer.parseInt(userIdStr));
                book.setUserId(user); // ✅ userID set
            } else {
                book.setUserId(null);
            }

            boolean success = DB.updateBook(book);
            if (success) {
                JOptionPane.showMessageDialog(this, "Book updated successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update book.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

