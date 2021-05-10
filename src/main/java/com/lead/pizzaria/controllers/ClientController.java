package com.lead.pizzaria.controllers;

import com.lead.pizzaria.entities.Client;
import com.lead.pizzaria.repositories.ClientRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {

    @Autowired
    public ClientRespository clientRespository;


    @GetMapping("/client")
    public ResponseEntity<List<Client>> getClients() {
        try{
            List<Client> clients = clientRespository.findAll();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable long id) {
        Optional<Client> clientData = clientRespository.findById(id);
        if(clientData.isPresent()) {
            return new ResponseEntity<>(clientData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getClient")
    public ResponseEntity<Client> getClientByName(@RequestParam String name) {
       Client clientData = clientRespository.findByName(name.toLowerCase());
       if (clientData != null) {
           return new ResponseEntity<>(clientData, HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

    }


    @PostMapping("/client")
    public ResponseEntity<Client> createClient(@RequestBody Client client_recebido) {
        try {
            Client client = clientRespository.save(new Client(client_recebido.getName(), client_recebido.getEmail(),
                    client_recebido.getTel()));
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<HttpStatus> deleteCliente(@PathVariable("id") long id) {
        try {
            clientRespository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/client/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client cliente) {
        Optional<Client> clienteData = clientRespository.findById(id);

        if (clienteData.isPresent()) {
            Client cliente_temp = clienteData.get();

            cliente_temp.setName(cliente.getName());
            cliente_temp.setTel(cliente.getTel());
            cliente_temp.setEmail(cliente.getEmail());

            return new ResponseEntity<>(clientRespository.save(cliente_temp), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
