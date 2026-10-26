package org.howard.edu.lsp.midterm.crccards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maintains a collection of {@link Book} objects and coordinates borrowing
 * and returning. The Library enforces a limit of three simultaneously
 * borrowed books and delegates all book state changes to the Book itself.
 *
 * <p>Books are stored in a {@link HashMap} keyed by ISBN, giving constant-time
 * lookup and a natural way to reject duplicate ISBNs.</p>
 *
 * @author Your Name
 */
public class Library {
    /** Maximum number of books that may be borrowed at one time. */
    private static final int MAX_BORROWED = 3;

    private final Map<String, Book> books = new HashMap<>();

    /**
     * Adds a book to the library.
     *
     * @param book the book to add
     * @throws IllegalArgumentException if a book with the same ISBN already exists
     */
    public void addBook(Book book) {
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Duplicate ISBN: " + book.getIsbn());
        }
        books.put(book.getIsbn(), book);
    }

    /**
     * Finds a book by ISBN.
     *
     * @param isbn the ISBN to look up
     * @return the matching Book instance, or null if none exists
     */
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    /**
     * Borrows the book with the given ISBN.
     *
     * @param isbn the ISBN of the book to borrow
     * @throws IllegalArgumentException if no book with that ISBN is in the library
     * @throws IllegalStateException    if the book is already borrowed or three
     *                                  books are already borrowed
     */
    public void borrowBook(String isbn) {
        Book book = requireBook(isbn);
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        if (getBorrowedBookCount() >= MAX_BORROWED) {
            throw new IllegalStateException("Borrowing limit of " + MAX_BORROWED + " reached.");
        }
        book.borrowBook();
    }

    /**
     * Returns the book with the given ISBN to the library.
     *
     * @param isbn the ISBN of the book to return
     * @throws IllegalArgumentException if no book with that ISBN is in the library
     * @throws IllegalStateException    if the book is not currently borrowed
     */
    public void returnBook(String isbn) {
        requireBook(isbn).returnBook();
    }

    /**
     * Returns all books that are currently available.
     *
     * @return a new list of available books; empty if there are none
     */
    public List<Book> getAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books by the given author, including borrowed books.
     * The comparison is case-sensitive.
     *
     * @param author the exact author name to match
     * @return a new list of matching books; empty if there are none
     */
    public List<Book> getBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }

    /**
     * Returns all books sorted by ISBN in ascending lexicographical order,
     * regardless of borrowing status. The library is not modified.
     *
     * @return a new sorted list; empty if the library has no books
     */
    public List<Book> getBooksSortedByIsbn() {
        List<Book> result = new ArrayList<>(books.values());
        Collections.sort(result, (a, b) -> a.getIsbn().compareTo(b.getIsbn()));
        return result;
    }

    /**
     * Returns the number of books currently borrowed.
     *
     * @return the count of borrowed books
     */
    public int getBorrowedBookCount() {
        int count = 0;
        for (Book book : books.values()) {
            if (!book.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    private Book requireBook(String isbn) {
        Book book = books.get(isbn);
        if (book == null) {
            throw new IllegalArgumentException("No book with ISBN: " + isbn);
        }
        return book;
    }
}
