package com.mn.b.entity;

import javax.persistence.*;

@Entity
@Table(name = "b_bank_account")
public class Account
{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String identity;
    private String name;
    private Integer money;
    /**
     * 建议使用Enum
     */
    private String status;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getIdentity()
    {
        return identity;
    }

    public void setIdentity(String identity)
    {
        this.identity = identity;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getMoney()
    {
        return money;
    }

    public void setMoney(Integer money)
    {
        this.money = money;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
