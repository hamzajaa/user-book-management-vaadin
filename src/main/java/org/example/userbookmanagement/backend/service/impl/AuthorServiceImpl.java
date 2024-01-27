package org.example.userbookmanagement.backend.service.impl;

import lombok.AllArgsConstructor;
import org.example.userbookmanagement.backend.bean.Author;
import org.example.userbookmanagement.backend.dao.AuthorDao;
import org.example.userbookmanagement.backend.dto.AuthorDto;
import org.example.userbookmanagement.backend.exception.ResourceNotFoundException;
import org.example.userbookmanagement.backend.service.facade.AuthorService;
import org.example.userbookmanagement.backend.transformer.AuthorTransformer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {


    private AuthorDao authorService;
    private AuthorTransformer authorTransformer;

    @Override
    public List<AuthorDto> findAll() {
        List<Author> categories = authorService.findAll();
        return authorTransformer.toDto(categories);
    }

    @Override
    public AuthorDto findById(Long id) {
        Author foundedAuthor = authorService.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Author", "id", id)
        );
        return authorTransformer.toDto(foundedAuthor);
    }

    @Override
    public int deleteById(Long id) {
        findById(id);
        authorService.deleteById(id);
        return 1;
    }

    @Override
    public AuthorDto save(AuthorDto userDto) {
        Author user = authorTransformer.toEntity(userDto);
        Author savedAuthor = authorService.save(user);
        return authorTransformer.toDto(savedAuthor);
    }

    @Override
    public List<AuthorDto> save(List<AuthorDto> userDtos) {
        if (userDtos != null && !userDtos.isEmpty()) {
            return userDtos.stream().map(this::save).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public AuthorDto update(AuthorDto userDto) {
        findById(userDto.id());
        Author user = authorTransformer.toEntity(userDto);
        Author updatedAuthor = authorService.save(user);
        return authorTransformer.toDto(updatedAuthor);
    }

    @Override
    public int delete(AuthorDto userDto) {
        AuthorDto foundedAuthorDto = findById(userDto.id());
        Author user = authorTransformer.toEntity(foundedAuthorDto);
        authorService.delete(user);
        return 1;
    }

    @Override
    public void delete(List<AuthorDto> userDtos) {
        if (userDtos != null && !userDtos.isEmpty()) {
            userDtos.forEach(this::delete);
        }
    }

    @Override
    public void update(List<AuthorDto> userDtos) {
        if (userDtos != null && !userDtos.isEmpty()) {
            userDtos.forEach(this::update);
        }
    }
}
