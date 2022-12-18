package ru.geekbrains.rabbitmq.console.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.Scanner;

public class DoubleDirectSender {
    private static final String EXCHANGE_NAME = "DoubleDirect";
    private static String message;

    public static void main(String[] argv) throws Exception {

        Scanner scanner = new Scanner(System.in);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            do {
                System.out.println("Введите тему и сообщение через пробел:");
                message = scanner.nextLine();
                String[] value = message.split(" ", 2);
                channel.basicPublish(EXCHANGE_NAME, value[0], null, value[1].getBytes("UTF-8"));
//                channel.basicPublish(EXCHANGE_NAME, "c++", null, "c++ msg".getBytes("UTF-8"));
//                channel.basicPublish(EXCHANGE_NAME, "java", null, "java msg".getBytes("UTF-8"));
            } while (exitCondition());
        }
    }

    public static boolean exitCondition(){
        Scanner scanner = new Scanner(System.in);
        String s;
        char a;
        for(;;){
            System.out.print("Продолжить? [y/n]:");
            s = scanner.nextLine();
            a = s.charAt(0);
            if(a == 'y')return true;
            else if(a == 'n')return false;
            System.out.print('\r');
        }
    }
}

