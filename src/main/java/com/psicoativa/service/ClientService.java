package com.psicoativa.service;

import com.psicoativa.dto.ClientDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Client;
import com.psicoativa.repository.ClientRepository;

public class ClientService {
    public void saveClient(Client client) throws ServiceFailedException{
        ClientRepository cRepo = new ClientRepository();
        try {
            cRepo.addToDb(client);
        } catch (InvalidDataException | DbOperationFailedException e) {
            throw new ServiceFailedException("Service failure: "+ e.getMessage());
        }
    }

    public Client getClient(int id){
        ClientRepository cRepo = new ClientRepository();
        try {
            return cRepo.findById(id);
        } catch (DbOperationFailedException e) {
            throw new ServiceFailedException("Service Failure: Couldn't find client of id: "+ id);
        }
    }
    
    public Client parseDto(ClientDto clientDto) throws InvalidDataException{
        Client client = new Client();
        client.setName(clientDto.getName());
        client.setCpf(clientDto.getCpf());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        return client;
    }
}
