package com.xeeapps.models;
// Generated Nov 10, 2013 9:52:17 PM by Hibernate Tools 3.2.1.GA


import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Album generated by hbm2java
 */
@Entity
@Table(name="album"
    ,catalog="lyrics_db"
)
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.FIELD)
public class Album  implements java.io.Serializable {


     private Long id;
     private Artist artist;
     private String albumName;
     @XmlTransient
     private Set<Song> songs = new HashSet<Song>(0);

    public Album() {
    }

    public Album(Artist artist, String albumName, Set<Song> songs) {
       this.artist = artist;
       this.albumName = albumName;
       this.songs = songs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="artist_id")
    public Artist getArtist() {
        return this.artist;
    }
    
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    
    @Column(name="album_name")
    public String getAlbumName() {
        return this.albumName;
    }
    
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="album")
    public Set<Song> getSongs() {
        return this.songs;
    }
    
    public void setSongs(Set<Song> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return albumName; //To change body of generated methods, choose Tools | Templates.
    }




}


