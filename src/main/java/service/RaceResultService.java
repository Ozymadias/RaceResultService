package service;

import clientA.Client;
import message.Message;

import java.util.*;

public class RaceResultService {
    private Collection<Client> clients = new HashSet<>();

    public void addSubscriber(Client client) {
        clients.add(client);
    }

    public void send(Message message) {
        for (Client client : clients)
            client.receive(message);
    }
}
