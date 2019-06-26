package com.mn.a.config.rabbitmq;

import com.mn.a.entity.BusinessStream;
import com.mn.a.repository.BusinessStreamRepository;
import com.mn.a.vo.TransferAccountRequestVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransferAccountSender implements RabbitTemplate.ConfirmCallback
{

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    BusinessStreamRepository businessStreamRepository;

    public void send(TransferAccountRequestVo transferAccountRequestVo, Integer businessSteamId)
    {

        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        BusinessStream businessSteam = businessStreamRepository.findOne(businessSteamId);
        businessSteam.setQueueId(correlationData.getId());
        businessStreamRepository.saveAndFlush(businessSteam);


        rabbitTemplate.setConfirmCallback(this);
        StringBuilder message = new StringBuilder();
        message.append(correlationData.getId());
        message.append(":");
        message.append(transferAccountRequestVo.getTargetAccountNo());
        message.append(":");
        message.append(transferAccountRequestVo.getAmount());

        System.out.println(message.toString());

        this.rabbitTemplate.convertAndSend("", RabbitMqConfig.QUEUE_NAME, message.toString(), correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s)
    {
        String id = correlationData.getId();
        BusinessStream businessStream = businessStreamRepository.findByQueueId(id);
        if (ack)
        {
            //已放入消息队列
            businessStream.setStatus("1");
        }
        else
        {
            //放入消息队列失败
            businessStream.setStatus("2");
        }
        businessStreamRepository.saveAndFlush(businessStream);
    }
}
