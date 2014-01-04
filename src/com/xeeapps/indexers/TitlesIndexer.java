package com.xeeapps.indexers;

import com.avaje.ebean.Ebean;
import com.xeeapps.models.Album;
import com.xeeapps.models.Artist;
import com.xeeapps.models.Song;
import com.xeeapps.proxies.ProxyList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
 * Date: 22/08/13
 * Time: 06:34 ص
 * To change this template use File | Settings | File Templates.
 */
public class TitlesIndexer {

        private  ProxyList proxyList ;
    public TitlesIndexer(ProxyList proxyList) {
            this.proxyList = proxyList;
    }

    public void index(String character) throws IOException {

        char a = 'a';
        List<Artist> artists = Ebean.find(Artist.class).where().startsWith("html_page", character).findList();

        for (Artist artist : artists) {
            List<Song> songs  =  Ebean.find(Song.class).where().eq("artist_id",artist.getId()).findList();
            if(!songs.isEmpty()){
                System.out.println("Artist : "+artist.getArtistName()+" exists in the database.");
                continue;
            }


           //here
            Elements elms =   fetchArtistData(artist);
            String currentAlbumString = null;

            for (Element element : elms) {

                if (element.className().equals("album")&&!(element.text().trim().equals(""))){

                    currentAlbumString = element.text();
                    System.out.println("Indexing album: "+currentAlbumString);
                    indexAlbum(currentAlbumString,artist);
                } else if (element.tagName().equals("a")) {

                    String title = element.text();
                String html =    element.attr("href") ;

                    if (html.startsWith("../lyrics/"))     {

                    indexSong(artist, currentAlbumString, title,html );
                        System.out.println("Indexing song: "+title);
                    }
                }

            }
        }


    }

    public boolean indexSong(Artist artist, String currentAlbumString, String title, String htmlPage) {

        Song currentSong = new Song();
        currentSong.setArtist(artist);
        if (currentAlbumString != null) {
            if (!currentAlbumString.equals("other songs:") && !currentAlbumString.equals(" "))
                System.out.println("currentAlbumString: "+currentAlbumString+" - artist_id: "+artist.getId());
                currentSong.setAlbum(Ebean.find(Album.class).where().eq("album_name", currentAlbumString).eq("artist_id", artist.getId()).findUnique());
        }
     //   currentSong.setLyrics(lyrics);
        currentSong.setTitle(title);
        currentSong.setHtmlPage(htmlPage);
       Ebean.save(currentSong);
        return true;
    }

    public void indexAlbum(String currentAlbumString
    ,Artist artist) {
        Album currentAlbum = null;
        currentAlbum = new Album();

        if (currentAlbumString.equals("other songs:") || currentAlbumString.equals(" ")) {
            return;
        }
        currentAlbum.setAlbumName(currentAlbumString);
        currentAlbum.setArtist(artist);
        Ebean.save(currentAlbum);
    }

    public Elements fetchArtistData(Artist artist){
        Document doc = null;
        String AllText = "";
        String line = "";
         Proxy currentProxy = proxyList.getAProxy();
        String currentLink = "http://www.azlyrics.com/" + artist.getHtmlPage();
        try { URL url = new URL(currentLink);

            HttpURLConnection uc = (HttpURLConnection)url.openConnection(currentProxy );
            uc.setConnectTimeout(90000);
            uc.setReadTimeout(90000);
            uc.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while ((line = in.readLine()) != null) {
                AllText += line + "\n";
            }
             doc = Jsoup.parse(AllText);
        } catch (IOException e) {
            System.out.println("Proxy timed out..");
           System.out.println("Removed Bad Proxy :"+currentProxy.address()+" :"+proxyList.removeProxy(currentProxy));
            System.out.println(currentLink+" Re-trying..!");
            return fetchArtistData(artist);
        }          //System.out.println(doc.select("div[id=listAlbum]"));
        Elements elements = doc.getAllElements();
        if (elements.size()==0){
            System.out.println("Proxy returned empty result..");
            System.out.println("Removed Bad Proxy :"+currentProxy.address()+" :"+proxyList.removeProxy(currentProxy));
            System.out.println(currentLink+" Re-trying..!");
            return fetchArtistData(artist);
        }
          //  return null;
       elements =   elements.select("div[id=listAlbum]") ;
        if(elements.text().trim().equals("")){
            elements = doc.getAllElements();
            if(elements.size()==0){
                System.out.println("Bad Proxy .. Re-trying..");
                return fetchArtistData(artist);
            }else{
                if(elements.select("img[src=http://images.azlyrics.com/logo.gif]").size()>0){
                    System.out.println(artist.getArtistName()+" has no albums");

                    return elements;
                }else{
                    proxyList.removeProxy(currentProxy);
                    return fetchArtistData(artist);
                }

            }



        }

        else
      return elements.get(0).getAllElements();
    }
}