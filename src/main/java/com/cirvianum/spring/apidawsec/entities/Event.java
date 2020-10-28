package com.cirvianum.spring.apidawsec.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//import jdk.jfr.Timestamp;

@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvent;

    private int idArtista;
    private float preu;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    private LocalDateTime dataInici;

    @ElementCollection
    private Map<Integer, Integer> puntuacions = new HashMap<Integer, Integer>();
    private Duration duracio;
    @OneToOne
    private Ubicacio ubicacio;

    public Event() {
    }

    public Map<Integer, Integer> getPuntuacions() {
        return puntuacions;
    }


    public void putPuntuacions(int idClient, int puntuacio) {
        if (!this.puntuacions.containsKey(idClient)) {
            this.puntuacions.put(idClient, puntuacio);
        } else {
            this.puntuacions.replace(idClient, puntuacio);
        }
    }

    public void setPuntuacions(Map<Integer, Integer> puntuacions) {
        this.puntuacions = puntuacions;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public LocalDateTime getDataInici() {
        return dataInici;
    }

    public void setDataInici(LocalDateTime dataInici) {
        this.dataInici = dataInici;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }


    public Duration getDuracio() {
        return duracio;
    }

    public void setDuracio(Duration duracio) {
        this.duracio = duracio;
    }

    public Ubicacio getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(Ubicacio ubicacio) {
        this.ubicacio = ubicacio;
    }
}
