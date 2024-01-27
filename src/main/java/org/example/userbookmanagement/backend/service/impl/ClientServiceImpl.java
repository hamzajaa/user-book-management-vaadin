package org.example.userbookmanagement.backend.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.userbookmanagement.backend.bean.Client;
import org.example.userbookmanagement.backend.dao.ClientDao;
import org.example.userbookmanagement.backend.dto.ClientDto;
import org.example.userbookmanagement.backend.exception.ResourceNotFoundException;
import org.example.userbookmanagement.backend.service.facade.ClientService;
import org.example.userbookmanagement.backend.transformer.ClientTransformer;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;
    private ClientTransformer clientTransformer;



    @Override
    public List<ClientDto> findAll() {
        List<Client> categories = clientDao.findAll();
        return clientTransformer.toDto(categories);
    }

    @Override
    public ClientDto findById(Long id) {
        Client foundedClient = clientDao.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Client", "id", id)
        );
        return clientTransformer.toDto(foundedClient);
    }

    @Override
    public int deleteById(Long id) {
        findById(id);
        clientDao.deleteById(id);
        return 1;
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        Client client = clientTransformer.toEntity(clientDto);
        Client savedClient = clientDao.save(client);
        return clientTransformer.toDto(savedClient);
    }

    @Override
    public List<ClientDto> save(List<ClientDto> clientDtos) {
        if (clientDtos != null && !clientDtos.isEmpty()) {
            return clientDtos.stream().map(this::save).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        findById(clientDto.getId());
        Client client = clientTransformer.toEntity(clientDto);
        Client updatedClient = clientDao.save(client);
        return clientTransformer.toDto(updatedClient);
    }

    @Override
    public int delete(ClientDto clientDto) {
        ClientDto foundedClientDto = findById(clientDto.getId());
        Client client = clientTransformer.toEntity(foundedClientDto);
        clientDao.delete(client);
        return 1;
    }

    @Override
    public void delete(List<ClientDto> clientDtos) {
        if (clientDtos != null && !clientDtos.isEmpty()) {
            clientDtos.forEach(this::delete);
        }
    }

    @Override
    public void update(List<ClientDto> clientDtos) {
        if (clientDtos != null && !clientDtos.isEmpty()) {
            clientDtos.forEach(this::update);
        }
    }
}
