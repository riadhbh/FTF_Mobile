package com.foot.basicdrawer.Network;

import org.jsoup.nodes.*;

import static com.foot.basicdrawer.ToolBox.AppSettings.getLang;


/**
 * Created by riadh on 9/3/2016.
 */
public class Server_Host_Constant {
public static final String APIServHost="ftfmobile.000webhostapp.com";//"192.168.137.1";//"ftfmobile.sts-tech.tn";//"169.254.216.207";//10.0.2.2
private static final String APIServProtocol="http";
public static final String APISite=APIServProtocol+"://"+APIServHost+"/ftfmobile";
public static Document NewsDoc=null;


public static final String WebsiteHost="www.ftf.org.tn";
private static final String WebsiteProtocol="http";
public static final String MyWebsite=WebsiteProtocol+"://"+WebsiteHost;

    private static String SitelangCodeFR="fr";
    private static String SitelangCodeAR="ar2" ;


private static String NewsLink =MyWebsite+"/fr/category/actualites/";


    public static String getSiteLangCode(){
        if(getLang().equals("ar"))
            return SitelangCodeAR;
        else return SitelangCodeFR;

    }


/*
    public static String getHtml(String url) throws IOException {
        // Build and set timeout values for the request.
        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        // Read and store the result line by line then return the entire string.
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line);
        }
        in.close();

        return html.toString();
    }*/

}
