# Bookstore Spring Boot Application

In order to run the project use the *docker-compose.yaml* file.


## Bookstore API Introduction

Welcome to the bookstore API documentation. This will describe the bookstore api which is used as a REST application to try/clarify various concepts.

## Data Resources

The following is a section of resources related to REST level 2 resources. These resources are only available in the application/json format.

## URI

###### GET
/books?page[number]&page[size] 

Retrieve all available books with/without pagination.

###### GET
/books/isbn

Retrieve a specific book with an exact match when you know the ISBN number.

###### GET
/books/isbn/buy

Retrieve a updated book with the quantity reduced by 1 when the ISBN number is known.

###### GET
/books?filter[author]=author_name&filter[title]=title_name&page[number]&page[size]

Retrieve all the books by a specific author or title name with partial/full match.it also has pagination enabled.

###### POST
/books

Retrieve a stored book by passing the book details(isbn,title,quntity,author) which are mandatory. if book already there it returns the updated book.

###### GET
/books/isbn/posts

Retrieve the post's title of given book's isbn via calling the web media service.
