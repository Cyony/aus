package com.ailk.aus.demo;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;


public class DFStest {

    public static void main(String[] args) throws IOException {
        // 注释aaaa
        // github
        // qqqq
        Configuration config = new Configuration();
        config.addResource(new Path(args[0]));
        config.addResource(new Path(args[1]));
        UserGroupInformation.setConfiguration(config);
        UserGroupInformation.loginUserFromKeytab(args[2], args[3]);
        FileSystem fs = FileSystem.get(config);
        for (FileStatus file : fs.listStatus(new Path("/"))) {
            System.out.println(file.getPath().getName());
        }
    }

}
