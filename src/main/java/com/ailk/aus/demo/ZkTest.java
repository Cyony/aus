package com.ailk.aus.demo;

import java.io.IOException;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public class ZkTest {

	public void testCurator(String[] args) throws Exception {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 3);
		CuratorFramework client = CuratorFrameworkFactory.newClient(args[0], retryPolicy);
		client.start();
		client.getChildren().forPath("/").forEach(System.out::println);
		LeaderLatch latch = new LeaderLatch(client, "/", "test");
		latch.start();
		System.out.println("get leader result:" + latch.hasLeadership());
		latch.close();

	}

	public void testApache(String[] args) throws KeeperException, InterruptedException, IOException {
		new ZooKeeper(args[0], 2000, null).getChildren("/", false).forEach(System.out::println);
	}

	public void testZkClient(String[] args) {
		ZkClient zkc = new ZkClient(new ZkConnection(args[0]), 10000);
		zkc.getChildren("/").forEach(System.out::println);
		System.out.println("=========create node=============");
		zkc.createPersistent(args[1]);
		System.out.println("============delete node============");
		zkc.deleteRecursive(args[1]);
	}

	public static void main(String[] args) throws Exception {
		ZkTest ZkTest = new ZkTest();
		ZkTest.testZkClient(args);
	}

}
