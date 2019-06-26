package com.mn.b.service;

import com.mn.b.entity.Account;
import com.mn.b.entity.BusinessStream;
import com.mn.b.repository.AccountRepository;
import com.mn.b.repository.BusinessStreamRepository;
import com.mn.b.util.HttpClientUtils;
import com.mn.b.vo.TransferAccountRequestVo;
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

    @Transactional
    public void transferAccount(TransferAccountRequestVo transferAccountRequestVo)
    {
        String businessId = transferAccountRequestVo.getBusinessId();
        //保证消费的幂等性，根据流水id进行管控
        BusinessStream businessStream = businessStreamRepository.findByBusinessId(businessId);
        if (businessStream != null)
        {
            System.out.println("this business has resolve.");
            return;
        }

        String accountId = transferAccountRequestVo.getAccountId();
        Integer money = transferAccountRequestVo.getMoney();
        //先将账户余额加上
        Account account = accountRepository.findByIdentity(accountId);
        if (account == null)
        {
            return;
        }
        Integer oldMoney = account.getMoney();
        account.setMoney(money + oldMoney);
        accountRepository.save(account);

        //记录业务流水表
        BusinessStream newBusinessStream = new BusinessStream();
        newBusinessStream.setAccountNo(accountId);
        newBusinessStream.setBusinessId(businessId);
        newBusinessStream.setAmount(money);
        businessStreamRepository.saveAndFlush(newBusinessStream);
    }

    public void sendResultToAbank(String businessId, String status)
    {
        //响应给A银行结果
        //TODO  url配置对端A银行的响应结果,,0为失败，1为成功
        String url = "   http://localhost:8080/BBank" + "/" + businessId + "/" + status;
        HttpClientUtils.executeGet(url);
    }
}
