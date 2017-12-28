package clientA;

import message.Message;

public interface Client {
    void receive(Message message);
}
