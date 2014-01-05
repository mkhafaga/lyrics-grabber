package com.xeeapps.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 23/08/13
 * Time: 03:41 م
 * To change this template use File | Settings | File Templates.
 */
public class UrlTest {
    public static void main(String args[]) throws IOException {    //90.150.9.38:1080
       String proxyText[] ="88.159.114.9:80".trim().split(":");
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyText[0],new Integer(proxyText[1])));

       // Jsoup.proxy =  proxy;
//
//    URL url = new URL("http://anonymise.us/browse.php?u=http://www.azlyrics.com/lyrics/maryjblige/leaveamessage.html");
//
        URL url = new URL("http://www.azlyrics.com/b/ballard.html");
        HttpURLConnection uc = (HttpURLConnection) url.openConnection(proxy);
        uc.setConnectTimeout(160000);
        uc.setReadTimeout(160000);
//       // uc.setRequestProperty("Cookie","s=f6973a7b407f0b5520b8cdebc4be7dfc");
//   uc.setRequestProperty("Referer", "http://anonymise.us");
//        uc.addRequestProperty("User-Agent", "Mozilla/4.76");
////        uc.addRequestProperty("Cookie", "ss=f8469d889d4ac011b2d73e3490e76a81; path=/");
        uc.connect();
//        Map<String,List<String>> headerFields  =  uc.getHeaderFields();
////        Jsoup.session =    headerFields.get("Set-Cookie").get(0);
        String AllText = "";
        String line = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while ((line = reader.readLine()) != null) {
            AllText += line + "\n";
        }
//        ;
        System.out.println(AllText);
//        Map<String,List<String>> headerFields  =  uc.getHeaderFields();
//        headerFields.get("Set-Cookie").get(0);
//        Set entries =  null;
//        Iterator it = headerFields.entrySet().iterator();
//      while (it.hasNext()){
//          Map.Entry pairs = (Map.Entry)it.next();
//         System.out.println(pairs.getKey()+" - "+ pairs.getValue());
//      }
//
//        System.out.println(AllText);
//        ArtistsIndexer artistsIndexer = new ArtistsIndexer();
//        artistsIndexer.index();
//
//        TitlesIndexer titlesIndexer = new TitlesIndexer();
//        titlesIndexer.index();
//        LyricsIndexer lyricsIndexer = new LyricsIndexer();
//        lyricsIndexer.index();
    }
}
