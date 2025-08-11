USE MyProject;
CREATE TABLE userinfo (
    userId INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    userType INT NOT NULL
);
INSERT INTO userinfo (username, password, userType)
VALUES ('admin', 'admin123', 1),('student', 'student123', 2);    

CREATE TABLE authors (
    authorId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    website VARCHAR(100) NOT NULL
);


CREATE TABLE books (
    bookId INT PRIMARY KEY AUTO_INCREMENT,
    authorId INT,
    title VARCHAR(100) NOT NULL,
    year INT,
    numberOfPages INT,
    cover VARCHAR(255),
    about TEXT,
    `read` INT,
    rating INT,
    comments TEXT,
    releaseDate DATE,
    userId INT,
    FOREIGN KEY (authorId) REFERENCES authors(authorId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES userinfo(userId) ON DELETE CASCADE
);




SELECT * FROM books; 
SELECT* FROM authors;



