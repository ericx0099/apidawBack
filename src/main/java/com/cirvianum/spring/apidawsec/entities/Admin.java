package com.cirvianum.spring.apidawsec.entities;


import javax.persistence.Entity;

@Entity
public class Admin extends Persona {

    public Admin() {
        super();
    }

/*    @OneToMany
    private List<Event> eventsCreats;

    public List<Event> getEventsCreats() {
        return eventsCreats;
    }

    public void setEventsCreats(List<Event> eventsCreats) {
        this.eventsCreats = eventsCreats;
    }*/


}
