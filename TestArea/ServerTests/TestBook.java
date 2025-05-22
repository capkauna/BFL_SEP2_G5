package ServerTests;

import dto.enums.Format;
import dto.enums.Genre;
import Server.model.*;
import Server.model.status.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestBook
{
  private User owner, testBorrower;
  private Book testBook;
  private Status available, borrowed, unavailable;

  @BeforeEach
  public void setUp()
  {
    // set up any shared resources or configurations
    owner = new User("testOwner", "Jane Doe", "jane@doe.com", "password123", "1234567890", "123 Test St");
    testBorrower = new User("testBorrower", "John Doe", "john@doe.com",
        "password123", "0987654321", "456 Test St");
    testBook = new Book("Test Title", "Test Author", 52, Genre.FICTION, "1234567890123", Format.HARDCOVER, "Test Description", "test/path/to/image.jpg", owner);
     available = new Available();
   //  borrowed = new Borrowed(User u);
     unavailable = new Unavailable();
  }

  // Test the constructor
  @Test
  public void testBookConstructor()
  {
    Book newBook = new Book("Test Title2", "Test Author", 152,
        Genre.FICTION, "1234567890123", Format.HARDCOVER,
        "Test Description", "test/path/to/image.jpg", owner);
    assertEquals("Test Title2", newBook.getTitle());
  }
  @Test
  public void testNullParametersBookConstructor()
  {
    assertThrows(IllegalArgumentException.class,
        () -> new Book(null, null, null, null, null, null, null, null));
  }
  @Test
  public void testEmptyParametersBookConstructor()
  {
    assertThrows(IllegalArgumentException.class,
        () -> new Book("", "", null, null , "", null, "", "", null));
  }

  // Test the title validation
  @Test
  public void testValidTitle()
  {
    testBook.setTitle("Valid Title");
    assertEquals("Valid Title", testBook.getTitle());
  }
  @Test
  public void testNullTitle()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setTitle(null));
  }
  @Test
  public void testEmptyTitle()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setTitle(""));
  }
  // Test the author validation
  @Test
  public void testValidAuthor()
  {
    testBook.setAuthor("Valid Author");
    assertEquals("Valid Author", testBook.getAuthor());
  }
  @Test
  public void testNullAuthor()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setAuthor(null));
  }
  @Test
  public void testEmptyAuthor()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setAuthor(""));
  }
  // Test the genre validation
  @Test
  public void testValidGenre()
  {
    testBook.setGenre(Genre.FICTION);
    assertEquals(Genre.FICTION, testBook.getGenre());
  }
  @Test
  public void testNullGenre()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setGenre(null));
  }
  // Test the ISBN validation
  @Test
  public void testValidISBN()
  {
    testBook.setIsbn("1234567890123");
    assertEquals("1234567890123", testBook.getIsbn());
  }
  @Test
  public void testNullISBN()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setIsbn(null));
  }
  @Test
  public void testEmptyISBN()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setIsbn(""));
  }
  @Test
  public void testInvalidISBN()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setIsbn("123456789012")); // too short
  }
  // Test the format validation
  @Test
  public void testValidFormat()
  {
    testBook.setFormat(Format.HARDCOVER);
    assertEquals(Format.HARDCOVER, testBook.getFormat());
  }
  @Test
  public void testNullFormat()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setFormat(null));
  }
  // Test the description
  @Test
  public void testValidDescription()
  {
    testBook.setDescription("Valid Description");
    assertEquals("Valid Description", testBook.getDescription());
  }

  //Test the image path
  @Test
  public void testValidImagePath()
  {
    testBook.setImage("valid/path/to/image.jpg");
    assertEquals("valid/path/to/image.jpg", testBook.getImage());
  }

  // Test the owner
  @Test
  public void testValidOwner()
  {
    testBook.setOwner(owner);
    assertEquals(owner, testBook.getOwner());
  }
  @Test
  public void testNullOwner()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setOwner(null));
  }
  // Test the status
  @Test
  public void testValidStatus()
  {
    testBook.setStatus(available);
    assertEquals(available, testBook.getStatus());
  }
  @Test
  public void testNullStatus()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.setStatus(null));
  }
  // Test id
  @Test
  //make sure the id is auto incremented
  public void testGetBookId()
  {
    Book secondBook = new Book("Test Title2", "Test Author",
        11, Genre.FICTION, "1234567890123", Format.HARDCOVER,
        "Test Description", "test/path/to/image.jpg", owner);
    assertEquals(1, testBook.getBookId());
    assertEquals(2, secondBook.getBookId());
  }
  // Test the borrowedBy
  //TODO check methods that were commented out and consider alternatives
//  @Test
//  public void testValidBorrowedBy()
//  {
//    User borrower1 = new User("testBorrower", "John Doe", "john@doe.com",
//        "password123", "0987654321", "456 Test St");
//    testBook.setBorrowedBy(borrower1);
//    assertEquals(borrower1, testBook.getBorrowedBy());
//  }
  @Test
  //test owner cannot lend to themselves
  public void testOwnerLendTo()
  {
    assertThrows(IllegalArgumentException.class, () -> testBook.lendTo(owner));
  }

  //test state affected methods
//  @Test
//  public void testAvailableLendTo()
//  {
//    testBook.lendTo( testBorrower);
//    assertInstanceOf(Borrowed.class, testBook.getStatus());
//    assertEquals(testBorrower, testBook.getBorrowedBy());
//  }

  @Test
  public void testAvailableMarkAsReturned()
  {
    assertThrows(UnsupportedOperationException.class, () -> testBook.markAsReturned());
  }
  @Test
  public void testAvailableSetUnavailable()
  {
    testBook.setStatus(available);
    testBook.setUnavailable();
    assertInstanceOf(Unavailable.class, testBook.getStatus());
  }

  @Test
  public void testBorrowAlreadyBorrowed()
  {
    testBook.setStatus(borrowed);
    assertThrows(UnsupportedOperationException.class, () -> testBook.lendTo(testBorrower));
  }
//  @Test
//  public void testBorrowedMarkAsReturned()
//  {
//    testBook.setStatus(borrowed);
//    testBook.markAsReturned();
//    assertInstanceOf(Available.class, testBook.getStatus());
//    assertNull(testBook.getBorrowedBy());
//  }
  @Test
  public void testBorrowedSetUnavailable()
  {
    testBook.setStatus(borrowed);
    assertThrows(UnsupportedOperationException.class, () -> testBook.setUnavailable());
  }

  @Test
  public void testUnavailableLendTo()
  {
    testBook.setStatus(unavailable);
    assertThrows(UnsupportedOperationException.class, () -> testBook.lendTo(testBorrower));
  }
  @Test
  public void testUnavailableMarkAsReturned()
  {
    testBook.setStatus(unavailable);
    assertThrows(UnsupportedOperationException.class, () -> testBook.markAsReturned());
  }
  @Test
  public void testUnavailableSetUnavailable()
  {
    testBook.setStatus(unavailable);
    testBook.setUnavailable();
    assertInstanceOf(Available.class, testBook.getStatus());
  }
}
