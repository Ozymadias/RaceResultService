package service;

import clientA.Client;
import message.Message;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

class RaceResultService {
    private Map<Category, HashSet<Client>> clients = new EnumMap<>(Category.class);
    private Logger logger;

    RaceResultService(Logger logger) {
        this.logger = logger;
    }

    void subscribeAll(Client client) {
        Arrays.stream(Category.values()).forEach(cat -> subscribe(client, cat));
    }

    void send(Message message) {
        for (Client client : clients.getOrDefault(message.getType(), new HashSet<>()))
            client.notify(message);
        logger.info(message.toString());
    }

    void unsubscribeForAll(Client client) {
        Arrays.stream(Category.values()).forEach(cat -> unsubscribe(client, cat));
    }

    void unsubscribe(Client client, Category category) {
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
