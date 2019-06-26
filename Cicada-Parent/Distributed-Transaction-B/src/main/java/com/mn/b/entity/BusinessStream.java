package com.mn.b.entity;


import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "b_bank_stream")
public class BusinessStream
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String fromAccountNo;

    private String fromBankNo;

    private String accountNo;

    private Integer amount;

    private String status;

    private String businessId;

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

    public String getFromAccountNo()
    {
        return fromAccountNo;
    }

    public void setFromAccountNo(String fromAccountNo)
    {
        this.fromAccountNo = fromAccountNo;
    }

    public String getFromBankNo()
    {
        return fromBankNo;
    }

    public void setFromBankNo(String fromBankNo)
    {
        this.fromBankNo = fromBankNo;
    }

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
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

    public String getBusinessId()
    {
        return businessId;
    }

    public void setBusinessId(String businessId)
    {
        this.businessId = businessId;
    }
}
