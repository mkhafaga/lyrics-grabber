package com.xeeapps.proxies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 15/10/13
 * Time: 04:28 ص
 * To change this template use File | Settings | File Templates.
 */
public class ProxyList2 {
    private static ProxyList2 proxyList2;

    public static synchronized ProxyList2 getInstance(String proxiesPath) throws IOException {
        if (proxyList2==null){
            proxyList2 = new ProxyList2(proxiesPath);
        }
        return  proxyList2;
    }
    private static ArrayList<String> proxies ;
    private ProxyList2(String proxiesPath) throws IOException {
        this.proxies =  readProxies(proxiesPath);
    }
    public static  void main(String args[]) throws IOException {

        ProxyList2 proxyList2 = new ProxyList2("C:\\Users\\Khafaga\\Desktop\\proxies.txt");
        proxies =  proxyList2.readProxies("C:\\Users\\Khafaga\\Desktop\\proxies.txt");
    }
    public ArrayList<String> readProxies(String proxiesPath) throws IOException {
        ArrayList<String> proxies = new ArrayList<String>();
        File proxiesFile = new File(proxiesPath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(proxiesFile));
        String currentProxy  = null;
        System.out.println("Setting up Proxies.");
        while ((currentProxy=bufferedReader.readLine())!=null){

            proxies.add(currentProxy);
            System.out.println(currentProxy);
        }
        bufferedReader.close();
        System.out.println("Number of Valid Proxies:"+proxies.size());
        return proxies;

    }


    public ArrayList<Proxy> readProxies() throws IOException {
        Document doc =  Jsoup.connect("http://spys.ru/en/anonymous-proxy-list/").get() ;
        Elements elems =  doc.children();//select("td[colspan=1]");

        for (Element elem : elems){
            System.out.println(elem.html());
        }
        ArrayList<Proxy> proxies = new ArrayList<Proxy>();

        return proxies;

    }


    public String getAProxy(){
        if(proxies.size()==0){
            System.out.println("Ran out of proxies..!");
            System.exit(0);
        }
        int index  = new Random().nextInt(proxies.size());
        String proxy = proxies.get(index);
        System.out.println("Using proxy: "+proxy);
        return  proxy;
    }






    public boolean removeProxy(String proxy){
        System.out.println("Number of Valid Proxies:" + (proxies.size() - 1));
        boolean removed =  proxies.remove(proxy);
        System.out.println("Proxy "+ proxy+" removed: "+removed);

        return  removed   ;
    }

    public boolean isValidProxy(Proxy proxy) {
        try {
            URL url = new URL("http://www.azlyrics.com/");

            HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy );
            uc.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            in.readLine();
        }    catch (Exception e){
//            removeProxy(proxy);
            return false;
        }

        return true;
    }
}
