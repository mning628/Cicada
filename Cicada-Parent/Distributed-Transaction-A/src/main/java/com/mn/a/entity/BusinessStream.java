package com.mn.a.entity;


import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "a_bank_stream")
public class BusinessStream
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String accountNo;


    private String targetAccountNo;

    private Integer amount;


    /**
     * 0: 已接受请求
     * 1：已放入消息队列
     * 2：放消息队列失败
     * 3：对端处理失败
     * 4：对端处理成功
     */
    private String status;

    @UpdateTimestamp
    private Date lastUpdateTime;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

}
