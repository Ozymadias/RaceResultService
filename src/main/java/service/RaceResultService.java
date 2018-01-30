package service;

import clientA.Client;
import message.Message;

import java.util.*;
import java.util.logging.Logger;

class RaceResultService {
    private Collection<Client> clients = new HashSet<>();
    private Logger logger;

    RaceResultService(Logger logger) {
        this.logger = logger;
    }

    void subscribe(Client client) {
        clients.add(client);
    }

    void send(Message message) {
        for (Client client : clients)
            client.notify(message);
        logger.info(message.toString());
    }

    void unsubscribe(Client clientA) {
        clients.remove(clientA);
    }
}
