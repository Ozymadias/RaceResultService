package service;

import client.Client;
import message.Message;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RaceResultServiceTest {

    @Test
    public void subscribedClientShouldReceivedMessage() {
        RaceResultService raceResults = new RaceResultService();
        Client client = mock(Client.class);
        Message message = mock(Message.class);

        raceResults.addSubscriber(client);
        raceResults.send(message);

        verify(client).receive(message);
    }
}
