package com.psicoativa.service;

import com.psicoativa.dto.ClientDto;
import com.psicoativa.exception.DbOperationFailedException;
import com.psicoativa.exception.InvalidDataException;
import com.psicoativa.exception.ServiceFailedException;
import com.psicoativa.model.Client;
import com.psicoativa.repository.ClientRepository;
import com.psicoativa.util.ClientDtoPopulator;

import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private final ClientRepository cRepo;

    public ClientService(ClientRepository cRepo){
        this.cRepo = cRepo;
    }

    public void saveClient(Client client) throws ServiceFailedException{
        try {
            cRepo.addToDb(client);
        } catch (InvalidDataException | DbOperationFailedException e) {
            throw new ServiceFailedException("Service failure: "+ e.getMessage());
        }
    }

    public ClientDto getClientDto(int id){
        ClientDtoPopulator cDtoPopulator = new ClientDtoPopulator();
        Client client = cRepo.findById(id);
        if (client == null) throw new ServiceFailedException("Service failure: Couldn't find client of id: "+ id);
        return cDtoPopulator.populate(cRepo.findById(id));
    }

    public Client getClient(int id){
        Client client = cRepo.findById(id);
        if (client == null) throw new ServiceFailedException("Service failure: Couldn't find client of id: "+ id);
        return client;
    }

    public List<ClientDto> getClientDto(String name){
        ClientDtoPopulator cDtoPopulator = new ClientDtoPopulator();
        List<Client> clients = cRepo.findByName(name);
        if (clients.isEmpty()) throw new ServiceFailedException("couldn't find any results with name: " + name);
        List<ClientDto> cDtos = new ArrayList<ClientDto>();
        for (Client c : clients){
            cDtos.add(cDtoPopulator.populate(c));
        }
        return cDtos;
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
