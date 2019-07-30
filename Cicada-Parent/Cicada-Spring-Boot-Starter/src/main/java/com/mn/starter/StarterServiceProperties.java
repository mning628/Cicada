package com.mn.starter;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "example.service")
public class StarterServiceProperties
{
    private String config = "default";

    public void setConfig(String config)
    {
        this.config = config;
    }

    public String getConfig()
    {
        return config;
    }
}
