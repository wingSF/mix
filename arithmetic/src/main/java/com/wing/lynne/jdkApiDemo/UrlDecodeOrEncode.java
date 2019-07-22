package com.wing.lynne.jdkApiDemo;

import java.net.URLDecoder;

public class UrlDecodeOrEncode {

    public static void main(String[] args) {

        String url = "http://xxxxxxxx/ws/v1/timeline/TEZ_DAG_ID?primaryFilter=user%3A%22report_config_dev%22&secondaryFilter=status%3A%22SUCCEEDED%22&_=1562813132098";

        String decode = URLDecoder.decode(url);

        System.out.println(decode);

    }
}
