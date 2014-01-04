package com.xeeapps.main;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.MySqlPlatform;
import com.xeeapps.indexers.LyricsIndexer;
import com.xeeapps.indexers.LyricsIndexer2;
import com.xeeapps.models.Album;
import com.xeeapps.models.Artist;
import com.xeeapps.models.Lyric;
import com.xeeapps.models.Song;
import com.xeeapps.proxies.ProxyList;
import com.xeeapps.proxies.ProxyList2;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Khafaga
 * Date: 22/08/13
 * Time: 04:34 ص
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {



//        ArtistsIndexer artistsIndexer = new ArtistsIndexer();
//        artistsIndexer.index();
//
        ServerConfig config = new ServerConfig();
        config.setName("mysql1");
        DataSourceConfig mmysqlDb = new DataSourceConfig();
        mmysqlDb.setDriver("com.mysql.jdbc.Driver");
        mmysqlDb.setUsername("root");
        mmysqlDb.setPassword("KaydenKross28189");
        mmysqlDb.setUrl("jdbc:mysql://127.0.0.1:3306/lyrics_db");
//        mmysqlDb.setHeartbeatSql("select count(*) from t_one");

        config.setDataSourceConfig(mmysqlDb);
        config.setDdlGenerate(false);
        config.setDdlRun(false);
        config.setDefaultServer(true);
//        config.setRegister(false);
        config.setDatabasePlatform(new MySqlPlatform()
        );

        config.addClass(Album.class);
        config.addClass(Artist.class);
        config.addClass(Lyric.class);
        config.addClass(Song.class);
        EbeanServer server = EbeanServerFactory.create(config);

//        Ebean.getServer(null);
        if (args[1].equalsIgnoreCase("lyrics")){
            ProxyList proxyList = ProxyList.getInstance(args[0]);
            LyricsIndexer lyricsIndexer = new LyricsIndexer(proxyList);
            lyricsIndexer.index(args[2]);
        } else {

            ProxyList2 proxyList2 = ProxyList2.getInstance(args[0]);
            LyricsIndexer2 lyricsIndexer2 = new LyricsIndexer2(proxyList2);
            lyricsIndexer2.index(args[2]);
//            System.out.println("Proxy List: "+proxyList2);
        }




    }
}
