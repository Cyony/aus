package com.ailk.aus.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Base64;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.sun.net.httpserver.HttpServer;

public class HttpServerDemo {

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		server.setExecutor(Executors.newCachedThreadPool());
		server.createContext("/test", exchange -> {
			System.out.println(exchange.getRemoteAddress().getHostString());
			exchange.getResponseHeaders().forEach((key, value) -> {
				System.out.println(key + "=>");
				value.stream().forEach(System.out::println);
			});
			OutputStream outputStream = exchange.getResponseBody();
			if (exchange.getRequestHeaders().containsKey("Authorization")) {
				String auth = exchange.getRequestHeaders().get("Authorization").get(0);
				String pwd = new String(Base64.getDecoder().decode(StringUtils.substringAfter(auth, " ")));
				if (!pwd.equals("aus:zhusy")) {
					String msg = "auth failed" + "\n";
					exchange.sendResponseHeaders(200, msg.length());
					outputStream.write(msg.getBytes());
					outputStream.close();
					return;
				}
			}
			System.out.println(IOUtils.toString(exchange.getRequestBody()));
			String responseString = "Hello! This a HttpServer!" + "\n";
			exchange.sendResponseHeaders(200, responseString.length());
			outputStream.write(responseString.getBytes());
			outputStream.close();
		});
		server.start();
	}

}
