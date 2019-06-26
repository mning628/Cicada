package com.mn.a.config.rabbitmq;

import com.mn.a.util.HttpClientUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TransferAccountReceiver
{
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
        //TODO  B bank开放的请求转账的接口
        String url = "http://localhost:8081/transfer";
        String resultUrl = url + "/" + split[0] + "/" + split[1] + "/" + split[2];
        HttpClientUtils.executeGet(resultUrl);
    }


}
