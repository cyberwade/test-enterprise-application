package ru.testea.impl;

import org.joda.time.LocalDate;
import ru.testea.api.Account;
import ru.testea.api.Client;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.Collections;

public class Main
{
    public static void main(
        String[] args)
    {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ru.testea").createEntityManager();
        entityManager.getTransaction().begin();

        Client client = new Client();
        client.setFullName("Иванов Иван Иванович");
        client.setBirthday(LocalDate.parse("1980-01-01"));
        client.setAddress("г. Москва, ул. Ленина, д. 1");
        client.setAccounts(Collections.<Account>emptySet());

        ClientRepository repository = new ClientRepository();
        repository.entityManager = entityManager;

        repository.persist(client);

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
