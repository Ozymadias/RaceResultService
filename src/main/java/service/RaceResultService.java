package service;

import clientA.Client;
import message.Message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

class RaceResultService {
    private Map<Category, HashSet<Client>> clients = new HashMap<>();
    private Logger logger;

    RaceResultService(Logger logger) {
        this.logger = logger;
    }

    void subscribe(Client client) {
        subscribe(client, Category.ALL);
    }

    void send(Message message) {
        for (Client client : clients.getOrDefault(message.getType(), new HashSet<>()))
            client.notify(message);
        logger.info(message.toString());
    }

    void unsubscribe(Client client) {
        Category category = Category.ALL;
        HashSet<Client> clientsOfCategory = clients.getOrDefault(category, new HashSet<>());
        clientsOfCategory.remove(client);
        clients.put(category, clientsOfCategory);
    }

    void subscribe(Client client, Category category) {
        HashSet<Client> clientsOfCategory = clients.getOrDefault(category, new HashSet<>());
        clientsOfCategory.add(client);
        clients.put(category, clientsOfCategory);
    }
}
