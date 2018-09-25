package com.ailk.aus.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

public class SolrJTest {

	private static String doCasLoginRequest(DefaultHttpClient httpclient, String url) throws IOException {
		try {
			String result = "";
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String tempLine = rd.readLine();
			String s = "<input type=\"hidden\" name=\"lt\" value=\"";
			while (tempLine != null) {
				int index = tempLine.indexOf(s);
				if (index != -1) {
					String s1 = tempLine.substring(index + s.length());
					int index1 = s1.indexOf("\"");
					if (index1 != -1)
						result = s1.substring(0, index1);
				}
				tempLine = rd.readLine();
			}
			if (entity != null) {
				entity.getContent().close();
			}
			return result;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static DefaultHttpClient casLogin(String url) throws Exception {
		DefaultHttpClient httpClient = new SSLClient();
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0");
		HttpPost post = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("username", "admin"));
		nvps.add(new BasicNameValuePair("password", "xxxx"));
		nvps.add(new BasicNameValuePair("lt", doCasLoginRequest(httpClient, url)));
		nvps.add(new BasicNameValuePair("execution", "e1s1"));
		nvps.add(new BasicNameValuePair("_eventId", "submit"));
		nvps.add(new BasicNameValuePair("submit", "登录"));
		post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		HttpResponse response = httpClient.execute(post);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			httpClient.getCookieStore().getCookies().forEach(c -> {
				System.out.println(c.getName() + "=>" + c.getValue());
			});
			entity.getContent().close();
		}
		return httpClient;
	}

	public static void cloudClient(String[] args) throws Exception {

		CloudSolrClient csClient = new CloudSolrClient(args[0]);
		csClient.setDefaultCollection("collection1");
		csClient.connect();

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", new Date().getTime());
		doc.addField("name", "A lovely summer holiday");
		csClient.add(doc);
		csClient.commit();
		csClient.close();
	}

	public static void solrClient(String[] args) throws Exception {

		HttpSolrClient client = new HttpSolrClient(args[0], casLogin(args[1]));
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", new Date().getTime());
		doc.addField("name", "A lovely summer holiday");
		client.add(doc);
		client.commit();
		client.close();

	}

	public static void main(String[] args) throws Exception {
		solrClient(args);
	}

}
