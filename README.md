MyLibrary – Personal Book Management Application
Overview
MyLibrary is a desktop application that allows users to keep track of books they have read, not read yet, or wish to read. It provides an easy-to-use interface with different functionalities depending on the type of user.

The system uses a database to store information about users, books, and authors, ensuring data consistency and relational integrity.

Database Structure
The application stores data in at least three tables:

userinfo – Stores user accounts, including username, password, and user type.

books – Stores detailed information about all books in the system.

authors – Stores information about authors (only authors with at least one book in the library are listed).

Book Attributes
Each book record includes:

bookId – Unique integer ID

authorId – Linked to authors table

title – Book title

year – Year of publication

numberOfPages – Number of pages

cover – Path to the book cover image

about – Short description of the book

read – Reading status (1 = read, 2 = not read, 3 = wish to read)

rating – User rating (1–5, 0 if not read or wish to read)

comments – Optional user comments

releaseDate – Only set if read = 3 (wish to read)

Author Attributes
authorId – Unique integer ID

name – First name

surname – Last name

website – Website (auto-generated as "website-ID")

User Attributes
userId – Unique integer ID

username – Login name

password – Login password

userType – Determines available features (Type-1: full access, Type-2: limited access)

Key Features
Add a new book with all details, automatically managing author records.

Delete a book and remove the author if they have no other books in the library.

Display full details of a specific book by its ID.

Search and display information about an author by name.

Edit and update details of a selected book.

View a list of favorite books (read & rated 4 or 5).

View a list of favorite authors (at least 3 books in the library).

View a list of books not yet read.

Get notifications for wish list books releasing within one week.

Display the cover image of a selected book.

Login System
Users log in via a LoginFrame with username and password.

Based on user type:

Type-1: Access to all features.

Type-2: Access to a limited set of features.

Upon login, the main application frame adapts to the user’s role.
