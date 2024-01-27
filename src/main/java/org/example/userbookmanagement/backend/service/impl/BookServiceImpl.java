package org.example.userbookmanagement.backend.service.impl;

import lombok.AllArgsConstructor;
import org.example.userbookmanagement.backend.bean.Author;
import org.example.userbookmanagement.backend.bean.Book;
import org.example.userbookmanagement.backend.bean.Category;
import org.example.userbookmanagement.backend.dao.BookDao;
import org.example.userbookmanagement.backend.dto.AuthorDto;
import org.example.userbookmanagement.backend.dto.BookDto;
import org.example.userbookmanagement.backend.dto.CategoryDto;
import org.example.userbookmanagement.backend.exception.ResourceNotFoundException;
import org.example.userbookmanagement.backend.service.facade.AuthorService;
import org.example.userbookmanagement.backend.service.facade.BookService;
import org.example.userbookmanagement.backend.service.facade.CategoryService;
import org.example.userbookmanagement.backend.transformer.AuthorTransformer;
import org.example.userbookmanagement.backend.transformer.BookTransformer;
import org.example.userbookmanagement.backend.transformer.CategoryTransformer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {


    private BookDao bookDao;
    private BookTransformer bookTransformer;
    private AuthorService authorService;
    private CategoryService categoryService;
    private AuthorTransformer authorTransformer;
    private CategoryTransformer categoryTransformer;

    @Override
    public List<BookDto> findAll() {
        List<Book> categories = bookDao.findAll();
        return bookTransformer.toDto(categories);
    }

    @Override
    public BookDto findById(Long id) {
        Book foundedBook = bookDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Book", "id", id)
        );
        return bookTransformer.toDto(foundedBook);
    }

    @Override
    public int deleteById(Long id) {
        findById(id);
        bookDao.deleteById(id);
        return 1;
    }

    @Override
    public BookDto save(BookDto bookDto) {
        Book book = bookTransformer.toEntity(bookDto);
        prepareSave(book);
        Book savedBook = bookDao.save(book);
        return bookTransformer.toDto(savedBook);
    }

    private void prepareSave(Book book) {
        findAuthor(book);
        findCategory(book);
    }

    private void findCategory(Book book) {
        CategoryDto foundedCategoryDto = categoryService.findById(book.getCategory().getId());
        if (foundedCategoryDto != null) {
            Category category = categoryTransformer.toEntity(foundedCategoryDto);
            book.setCategory(category);
        }
    }

    private void findAuthor(Book book) {
        AuthorDto foundedAuthorDto = authorService.findById(book.getAuthor().getId());
        if (foundedAuthorDto != null) {
            Author author = authorTransformer.toEntity(foundedAuthorDto);
            book.setAuthor(author);
        }
    }

    @Override
    public List<BookDto> save(List<BookDto> bookDtos) {
        if (bookDtos != null && !bookDtos.isEmpty()) {
            return bookDtos.stream().map(this::save).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public BookDto update(BookDto bookDto) {
        findById(bookDto.id());
        Book book = bookTransformer.toEntity(bookDto);
        Book updatedBook = bookDao.save(book);
        return bookTransformer.toDto(updatedBook);
    }

    @Override
    public int delete(BookDto bookDto) {
        BookDto foundedBookDto = findById(bookDto.id());
        Book book = bookTransformer.toEntity(foundedBookDto);
        bookDao.delete(book);
        return 1;
    }

    @Override
    public void delete(List<BookDto> bookDtos) {
        if (bookDtos != null && !bookDtos.isEmpty()) {
            bookDtos.forEach(this::delete);
        }
    }

    @Override
    public void update(List<BookDto> bookDtos) {
        if (bookDtos != null && !bookDtos.isEmpty()) {
            bookDtos.forEach(this::update);
        }
    }
}
