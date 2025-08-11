


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
public class AddBookFrame extends javax.swing.JFrame {

   private JTextField txtTitle, txtYear, txtPages, txtCover, txtAbout, txtComments, txtReleaseDate;
    private JTextField txtAuthorName, txtAuthorSurname, txtAuthorWebsite,txtUserId;
    private JComboBox<String> comboReadStatus, comboRating;
    private JButton btnAdd;
    
    public AddBookFrame() {
        setTitle("ADD A BOOK");
         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(14, 2, 5, 5));
        txtAuthorName = new JTextField();
        txtAuthorSurname = new JTextField();
        txtAuthorWebsite = new JTextField();

        txtTitle = new JTextField();
        txtYear = new JTextField();
        txtPages = new JTextField();
        txtCover = new JTextField();
        txtAbout = new JTextField();
        txtComments = new JTextField();
        txtReleaseDate = new JTextField();
        txtUserId = new JTextField();

        comboReadStatus = new JComboBox<>(new String[]{"1", "2", "3"});
        comboRating = new JComboBox<>(new String[]{"0", "1", "2", "3", "4", "5"});

        btnAdd = new JButton("INSERT");

        // Bileşenleri ekle
        add(new JLabel("AUTHOR NAME:"));           add(txtAuthorName);
        add(new JLabel("AUTHOR SURNAME:"));        add(txtAuthorSurname);
        add(new JLabel("AUTHOR WEBSITE:"));    add(txtAuthorWebsite);

        add(new JLabel("BOOK NAME:"));           add(txtTitle);
        add(new JLabel("YEAR:"));                 add(txtYear);
        add(new JLabel("NUMBER OF PAGES:"));        add(txtPages);
        add(new JLabel("COVER:"));           add(txtCover);
        add(new JLabel("ABOUT:"));            add(txtAbout);
        add(new JLabel("COMMENTS:"));            add(txtComments);
        add(new JLabel("READ:"));        add(comboReadStatus);
        add(new JLabel("RATING:"));          add(comboRating);
        add(new JLabel("RELEASE DATE:")); add(txtReleaseDate);
        add(new JLabel("USER ID:"));               add(txtUserId);
        add(new JLabel(""));                     add(btnAdd);
        
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        setVisible(true);
    }
    private void addBook() {
    try {
        // Giriş kontrolleri
        String authorName = txtAuthorName.getText().trim();
        String authorSurname = txtAuthorSurname.getText().trim();
        String authorWebsite = txtAuthorWebsite.getText().trim();
        String title = txtTitle.getText().trim();
        String yearStr = txtYear.getText().trim();
        String pagesStr = txtPages.getText().trim();
        String userIdStr = txtUserId.getText().trim();

        if (authorName.isEmpty() || authorSurname.isEmpty() || title.isEmpty() || yearStr.isEmpty() || pagesStr.isEmpty()||userIdStr.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Lütfen zorunlu alanları doldurunuz!", "Eksik Bilgi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int year = Integer.parseInt(yearStr);
        int pages = Integer.parseInt(pagesStr);
        String cover = txtCover.getText().trim();
        String about = txtAbout.getText().trim();
        String comments = txtComments.getText().trim();
        int read = Integer.parseInt((String) comboReadStatus.getSelectedItem());
        int rating = Integer.parseInt((String) comboRating.getSelectedItem());
        int userId = Integer.parseInt(userIdStr);

        // Kitap nesnesini oluştur
        Book book = new Book();
        book.getAuthorId();
        book.setTitle(title);
        book.setYear(year);
        book.setNumberOfPages(pages);
        book.setCover(cover);
        book.setAbout(about);
        book.setComments(comments);
        book.setRead(read);
        book.setRating(rating);

        // Tarih kontrolü
        String dateStr = txtReleaseDate.getText().trim();
        if (!dateStr.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate localDate = LocalDate.parse(dateStr, formatter);
                java.util.Date utilDate = java.sql.Date.valueOf(localDate);
                book.setReleaseDate(utilDate);
            } catch (Exception dateEx) {
                JOptionPane.showMessageDialog(this, "Geçersiz tarih formatı! Örn: 15.06.2025", "Tarih Hatası", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        User user = new User();
        user.setUserId(userId);
        DB db = new DB();
        
        boolean success = db.addBook(book, authorName, authorSurname, authorWebsite,user);

        if (success) {
            JOptionPane.showMessageDialog(this, "Kitap başarıyla eklendi!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Kitap eklenemedi!", "Veritabanı Hatası", JOptionPane.ERROR_MESSAGE);
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Yıl ve sayfa sayısı gibi alanlar sadece sayı olmalıdır.", "Format Hatası", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
    }
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

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
