package ru.testea.impl;

import com.google.common.collect.Sets;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.testea.api.Account;
import ru.testea.api.Client;
import ru.testea.api.Constants;
import ru.testea.api.IClientCRUDService;

import java.math.BigDecimal;

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

        Account a1 = new Account();
        a1.setName("a1");
        a1.setAmount(Money.of(Constants.CURRENCY, BigDecimal.TEN));
        a1.setCreationTime(DateTime.now());
        a1.setClient(client);

        Account a2 = new Account();
        a2.setName("a2");
        a2.setAmount(Money.of(Constants.CURRENCY, BigDecimal.ONE));
        a2.setCreationTime(DateTime.now());
        a2.setClient(client);

        client.setAccounts(Sets.newHashSet(a1, a2));

        clientCRUDService.create(client);

        context.close();
    }
}
