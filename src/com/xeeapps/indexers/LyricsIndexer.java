package com.xeeapps.indexers;

import com.avaje.ebean.Ebean;
import com.xeeapps.models.Lyric;
import com.xeeapps.models.Song;
import com.xeeapps.proxies.ProxyList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 25/08/13
 * Time: 12:52 م
 * To change this template use File | Settings | File Templates.
 */
public class LyricsIndexer {
    private ProxyList proxyList ;
    private Proxy currentProxy =null ;
    public LyricsIndexer(ProxyList proxyList){
        this.proxyList =  proxyList;
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
         URL url = new URL("http://www.azlyrics.com/"+song.getHtmlPage().replace("../",""));
         currentProxy = proxyList.getAProxy();
         HttpURLConnection uc = (HttpURLConnection)url.openConnection(currentProxy );
         uc.setConnectTimeout(160000);
         uc.setReadTimeout(160000);
         uc.connect();
         BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"utf-8"));
         while ((line = in.readLine()) != null) {
//             System.out.println(line);
             AllText += line + "\n";
         }
//         System.out.println(AllText);
         doc =   Jsoup.parse(AllText) ;

//        doc =  Jsoup.connect("http://www.azlyrics.com/"+song.getHtmlPage().replace("../","")).get();

         if(doc.select("a[href=http://www.azlyrics.com/privacy.html]").size()>0){
             String words  =  doc.select("div[style^=margin-left:10px;]").html();
             if (words.trim().equals("")){
                 System.out.println("Bad Proxy: "+currentProxy.address());
                 proxyList.removeProxy(currentProxy) ;
                 currentProxy =  proxyList.getAProxy();
                 return  fetchLyric(song);
             }
             return words;
         }else{
             proxyList.removeProxy(currentProxy) ;
             currentProxy =  proxyList.getAProxy();
             return  fetchLyric(song);
         }

     }catch (Exception e){
         System.out.println("Exception: "+e.getMessage());
         System.out.println("Bad Proxy: "+currentProxy.address());
         proxyList.removeProxy(currentProxy) ;
         currentProxy =  proxyList.getAProxy();
         return  fetchLyric(song);
     }
        }
}
