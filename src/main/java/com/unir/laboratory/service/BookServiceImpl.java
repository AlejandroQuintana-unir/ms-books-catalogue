// package com.unir.laboratory.service;

// import java.sql.Date;
// import java.util.List;

// import static com.github.fge.jsonpatch.mergepatch.JsonMergePatch.fromJson;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.github.fge.jsonpatch.JsonPatchException;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.util.StringUtils;

// import com.unir.laboratory.entity.Book;
// import com.unir.laboratory.repository.BookRepository;

// @Service
// @Slf4j
// public class BookServiceImpl implements BookService {

//     @Autowired
//     BookRepository bookRepository;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Override
//     public List<Book> getBooks(String author, String title, Date publishDate, String isbnCode, String genre,
//             Integer valoration, Boolean visibility) {

//         if (StringUtils.hasText(author) || StringUtils.hasText(title) || publishDate != null
//                 || StringUtils.hasText(isbnCode) || StringUtils.hasText(genre) || valoration != null
//                 || visibility != null) {
//             return bookRepository.search(author, title, publishDate, isbnCode, genre, valoration, visibility);
//         }
//         List<Book> books = bookRepository.getBooks();
//         return books.isEmpty() ? null : books;
//     }

//     @Override
//     public Book getBook(String bookId) {
//         return bookRepository.getById(bookId);
//     }

//     @Override
//     public Book createBook(Book book) {
//         if (book.getAuthor() == null || book.getTitle() == null || book.getPublishDate() == null
//                 || book.getIsbnCode() == null || book.getGenre() == null || book.getValoration() == null
//                 || book.getVisibility() == null || book.getStock() == null || book.getPrice() == null
//                 || book.getPriceIva() == null || book.getPriceDigital() == null || book.getPriceDigitalIva() == null
//                 || book.getPublisher() == null || book.getEdition() == null || book.getDescription() == null) {
//             return null;
//         }
//         return bookRepository.save(book);
//     }

//     @Override
//     public Boolean removeBook(String bookId) {
//         Book book = bookRepository.getById(bookId);
//         if (book != null) {
//             bookRepository.delete(book);
//             return Boolean.TRUE;
//         } else {
//             return Boolean.FALSE;
//         }
//     }

//     @Override
//     public Book patchBook(String bookId, String book) {
//         Book bookToUpdate = bookRepository.getById(bookId);
//         if (bookToUpdate == null)
//             return null;

//         try {
//             var target = fromJson(objectMapper.readTree(book))
//                     .apply(objectMapper.readTree(objectMapper.writeValueAsString(bookToUpdate)));
//             return bookRepository.save(objectMapper.treeToValue(target, Book.class));
//         } catch (JsonProcessingException | JsonPatchException e) {
//             log.error("Error al actualizar el libro con id: {} - {}", bookId, e.getMessage());
//             return null;
//         }
//     }

//     @Override
//     public Book updateBook(String bookId, Book book) {
//         Book bookToUpdate = bookRepository.getById(bookId);
//         if (bookToUpdate != null) {
//             bookToUpdate.update(book);
//             bookRepository.save(bookToUpdate);
//             return bookToUpdate;
//         } else {
//             return null;
//         }
//     }

// }
