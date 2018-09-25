package com.ailk.aus.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.stream.Stream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpTest {

	public static void main(String[] args) throws IOException {

		FTPClient ftpClient = new FTPClient();
		ftpClient.setCharset(Charset.forName("UTF-8"));
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect("10.70.205.232", 21);
		boolean success = ftpClient.login("xxaq", "xxaq");
		if (!success) {
			throw new IOException("login failed : the username or password is incorrect! ");
		}
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setBufferSize(1024 * 1024 * 10);
		ftpClient.setDataTimeout(30000);
		ftpClient.setDefaultTimeout(30000);
		ftpClient.noop();
		ftpClient.enterLocalPassiveMode();
		System.out.println("connect to ftp server successful");
		String spool = "/fw/asa";
		ftpClient.changeWorkingDirectory(spool);
		System.out.println("change dir success:" + spool);
		// 此操作不支持，报没有权限
		// FTPFile[] files = ftpClient.listFiles();
		// 此操作支持
		String[] files = ftpClient.listNames();
		Stream.of(files).forEach(System.out::println);

	}

}
