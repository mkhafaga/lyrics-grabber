package com.xeeapps.indexers;

import com.avaje.ebean.Ebean;
import com.xeeapps.models.Artist;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 22/08/13
 * Time: 06:31 ص
 * To change this template use File | Settings | File Templates.
 */
public class ArtistsIndexer {

    public ArtistsIndexer() throws IOException {

    }

    public void index() throws UnsupportedEncodingException {
        //98
        char a = 'y';  //123
//       for (int i = a; i < 123; i++, a++) {

            Document doc = null;
            //http://proxy.primeoptic.net/browse.php?u=http://www.azlyrics.com/a.html
            try {            //http://www.webproxy.ca/browse.php?u=http://www.azlyrics.com/a.html&b=28&f=norefer
              //  ProxyList.getAProxy();
                doc = Jsoup.connect("http://www.azlyrics.com/" + a + ".html").ignoreContentType(true).get();
            } catch (IOException e) {
               // e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                System.out.println("Artists for "+a+" has not been accomplished.");
               // continue;
            }
//                System.out.println(doc.body().text());
            //  Elements links = doc.select("a[href^="+a+"]"); // a with href
            Elements links = doc.select("div[class^=artists]");
            for (Element link : links) {
                Elements children = link.select("a[href]");
                for (Element current : children) {
                    String artistName = current.text();
                    System.out.println(artistName);
                    //   System.out.println(current.text());
                    Artist artist = new Artist();
                    artist.setArtistName(artistName);
                    artist.setHtmlPage(URLDecoder.decode(current.attr("href"), "utf8"));
                    Ebean.save(artist);
                }
            }





    }
}
