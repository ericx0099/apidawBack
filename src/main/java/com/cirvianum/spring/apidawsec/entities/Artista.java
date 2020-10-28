package com.cirvianum.spring.apidawsec.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String membres;
    private String urlPhoto;
    @OneToMany(cascade = CascadeType.ALL)
    List<Event> events = new ArrayList<Event>();

    public Artista() {
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getMembres() {
        return membres;
    }

    public void setMembres(String membres) {
        this.membres = membres;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getParticipants() {
        return membres;
    }

    public void setParticipants(String participants) {
        this.membres = participants;
    }
}
