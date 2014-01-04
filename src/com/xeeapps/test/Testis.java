package com.xeeapps.test;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 08/10/13
 * Time: 05:35 م
 * To change this template use File | Settings | File Templates.
 */
public class Testis {
    public static void main(String args[]) throws IOException {
////          Document doc =  Jsoup.connect("http://spys.ru/en/anonymous-proxy-list/").get();
////        Elements elements =         doc.getAllElements();
////        for (Element element: elements){
////            System.out.println("Element: "+element);
////        }
//
//        // Jsoup.proxy =  proxy;
////
////    URL url = new URL("http://proxy.primeoptic.net/browse.php?u=http://www.azlyrics.com/a.html");
////
//        URL url = new URL("http://spys.ru/en/anonymous-proxy-list/");
//        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
//        uc.setConnectTimeout(120000);
//        uc.setReadTimeout(120000);
////       // uc.setRequestProperty("Cookie","s=f6973a7b407f0b5520b8cdebc4be7dfc");
////     //   uc.setRequestProperty("Referer", "http://proxy.primeoptic.net/");
////        uc.addRequestProperty("User-Agent", "Mozilla/4.76");
//////        uc.addRequestProperty("Cookie", "ss=f8469d889d4ac011b2d73e3490e76a81; path=/");
//        uc.connect();
////        Map<String,List<String>> headerFields  =  uc.getHeaderFields();
//////        Jsoup.session =    headerFields.get("Set-Cookie").get(0);
//        String AllText = "";
//        String line = "";
//        BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
//        while ((line = reader.readLine()) != null) {
//            AllText += line + "\n";
//        }
////        ;
//        System.out.println(AllText);

        char character  =  'a';
        int characterIntegered =  character;
       int end =  'z';
//        for (int i=characterIntegered;i<=end;i++) {
        System.out.println((int)character);
        System.out.println((int)end);
        System.out.println(Base64.encode("someText".getBytes()))  ;
    }




//    }
}
