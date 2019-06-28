package com.mn.a.config.rabbitmq;

import com.mn.a.util.HttpClientUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TransferAccountReceiver
{
    @Value("${transfer.request.url:http://localhost:8081/transfer}")
    String transferRequestUrl;

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void receiver(Message message, Channel channel) throws IOException
    {
        String s = new String(message.getBody(), "UTF-8");
        System.out.println(s);
        //从消息队列中取消息，然后发送给B系统
        String[] split = s.split(":");
        if (split.length != 3)
        {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        //手动提交ack
        else
        {
            sendMessageToBbank(split);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }


    private void sendMessageToBbank(String[] split)
    {
        String resultUrl = transferRequestUrl + "/" + split[0] + "/" + split[1] + "/" + split[2];
        //Todo 异步请求更好一些
        HttpClientUtils.executeGet(resultUrl);
    }


}
