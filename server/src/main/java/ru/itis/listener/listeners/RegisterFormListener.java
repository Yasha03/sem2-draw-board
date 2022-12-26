package ru.itis.listener.listeners;

import ru.itis.listener.AbstractEventListener;
import ru.itis.message.Message;
import ru.itis.message.MessageTypes;
import ru.itis.models.Account;
import ru.itis.models.RegisterForm;
import ru.itis.repositories.AccountRepository;
import ru.itis.repositories.AccountRepositoryImpl;
import ru.itis.serializers.AccountSerializer;
import ru.itis.serializers.MessageSerializer;
import ru.itis.serializers.RegisterFormSerializer;
import ru.itis.server.BoardConnection;
import ru.itis.server.ServerContext;
import ru.itis.services.AccountService;

public class RegisterFormListener extends AbstractEventListener {

    private RegisterFormSerializer formSerializer;

    private AccountSerializer accountSerializer;

    private AccountService accountService;

    public RegisterFormListener() {
        super(MessageTypes.REGISTER_FORM);
        this.formSerializer = new RegisterFormSerializer();
        this.accountSerializer = new AccountSerializer();
    }

    @Override
    public void handle(BoardConnection connection, Message message) {
        this.accountService = context.getAccountService();
        RegisterForm form = formSerializer.deserialize(message.getData());

        Account account = form.collectAccount();

        Message sendMessage = Message.builder().build();

        if (account.getLogin() != null && account.getEmail() != null && account.getPassword() != null) {
            if (accountService.loadIfExist(account.getEmail()) == null) {
                String errorStatus = accountService.isCorrectAccount(account);
                if (errorStatus == null) {
                    accountService.signUp(account);
                    Account accountSend = accountService.loadIfExist(account.getEmail());
                    sendMessage.setType(MessageTypes.REGISTER_SUCCESS);
                    sendMessage.setData(accountSerializer.serialize(accountSend));
                } else {
                    sendMessage.setType(MessageTypes.REGISTER_ERROR);
                    sendMessage.setData(MessageSerializer.serialize(errorStatus));
                }
            }
        } else {
            sendMessage.setType(MessageTypes.REGISTER_ERROR);
            sendMessage.setData(MessageSerializer.serialize("Заполните данные"));
        }

        connection.sendMessage(sendMessage);

    }
}
