package com.xeeapps.models;
// Generated Nov 10, 2013 9:52:17 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Artist generated by hbm2java
 */
@Entity
@Table(name="artist"
    ,catalog="lyrics_db"
)
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Artist  implements java.io.Serializable {


     private Long id;
     private String artistName;
     private String htmlPage;
     @XmlTransient
     private Set<Song> songs = new HashSet<Song>(0);
     @XmlTransient
     private Set<Album> albums = new HashSet<Album>(0);

    public Artist() {
    }

    public Artist(String artistName, String htmlPage, Set<Song> songs, Set<Album> albums) {
       this.artistName = artistName;
       this.htmlPage = htmlPage;
       this.songs = songs;
       this.albums = albums;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Column(name="artist_name")
    public String getArtistName() {
        return this.artistName;
    }
    
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    
    @Column(name="html_page")
    public String getHtmlPage() {
        return this.htmlPage;
    }
    
    public void setHtmlPage(String htmlPage) {
        this.htmlPage = htmlPage;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="artist")
    public Set<Song> getSongs() {
        return this.songs;
    }
    
    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="artist")
    public Set<Album> getAlbums() {
        return this.albums;
    }
    
    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }




}


