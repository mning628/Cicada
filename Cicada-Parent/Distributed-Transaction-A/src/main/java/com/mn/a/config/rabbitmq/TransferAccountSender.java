package com.mn.a.config.rabbitmq;

import com.mn.a.entity.BusinessStream;
import com.mn.a.repository.BusinessStreamRepository;
import com.mn.a.vo.TransferAccountRequestVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferAccountSender implements RabbitTemplate.ConfirmCallback
{

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    BusinessStreamRepository businessStreamRepository;

    public void send(TransferAccountRequestVo transferAccountRequestVo, Integer businessSteamId)
    {
        //此id用businessSteamId也是可以的
        CorrelationData correlationData = new CorrelationData(String.valueOf(businessSteamId));
        rabbitTemplate.setConfirmCallback(this);
        //message--> 随机id:接受账户:转入金额
        //随机id用于对端做幂等校验
        StringBuilder message = new StringBuilder();
        message.append(correlationData.getId());
        message.append(":");
        message.append(transferAccountRequestVo.getTargetAccountNo());
        message.append(":");
        message.append(transferAccountRequestVo.getAmount());
        this.rabbitTemplate.convertAndSend("", RabbitMqConfig.QUEUE_NAME, message.toString(), correlationData);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String s)
    {
        String id = correlationData.getId();
        BusinessStream businessStream = businessStreamRepository.findOne(Integer.parseInt(id));
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
