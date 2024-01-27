package org.example.userbookmanagement.backend.ws;

import org.example.userbookmanagement.backend.dto.BookDto;
import org.example.userbookmanagement.backend.service.facade.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<List<BookDto>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BookDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Integer> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.deleteById(id));
    }

    @PostMapping("/")
    public ResponseEntity<BookDto> save(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.save(bookDto), HttpStatus.CREATED);
    }

    @PostMapping("/list/")
    public ResponseEntity<List<BookDto>> save(@RequestBody List<BookDto> bookDtos) {
        return new ResponseEntity<>(bookService.save(bookDtos), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<BookDto> update(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.update(bookDto));
    }

    @DeleteMapping("/")
    public ResponseEntity<Integer> delete(@RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.delete(bookDto));
    }

    @DeleteMapping("/list/")
    public void delete(@RequestBody List<BookDto> bookDtos) {
        bookService.delete(bookDtos);
    }

    @PutMapping("/list/")
    public void update(@RequestBody List<BookDto> list) {
        bookService.update(list);
    }
}
