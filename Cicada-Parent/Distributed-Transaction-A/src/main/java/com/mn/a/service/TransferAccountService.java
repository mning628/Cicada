package com.mn.a.service;

import com.mn.a.config.rabbitmq.TransferAccountSender;
import com.mn.a.entity.Account;
import com.mn.a.entity.BusinessStream;
import com.mn.a.repository.AccountRepository;
import com.mn.a.repository.BusinessStreamRepository;
import com.mn.a.vo.TransferAccountRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransferAccountService
{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BusinessStreamRepository businessStreamRepository;

    @Autowired
    TransferAccountSender transferAccountSender;

    @Transactional
    public Integer transferAccout(TransferAccountRequestVo transferAccountRequestVo)
    {
        updateAccount(transferAccountRequestVo);
        Integer businessSteamId = insertBusinessStream(transferAccountRequestVo);
        return businessSteamId;
    }

    private void updateAccount(TransferAccountRequestVo transferAccountRequestVo)
    {
        //先将账户余额减少
        String identity = transferAccountRequestVo.getIdentity();
        Account account = accountRepository.findAccountByIdentity(identity);
        Integer money = account.getMoney();
        //要转账的钱数
        Integer amount = transferAccountRequestVo.getAmount();
        if (money > amount)
        {
            account.setMoney(money - amount);
        }
        //TODO 如果小于，直接报错
        accountRepository.save(account);
    }

    private Integer insertBusinessStream(TransferAccountRequestVo transferAccountRequestVo)
    {
        //插入流水记录表
        BusinessStream businessSteam = new BusinessStream();
        businessSteam.setAccountNo(transferAccountRequestVo.getIdentity());
        businessSteam.setAmount(transferAccountRequestVo.getAmount());
        //已接受请求
        businessSteam.setStatus("0");
        businessSteam.setTargetAccountNo(transferAccountRequestVo.getTargetAccountNo());
        businessStreamRepository.save(businessSteam);
        Integer businessSteamId = businessSteam.getId();
        return businessSteamId;
    }


    public void sendMessageToQueue(TransferAccountRequestVo transferAccountRequestVo, Integer businessSteamId)
    {
        transferAccountSender.send(transferAccountRequestVo, businessSteamId);
    }

    public void resolveResult(String businessId, String status)
    {
        BusinessStream businessStream = businessStreamRepository.findByQueueId(businessId);
        if (businessId != null)
        {
            if ("0".equals(status))
            {
                businessStream.setStatus("3");
                //对方告知转账失败，将钱数回滚
                Integer amount = businessStream.getAmount();
                String accountNo = businessStream.getAccountNo();
                Account account = accountRepository.findAccountByIdentity(accountNo);
                Integer money = account.getMoney();
                account.setMoney(money+amount);
                accountRepository.save(account);
            }
            if ("1".equals(status))
            {
                businessStream.setStatus("4");
            }
            businessStreamRepository.saveAndFlush(businessStream);
        }

    }
}
