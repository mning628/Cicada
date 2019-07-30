package com.mn.starter;

public class StarterService
{
    private String config;

    public StarterService(String config)
    {
        this.config = config;
    }

    public String getConfig()
    {
        return config;
    }

    public void print()
    {
        System.out.println(config);
    }
}
