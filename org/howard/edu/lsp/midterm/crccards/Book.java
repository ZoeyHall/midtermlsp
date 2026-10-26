package org.howard.edu.lsp.midterm.crccards;

/**
 * Represents a single book in the Library Lending System.
 * A Book stores its identifying information and controls its own
 * availability; other classes can only change availability through
 * {@link #borrowBook()} and {@link #returnBook()}.
 *
 * @Zoey Hall
 */
public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private boolean available;

    /**
     * Creates a new book. New books start out available.
     *
     * @param isbn   the unique ISBN of the book
     * @param title  the title of the book
     * @param author the author of the book
     */
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    /**
     * Returns the ISBN of this book.
     *
     * @return the ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Returns the title of this book.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the author of this book.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Indicates whether this book can currently be borrowed.
     *
     * @return true if the book is available, false if it is borrowed
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Marks this book as borrowed.
     *
     * @throws IllegalStateException if the book is already borrowed
     */
    public void borrowBook() {
        if (!available) {
            throw new IllegalStateException("Book " + isbn + " is already borrowed.");
        }
        available = false;
    }

    /**
     * Marks this book as available again.
     *
     * @throws IllegalStateException if the book is not currently borrowed
     */
    public void returnBook() {
        if (available) {
            throw new IllegalStateException("Book " + isbn + " is not borrowed.");
        }
        available = true;
    }

    /**
     * Returns a string such as
     * {@code B101 Effective Java by Joshua Bloch [AVAILABLE]}.
     * Uses {@code [BORROWED]} when the book is unavailable.
     *
     * @return the formatted description of this book
     */
    @Override
    public String toString() {
        return isbn + " " + title + " by " + author
                + (available ? " [AVAILABLE]" : " [BORROWED]");
    }
}
