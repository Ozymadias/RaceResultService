package service;

import clientA.Client;
import message.Message;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class RaceResultServiceTest {

    private RaceResultService raceResults;
    private Client clientA;
    private Client clientB;
    private Message message;

    @BeforeMethod
    private void setUp() {
        raceResults = new RaceResultService();
        clientA = mock(Client.class);
        clientB = mock(Client.class);
        message = mock(Message.class);
    }

    @Test
    public void notSubscribedClientShouldNotReceiveMessages() {
        raceResults.send(message);

        verify(clientA, never()).receive(message);
        verify(clientB, never()).receive(message);
    }

    @Test
    public void subscribedClientShouldReceivedMessage() {
        raceResults.addSubscriber(clientA);
        raceResults.send(message);

        verify(clientA).receive(message);
    }

    @Test
    public void subscribedClientShouldBeSendToAllSubscribedClients() {
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientB);
        raceResults.send(message);

        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }

    @Test
    public void shouldSendOnlyOneMessageToClientsSubscribedMultipleTimes() {
        raceResults.addSubscriber(clientA);
        raceResults.addSubscriber(clientA);
        raceResults.send(message);

        verify(clientA).receive(message);
    }
}
