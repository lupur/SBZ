package com.sbz.agro_kjar.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.management.MXBean;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ActuatorService {
    
    private final static String url = "http://localhost:8081";
    
    public static void SendActuation(String serialNo, String value) {
        try {
            HttpPost post = new HttpPost(url);
            
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"serialNo\":\""+serialNo+"\",");
            json.append("\"state\":\""+value+"\"");
            json.append("}");
            
            post.setEntity(new StringEntity(json.toString()));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            httpClient.execute(post);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
