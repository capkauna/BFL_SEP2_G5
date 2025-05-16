package repository;

import model.BookEntity;

import java.util.List;

public interface BookDAO {
  void save(BookEntity book);
  List<BookEntity>findAll();
  BookEntity findById(int id);
}
