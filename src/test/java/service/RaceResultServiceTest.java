package service;

import clientA.Client;
import message.Message;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class RaceResultServiceTest {

    private RaceResultService raceResults;
    private Client clientA;
    private Client clientB;
    private Message message;
    private Logger logger;

    @BeforeMethod
    private void setUp() {
        logger = mock(Logger.class);
        raceResults = new RaceResultService(logger);
        clientA = mock(Client.class);
        clientB = mock(Client.class);
        message = mock(Message.class);
        when(message.getType()).thenReturn(Category.BOAT_RACES);
    }

    @Test
    public void notSubscribedClientShouldNotReceiveMessages() {
        raceResults.send(message);

        verify(clientA, never()).notify(message);
        verify(clientB, never()).notify(message);
    }

    @Test
    public void subscribedClientShouldReceivedMessage() {
        raceResults.subscribeForAll(clientA);
        raceResults.send(message);

        verify(clientA).notify(message);
    }

    @Test
    public void subscribedClientShouldBeSendToAllSubscribedClients() {
        raceResults.subscribeForAll(clientA);
        raceResults.subscribeForAll(clientB);
        raceResults.send(message);

        verify(clientA).notify(message);
        verify(clientB).notify(message);
    }

    @Test
    public void shouldSendOnlyOneMessageToClientsSubscribedMultipleTimes() {
        raceResults.subscribeForAll(clientA);
        raceResults.subscribeForAll(clientA);
        raceResults.send(message);

        verify(clientA).notify(message);
    }

    @Test
    public void unsubscribedClientShouldNotReceiveMessages() {
        raceResults.subscribeForAll(clientA);
        raceResults.unsubscribeForAll(clientA);
        raceResults.send(message);

        verify(clientA, never()).notify(message);
    }

    @Test
    public void whenNotSubscribeClientTriesToUnsubscribeNothingShouldHappen() {
        raceResults.unsubscribeForAll(clientA);
    }

    @Test
    public void whenTwoMessagesAreSendTwoMessagesShouldBeDelivered() {
        raceResults.subscribeForAll(clientA);

        raceResults.send(message);
        raceResults.send(message);

        verify(clientA, times(2)).notify(message);
    }

    @Test
    public void whenMessageIsSendShouldBeLogged() {
        raceResults.send(message);

        verify(logger).info(message.toString());
    }

    @Test
    public void whenMessageOfSomeCategoryIsSendShouldBeReceivedByThisWhoSubscribeForThatCategory() {
        Message horseRaceMessage = mock(Message.class);
        when(horseRaceMessage.getType()).thenReturn(Category.HOURS_RACES);
        raceResults.subscribe(clientA, Category.HOURS_RACES);

        raceResults.send(horseRaceMessage);

        verify(clientA).notify(horseRaceMessage);
    }

    @Test
    public void whenMessageOfSomeCategoryIsSendShouldNotBeReceivedByThisWhoAreNotSubscribedForThatCategory() {
        Message horseRaceMessage = mock(Message.class);
        when(horseRaceMessage.getType()).thenReturn(Category.HOURS_RACES);
        raceResults.subscribe(clientA, Category.HOURS_RACES);
        raceResults.subscribe(clientB, Category.F1_RACES);

        raceResults.send(horseRaceMessage);

        verify(clientB, never()).notify(horseRaceMessage);
    }

    @Test
    public void whenMessageOfSomeCategoryIsSendShouldNotBeReceivedByThisWhoSubscribedForAllCategories() {
        Message horseRaceMessage = mock(Message.class);
        when(horseRaceMessage.getType()).thenReturn(Category.HOURS_RACES);
        raceResults.subscribeForAll(clientA);

        raceResults.send(horseRaceMessage);

        verify(clientA).notify(horseRaceMessage);
    }

    @Test
    public void whenOneIsUnsubscribedForSomeCategoryShouldNotReceivedThatCategoryMessages() {
        Message horseRaceMessage = mock(Message.class);
        when(horseRaceMessage.getType()).thenReturn(Category.HOURS_RACES);
        raceResults.subscribeForAll(clientA);
        raceResults.unsubscribe(clientA, Category.HOURS_RACES);

        raceResults.send(horseRaceMessage);

        verify(clientA, never()).notify(horseRaceMessage);
    }

    @Test
    public void whenOneIsSubscribedForAllAndForSomeCategoryShouldReceivedOneThatCategoryMessage() {
        Message horseRaceMessage = mock(Message.class);
        when(horseRaceMessage.getType()).thenReturn(Category.HOURS_RACES);
        raceResults.subscribeForAll(clientA);
        raceResults.subscribe(clientA, Category.HOURS_RACES);

        raceResults.send(horseRaceMessage);

        verify(clientA).notify(horseRaceMessage);
    }
}
