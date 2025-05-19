package repository;

import model.BookEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO
{
  private final Connection connection;

  public BookDAOImpl(Connection connection){
    this.connection = connection;
  }

  public void save(BookEntity book)
  {
    String sql = "INSERT INTO books (title, publisher, ownerId, year) VALUES (?, ?, ?, ?)";
    try(PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setString(1, book.getTitle());
      stmt.setString(2, book.getPublisher());
      stmt.setInt(3, book.getOwnerId());
      stmt.setInt(4, book.getYear());
      stmt.executeUpdate();
      System.out.println("Book saved successfully");
    }
    catch (SQLException e)
    {
      System.out.println("Error saving book: " + e.getMessage());
    }
  }
  public List<BookEntity> findAll()
  {
    List<BookEntity> books = new ArrayList<>();
    String sql = "SELECT * FROM books";
    try(Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql))
    {
      while (rs.next()){
        BookEntity book = new BookEntity(
            rs.getInt("bookId"),
            rs.getString("title"),
            rs.getString("publisher"),
            rs.getInt("ownerId"),
            rs.getInt("year")
        );
        books.add(book);
      }

    }
    catch (SQLException e)
    {
      System.out.println("Error retrieving books: " + e.getMessage());
    }
return books;
  }
  public BookEntity findById(int id)
  {
    String sql = "SELECT * FROM books WHERE bookId = ?";
    try(PreparedStatement stmt = connection.prepareStatement(sql))
    {
      stmt.setInt(1, id);
      try(ResultSet rs = stmt.executeQuery();)
      {
      if (rs.next()){
        return new BookEntity(
            rs.getInt("bookId"),
            rs.getString("title"),
            rs.getString("publisher"),
            rs.getInt("ownerId"),
            rs.getInt("year")
        );
      }
    }
    catch (SQLException e)
    {
      System.out.println("Error retrieving book: " + e.getMessage());
    }
    return null;
  }
    catch (SQLException e)
    {
      throw new RuntimeException(e);
    }

  }}
