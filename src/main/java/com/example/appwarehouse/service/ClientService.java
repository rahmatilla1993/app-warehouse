package com.example.appwarehouse.service;

import com.example.appwarehouse.entity.Client;
import com.example.appwarehouse.repository.ClientRepository;
import com.example.appwarehouse.transfer.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClient(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        return optionalClient.orElse(null);
    }

    public Result addClient(Client client) {
        Client new_client = new Client();
        if (clientRepository.existsByNameAndPhoneNumber(client.getName(), client.getPhoneNumber())) {
            return new Result("Bunday telefon nomerli client bor", false);
        }
        if (clientRepository.existsByPhoneNumber(client.getPhoneNumber())) {
            return new Result("Bunday telefon nomer bor", false);
        }
        new_client.setPhoneNumber(client.getPhoneNumber());
        new_client.setName(client.getName());
        new_client.setActive(client.isActive());
        clientRepository.save(new_client);
        return new Result("Client qo'shildi", true);
    }

    public Result deleteClient(Integer id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            clientRepository.delete(optionalClient.get());
            return new Result("Client o'chirildi", true);
        }
        return new Result("Client topilmadi", false);
    }

    public Result editClient(Integer id, Client client) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()) {
            Client editClient = optionalClient.get();
            if (clientRepository.existsByIdIsNotAndNameAndPhoneNumber(id, client.getName(), client.getPhoneNumber())) {
                return new Result("Bunday telefon nomerli client bor", false);
            }
            if (clientRepository.existsByIdIsNotAndPhoneNumber(id, client.getPhoneNumber())) {
                return new Result("Bunday telefon nomer bor", false);
            }
            editClient.setActive(client.isActive());
            editClient.setName(client.getName());
            editClient.setPhoneNumber(client.getPhoneNumber());
            clientRepository.save(editClient);
            return new Result("Client o'zgartirildi", true);
        }
        return new Result("Client topilmadi", false);
    }
}
