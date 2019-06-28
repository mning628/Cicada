package com.mn.b.service;

import com.mn.b.entity.Account;
import com.mn.b.entity.BusinessStream;
import com.mn.b.repository.AccountRepository;
import com.mn.b.repository.BusinessStreamRepository;
import com.mn.b.util.HttpClientUtils;
import com.mn.b.vo.TransferAccountRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransferAccountService
{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BusinessStreamRepository businessStreamRepository;

    @Value("${b.response.url:http://localhost:8080/BBank}")
    String responseUrl;

    @Transactional
    public void transferAccount(TransferAccountRequestVo transferAccountRequestVo)
    {
        //记录业务流水表  可以考虑加条状态，标记这条业务数据是否正常
        String businessId = transferAccountRequestVo.getBusinessId();
        String accountId = transferAccountRequestVo.getAccountId();
        Integer money = transferAccountRequestVo.getMoney();
        BusinessStream newBusinessStream = new BusinessStream();
        newBusinessStream.setAccountNo(accountId);
        newBusinessStream.setBusinessId(businessId);
        newBusinessStream.setAmount(money);
        //可以根据状态判断有没有正确返回给对方银行，若发送失败则重新发送
        newBusinessStream.setStatus("0");
        businessStreamRepository.saveAndFlush(newBusinessStream);

        //加账户余额
        Account account = accountRepository.findByIdentity(accountId);
        if (account == null)
        {
            throw new RuntimeException("账户不存在.");
        }
        Integer oldMoney = account.getMoney();
        account.setMoney(money + oldMoney);
        accountRepository.save(account);
    }

    public void sendResultToAbank(String businessId, String status)
    {
        //响应给A银行结果
        String url = responseUrl + "/" + businessId + "/" + status;
        //TODO  发送消息可能会遇到网络异常，加重试机制
        try
        {
            HttpClientUtils.executeGet(url);
        }
        catch (Exception e)
        {
            //TODO  记录状态再次回调
            e.printStackTrace();
        }
    }
}
