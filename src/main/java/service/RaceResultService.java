package service;

import client.Client;
import message.Message;

public class RaceResultService {
    private Client client;

    public void addSubscriber(Client client) {
        this.client = client;

    }

    public void send(Message message) {
        client.receive(message);
    }
}
