package com.mn.a.vo;

public class TransferAccountRequestVo
{

    /**
     * 转账人，因不能从缓存取用户信息，手动传
     */
    private String identity;

    /**
     * 目标账户
     */
    private String targetAccountNo;

    /**
     * 转账金额
     */
    private Integer amount;

    public String getIdentity()
    {
        return identity;
    }

    public void setIdentity(String identity)
    {
        this.identity = identity;
    }

    public String getTargetAccountNo()
    {
        return targetAccountNo;
    }

    public void setTargetAccountNo(String targetAccountNo)
    {
        this.targetAccountNo = targetAccountNo;
    }

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
}
