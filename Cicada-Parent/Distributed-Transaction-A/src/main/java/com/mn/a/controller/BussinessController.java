package com.mn.a.controller;

import com.mn.a.service.TransferAccountService;
import com.mn.a.vo.TransferAccountRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BussinessController
{

    @Autowired
    TransferAccountService transferAccountService;

    @PostMapping("/transfer")
    public void transferBusiness(@RequestBody TransferAccountRequestVo transferAccountRequestVo)
    {
        Integer businessStreamId = transferAccountService.transferAccout(transferAccountRequestVo);
        transferAccountService.sendMessageToQueue(transferAccountRequestVo, businessStreamId);
    }

    @GetMapping(value = "BBank/{businessId}/{status}")
    public void getBBankResult(@PathVariable("businessId") String businessId, @PathVariable("status") String status)
    {
        transferAccountService.resolveResult( businessId,  status);
    }


}
