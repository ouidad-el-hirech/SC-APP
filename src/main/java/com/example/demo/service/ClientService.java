package com.example.demo.service;

import com.example.demo.entity.Client;
import com.example.demo.repository.AvisRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.RendezvousRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RendezvousRepository rendezvousRepository;
    @Autowired
    private AvisRepository avisRepository;
    

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client avec ID " + id + " non trouvé"));
    }
   

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Client updatedClient) {
        if (updatedClient.getIdClient() == null) {
            throw new IllegalArgumentException("L'ID du client est obligatoire pour la mise à jour.");
        }

        Client existingClient = clientRepository.findById(updatedClient.getIdClient())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Client avec ID " + updatedClient.getIdClient() + " non trouvé"
                ));

        if (updatedClient.getNom() != null) existingClient.setNom(updatedClient.getNom());
        if (updatedClient.getPrenom() != null) existingClient.setPrenom(updatedClient.getPrenom());
        if (updatedClient.getEmail() != null) existingClient.setEmail(updatedClient.getEmail());
        if (updatedClient.getTelephone() != null) existingClient.setTelephone(updatedClient.getTelephone());

        return clientRepository.save(existingClient);
    }

    public void deleteClient(Long id) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client avec ID " + id + " non trouvé"));
        rendezvousRepository.deleteByClient_IdClient(id);
        avisRepository.deleteByClient_IdClient(id);
        clientRepository.delete(existingClient);
      
    }
}
