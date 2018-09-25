package com.ailk.aus.demo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HClientTest {

	public static void main(String[] args) throws Exception {
		DefaultHttpClient httpClient = new SSLClient();
		HttpPost post = new HttpPost(args[0]);
		post.addHeader("Content-Type", "application/json");
		post.setEntity(new StringEntity(args[1]));
		HttpResponse response = httpClient.execute(post);
		System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
	}

}
