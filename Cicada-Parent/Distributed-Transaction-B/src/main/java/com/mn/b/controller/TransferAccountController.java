package com.mn.b.controller;

import com.mn.b.entity.BusinessStream;
import com.mn.b.repository.AccountRepository;
import com.mn.b.repository.BusinessStreamRepository;
import com.mn.b.service.TransferAccountService;
import com.mn.b.vo.TransferAccountRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferAccountController
{

    @Autowired
    TransferAccountService transferAccountService;

    @Autowired
    BusinessStreamRepository businessStreamRepository;

    @Autowired
    AccountRepository accountRepository;


    @GetMapping(value = "transfer/{businessStreamId}/{accountNo}/{money}")
    public void transferAccount(@PathVariable("businessStreamId") String businessStreamId, @PathVariable("accountNo") String accountNo,
                                @PathVariable("money") Integer money)
    {
        TransferAccountRequestVo transferAccountRequestVo = new TransferAccountRequestVo();
        transferAccountRequestVo.setBusinessId(businessStreamId);
        transferAccountRequestVo.setAccountId(accountNo);
        transferAccountRequestVo.setMoney(money);
        //保证消费的幂等性，根据流水id进行管控, 可以拆成方法
        String businessId = transferAccountRequestVo.getBusinessId();
        BusinessStream businessStream = businessStreamRepository.findByBusinessId(businessId);
        if (businessStream != null)
        {
            System.out.println("this business has resolve.");
            return;
        }
        try
        {
            transferAccountService.transferAccount(transferAccountRequestVo);
            transferAccountService.sendResultToAbank(businessStreamId, "1");
        }
        catch (Exception e)
        {
            transferAccountService.sendResultToAbank(businessStreamId, "0");
        }
    }
}
