package ru.testea.impl;

import org.joda.time.LocalDate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.testea.api.Account;
import ru.testea.api.Client;
import ru.testea.api.IClientCRUDService;

import java.util.Collections;

public class Main
{
    public static void main(
        String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
            "classpath:META-INF/applicationContext.xml");

        IClientCRUDService clientCRUDService =
            context.getBean(IClientCRUDService.class);

        Client client = new Client();
        client.setFullName("Иванов Иван Иванович");
        client.setBirthday(LocalDate.parse("1980-01-01"));
        client.setAddress("г. Москва, ул. Ленина, 1");
        client.setAccounts(Collections.<Account>emptySet());

        clientCRUDService.create(client);

        context.close();
    }
}
