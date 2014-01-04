package com.xeeapps.indexers;

import com.avaje.ebean.Ebean;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.xeeapps.models.Lyric;
import com.xeeapps.models.Song;
import com.xeeapps.proxies.ProxyList2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 14/10/13
 * Time: 04:38 ص
 * To change this template use File | Settings | File Templates.
 */
public class LyricsIndexer2 {
    private ProxyList2 proxyList2 ;
    private String currentProxy =null ;
    private String currentProxyAltered=null;
    public LyricsIndexer2(ProxyList2 proxyList2){
        this.proxyList2 =  proxyList2;
    }
    public void index(String character) throws IOException {
        List<Song> songs  ;

        songs = Ebean.find(Song.class).where().startsWith("html_page","../lyrics/"+character).findList();



        for(Song song : songs){
            if(Ebean.find(Lyric.class).where().eq("song_id",song.getId()).findList().size()>0){
                System.out.println("Song :"+song.getTitle()+" already exists in db.");
                continue;
            }
            try {
                //  ProxyList.getAProxy();
                String words = fetchLyric(song);

                Lyric lyric = new Lyric();
                lyric.setSong(song);
                lyric.setWords(words);
                Ebean.save(lyric) ;
                System.out.println("Lyrics for " + song.getTitle() + " has been fetched.");
            }   catch (Exception e){

                System.out.println(song.getTitle()+" Lyric Error.");
//                e.printStackTrace();
//                System.out.println(doc.body());
            }

        }

        //  return Jsoup.connect("http://proxy.primeoptic.net"+lyricsPage).ignoreContentType(true).get().select("div[style^=]").text();
    }

    public  String fetchLyric(Song song)  {

        Document doc = null;
        String AllText = "";
        String line = "";
        try{


            //   String currentLink = "http://www.azlyrics.com/" + song.getHtmlPage();
            currentProxy = proxyList2.getAProxy();
            currentProxyAltered = currentProxy;
       String urlText ="http://www.azlyrics.com/"+song.getHtmlPage().replace("../","");
            if(currentProxy.contains("base64")){
               urlText =  Base64.encode(urlText.getBytes()) ;
                currentProxyAltered = currentProxy.replaceAll("base64","");
            }
         //   System.out.println("url: "+currentProxy+"http://www.azlyrics.com/"+song.getHtmlPage().replace("../",""));
            URL url = new URL("http://"+currentProxyAltered+urlText);

            HttpURLConnection uc = (HttpURLConnection)url.openConnection( );
           uc.addRequestProperty("User-Agent", "Mozilla/4.76");
            uc.setConnectTimeout(180000);
            uc.setReadTimeout(180000);
            uc.setRequestProperty("Referer","http://"+currentProxyAltered.split("/")[0]);
       //     System.out.println("Referer "+"http://"+currentProxy.split("/")[0]);
//            uc.setRequestProperty("Referer", "http://anonymise.us/");
            uc.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"utf-8"));
            while ((line = in.readLine()) != null) {
//             System.out.println(line);
                AllText += line + "\n";
            }
   //     System.out.println(AllText);
            doc =   Jsoup.parse(AllText) ;

//        doc =  Jsoup.connect("http://www.azlyrics.com/"+song.getHtmlPage().replace("../","")).get();

            if(doc.select("div[class*=ring]").size()>0){
                String words  =  doc.select("div[style^=margin-left:10px;]").html();
                if (words.trim().equals("")){
                    System.out.println("Bad Proxy: "+currentProxy);
                    proxyList2.removeProxy(currentProxy) ;
                    currentProxy =  proxyList2.getAProxy();
                    return  fetchLyric(song);
                }
                return words;
            }else{
                proxyList2.removeProxy(currentProxy) ;
                currentProxy =  proxyList2.getAProxy();
                return  fetchLyric(song);
            }

        }catch (Exception e){
//            e.printStackTrace();
            System.out.println("Exception: "+e.getMessage());
            System.out.println("Bad Proxy: "+currentProxy);
            proxyList2.removeProxy(currentProxy) ;
            currentProxy =  proxyList2.getAProxy();
            return  fetchLyric(song);

        }
    }
}
