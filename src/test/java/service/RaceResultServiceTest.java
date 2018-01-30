package service;

import clientA.Client;
import message.Message;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

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
        raceResults.subscribe(clientA);
        raceResults.send(message);

        verify(clientA).receive(message);
    }

    @Test
    public void subscribedClientShouldBeSendToAllSubscribedClients() {
        raceResults.subscribe(clientA);
        raceResults.subscribe(clientB);
        raceResults.send(message);

        verify(clientA).receive(message);
        verify(clientB).receive(message);
    }

    @Test
    public void shouldSendOnlyOneMessageToClientsSubscribedMultipleTimes() {
        raceResults.subscribe(clientA);
        raceResults.subscribe(clientA);
        raceResults.send(message);

        verify(clientA).receive(message);
    }

    @Test
    public void unsubscribedClientShouldNotReceiveMessages() {
        raceResults.subscribe(clientA);
        raceResults.unsubscribe(clientA);
        raceResults.send(message);

        verify(clientA, never()).receive(message);
    }

    @Test
    public void whenNotSubscribeClientTriesToUnsubscribeNothingShouldHappen() {
        raceResults.unsubscribe(clientA);
    }

    @Test
    public void whenTwoMessagesAreSendTwoMessagesShouldBeDelivered() {
        raceResults.subscribe(clientA);

        raceResults.send(message);
        raceResults.send(message);

        verify(clientA, times(2)).receive(message);
    }
}
