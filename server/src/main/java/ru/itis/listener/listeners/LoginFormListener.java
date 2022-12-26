package ru.itis.listener.listeners;

import ru.itis.listener.AbstractEventListener;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Account;
import ru.itis.serializers.AccountSerializer;
import ru.itis.serializers.MessageSerializer;
import ru.itis.serializers.RegisterFormSerializer;
import ru.itis.server.BoardConnection;
import ru.itis.services.AccountService;

public class LoginFormListener extends AbstractEventListener {

    private AccountService accountService;

    private AccountSerializer accountSerializer;

    public LoginFormListener() {
        super(MessageTypes.LOGIN_FORM);
        this.accountSerializer = new AccountSerializer();
    }

    @Override
    public void handle(BoardConnection connection, Message message) {
        this.accountService = context.getAccountService();

        Account accountRequest = accountSerializer.deserialize(message.getData());

        Message sendMessage = Message.builder().build();

        if (accountService.signIn(accountRequest)) {
            Account account = accountService.loadIfExist(accountRequest.getEmail());
            sendMessage.setType(MessageTypes.LOGIN_SUCCESS);
            sendMessage.setData(accountSerializer.serialize(account));
        } else {
            sendMessage.setType(MessageTypes.LOGIN_ERROR);
            sendMessage.setData(MessageSerializer.serialize("“акого email несуществует или неверный пароль"));
        }
        connection.sendMessage(sendMessage);
    }
}
