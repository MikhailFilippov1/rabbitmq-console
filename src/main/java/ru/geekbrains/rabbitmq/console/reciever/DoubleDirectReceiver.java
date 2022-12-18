package ru.geekbrains.rabbitmq.console.reciever;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class DoubleDirectReceiver {

    private static final String EXCHANGE_NAME = "DoubleDirect";

    private static String theme;

    public static void main(String[] argv) throws Exception {
        Scanner scanner = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();
        System.out.println("My queue name: " + queueName);

        System.out.println("Введите тему:");
        theme = scanner.nextLine();

        channel.queueBind(queueName, EXCHANGE_NAME, theme);

        System.out.println(" [*] Waiting for messages");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
            System.out.println(Thread.currentThread().getName());
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
