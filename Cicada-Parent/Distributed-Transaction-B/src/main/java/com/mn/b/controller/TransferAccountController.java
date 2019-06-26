package com.mn.b.controller;

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

    @GetMapping(value = "transfer/{businessStreamId}/{accountNo}/{money}")
    public void transferAccount(@PathVariable("businessStreamId") String businessStreamId, @PathVariable("accountNo") String accountNo,
                                @PathVariable("money") Integer money)
    {
        TransferAccountRequestVo transferAccountRequestVo = new TransferAccountRequestVo();
        transferAccountRequestVo.setBusinessId(businessStreamId);
        transferAccountRequestVo.setAccountId(accountNo);
        transferAccountRequestVo.setMoney(money);
        try
        {
            transferAccountService.transferAccount(transferAccountRequestVo);
            //TODO  发送消息可能会遇到网络异常，加重试机制
            transferAccountService.sendResultToAbank(businessStreamId, "1");
        }
        catch (Exception e)
        {
            //TODO  发送消息可能会遇到网络异常，加重试机制
            transferAccountService.sendResultToAbank(businessStreamId, "0");
        }
    }
}
