package com.xeeapps.proxies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 26/08/13
 * Time: 11:27 ص
 * To change this template use File | Settings | File Templates.
 */

//54.213.66.208:80,190.38.184.72:8080,190.199.248.212:8080,200.109.59.192:8080
public class ProxyList {
    private static ProxyList proxyList;

    public static synchronized ProxyList getInstance(String proxiesPath) throws IOException {
        if (proxyList==null){
            proxyList = new ProxyList(proxiesPath);
        }
        return  proxyList;
    }
    private static ArrayList<Proxy> proxies ;//= new ArrayList<>();
//    public static String[] proxies={"211.167.105.77:80"
//    ,"177.21.222.135:8080","74.221.209.228:7808","79.111.12.199:3128","89.248.118.232:3128","92.50.144.246:3128"};
    private ProxyList(String proxiesPath) throws IOException {
        this.proxies =  readProxies(proxiesPath);
    }
public static  void main(String args[]) throws IOException {

    ProxyList proxyList1 = new ProxyList("C:\\Users\\Khafaga\\Desktop\\proxies.txt");
//    for(int i=0;i<proxies.size();i++){
//        System.out.println( proxyList1.isValidProxy(proxies.get(i)));
//    }
        proxies =  proxyList1.readProxies("C:\\Users\\Khafaga\\Desktop\\proxies.txt");

    for (Proxy proxy : proxies){
        System.out.println(proxyList1.isValidProxy(proxy)+" "+proxy.address());
    }

//    System.out.println("After Check.. valid proxies are:");
//    for (int i=0;i<proxies.size();i++){
//        System.out.println(proxies.get(i));
//    }
}
    public ArrayList<Proxy> readProxies(String proxiesPath) throws IOException {
        ArrayList<Proxy> proxies = new ArrayList<Proxy>();
        File proxiesFile = new File(proxiesPath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(proxiesFile));
        String currentProxy  = null;
        System.out.println("Setting up Proxies.");
        while ((currentProxy=bufferedReader.readLine())!=null){

//            String currentProxy  = proxies.get(index);
            String proxyText[] =currentProxy.split(":");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyText[0],new Integer(proxyText[1])));
        //    if(isValidProxy(proxy)) {
//                System.out.println(currentProxy);

                proxies.add(proxy);
//            }else {
//                System.out.println("Invalid Proxy: "+currentProxy);
//            }


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
//        File proxiesFile = new File(proxiesPath);
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(proxiesFile));
//        String currentProxy  = null;
//        System.out.println("Setting up Proxies.");
//        while ((currentProxy=bufferedReader.readLine())!=null){
//
////            String currentProxy  = proxies.get(index);
//            String proxyText[] =currentProxy.split(":");
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyText[0],new Integer(proxyText[1])));
//            //    if(isValidProxy(proxy)) {
//            System.out.println(currentProxy);
//
//            proxies.add(proxy);
////            }else {
////                System.out.println("Invalid Proxy: "+currentProxy);
////            }
//
//
//        }
//        bufferedReader.close();
//        System.out.println("Number of Valid Proxies:"+proxies.size());
        return proxies;

    }


    public Proxy getAProxy(){
        if(proxies.size()==0){
            System.out.println("Ran out of proxies..!");
            System.exit(0);
        }
//        System.out.println("size here "+proxies.size());
        int index  = new Random().nextInt(proxies.size());
//        String currentProxy  = proxies.get(index);
//        String proxyText[] =currentProxy.split(":");
        Proxy proxy = proxies.get(index);
       //
//        Jsoup.proxy =  proxy;
        System.out.println("Using proxy: "+proxy.address());
        //index+" "+proxies[index]

         return  proxy;
    }


    public boolean removeProxy(Proxy proxy){
        System.out.println("Number of Valid Proxies:" + (proxies.size() - 1));
        boolean removed =  proxies.remove(proxy);
        System.out.println("Proxy "+ proxy.address()+" removed: "+removed);

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
