package org.example.userbookmanagement.backend.transformer;

import org.example.userbookmanagement.backend.bean.Client;
import org.example.userbookmanagement.backend.dto.ClientDto;
import org.springframework.stereotype.Component;

@Component
public class ClientTransformer extends AbstractTransformer<Client, ClientDto> {
    @Override
    public Client toEntity(ClientDto dto) {
        if (dto == null) {
            return null;
        } else {
            Client entity = new Client();
            entity.setId(dto.getId());
            entity.setFirstName(dto.getFirstName());
            entity.setLastName(dto.getLastName());
            entity.setEmail(dto.getEmail());
            entity.setPhoneNumber(dto.getPhoneNumber());
            return entity;
        }
    }

    @Override
    public ClientDto toDto(Client entity) {
        if (entity == null) {
            return null;
        } else {
            return new ClientDto(
                    entity.getId(),
                    entity.getFirstName(),
                    entity.getLastName(),
                    entity.getEmail(),
                    entity.getPhoneNumber()
            );
        }
    }
}
