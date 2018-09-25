package com.ailk.aus.demo;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.HttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;
import org.apache.solr.client.solrj.response.CollectionAdminResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

import com.huawei.solr.client.solrj.impl.InsecureHttpClient;

public class hWSolrJ {

	public static void createCollection(String[] args) throws SolrException, SolrServerException, IOException {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 1000);
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 500);
		HttpClient httpClient = HttpClientUtil.createClient(params);
		httpClient = new InsecureHttpClient(httpClient, params);

		CloudSolrClient csClient = new CloudSolrClient(args[0], httpClient);
		csClient.setDefaultCollection("collection1");
		csClient.connect();

		CollectionAdminRequest.Create create = new CollectionAdminRequest.Create();
		create.setCollectionName(args[1]);
		create.setConfigName(args[2]);
		create.setNumShards(3);
		create.setReplicationFactor(1);

		CollectionAdminResponse response = null;
		response = create.process(csClient);
		if (response.isSuccess()) {
			System.out.println("=====success=======");
		} else {
			System.out.println("=============failed==============");
		}
	}

	public static void query(String[] args) throws SolrServerException, IOException {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 1000);
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 500);
		HttpClient httpClient = HttpClientUtil.createClient(params);
		httpClient = new InsecureHttpClient(httpClient, params);

		CloudSolrClient csClient = new CloudSolrClient(args[0], httpClient);
		csClient.setDefaultCollection("collection1");
		csClient.connect();

		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");
		QueryResponse response = csClient.query(query);
		SolrDocumentList docs = response.getResults();
		for (SolrDocument doc : docs) {
			System.out.println(doc);
		}
		csClient.close();

	}

	public static void indexDoc(String[] args) throws SolrServerException, IOException {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS, 1000);
		params.set(HttpClientUtil.PROP_MAX_CONNECTIONS_PER_HOST, 500);
		HttpClient httpClient = HttpClientUtil.createClient(params);
		httpClient = new InsecureHttpClient(httpClient, params);
		CloudSolrClient csClient = new CloudSolrClient(args[0], httpClient);
		csClient.setDefaultCollection("collection1");
		csClient.connect();

		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", new Date().getTime());
		doc.addField("name", "A lovely summer holiday");
		csClient.add(doc);
		csClient.commit();
		csClient.close();
	}

	public static void main(String[] args) throws SolrServerException, IOException {
		query(args);
	}

}
