package service;

import clientA.Client;
import message.Message;

import java.util.*;

class RaceResultService {
    private Collection<Client> clients = new HashSet<>();

    void addSubscriber(Client client) {
        clients.add(client);
    }

    void send(Message message) {
        for (Client client : clients)
            client.receive(message);
    }

    void unsubscribe(Client clientA) {
        clients.remove(clientA);
    }
}
