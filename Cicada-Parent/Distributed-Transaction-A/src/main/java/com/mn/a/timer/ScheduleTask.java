package com.mn.a.timer;

import com.mn.a.config.rabbitmq.TransferAccountSender;
import com.mn.a.entity.BusinessStream;
import com.mn.a.repository.BusinessStreamRepository;
import com.mn.a.vo.TransferAccountRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduleTask
{
    @Autowired
    BusinessStreamRepository businessStreamRepository;

    @Autowired
    TransferAccountSender transferAccountSender;


    @Scheduled(cron = "0/10 * * * * ? ")
    public void resovleFailInsertQueueTask()
    {
        List<BusinessStream> businessStreams = businessStreamRepository.findByStatus("2");
        for (BusinessStream businessStream : businessStreams)
        {
            TransferAccountRequestVo transferAccountRequestVo = new TransferAccountRequestVo();
            transferAccountRequestVo.setAmount(businessStream.getAmount());
            transferAccountRequestVo.setIdentity(businessStream.getAccountNo());
            transferAccountRequestVo.setTargetAccountNo(businessStream.getTargetAccountNo());
            transferAccountSender.send(transferAccountRequestVo,businessStream.getId());
        }
    }
}
