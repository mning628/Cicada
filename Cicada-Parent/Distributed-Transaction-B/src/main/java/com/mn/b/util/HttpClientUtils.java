package com.mn.b.util;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class HttpClientUtils
{

    public static void executeGet(String url)
    {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
        try
        {
            httpClient.execute(get);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
