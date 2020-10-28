package com.cirvianum.spring.apidawsec.rest;

import com.cirvianum.spring.apidawsec.dao.*;
import com.cirvianum.spring.apidawsec.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RestController
@RequestMapping("/")
public class CiriviApiRest {
    @Autowired
    private ClientDao clientDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private ArtistaDao artistaDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private UbicacioDao ubicacioDao;

    @Autowired
    private CompraDao compraDao;
    @RequestMapping("/")
    public String index(){
        return "Greetings from SpringBoot";
    }

    @RequestMapping(value="changePhoto/{idC}/{url}", method = RequestMethod.PUT)
    public ResponseEntity<Client> changePhoto(@PathVariable("idC")Integer idC, @PathVariable("url") String url){
        Optional<Client> oClient = clientDao.findById(idC);
        if(oClient.isPresent()){
            String urlReplaced = url.replace("-","/");
            Client client = oClient.get();
            client.setUrlPhoto(urlReplaced);
            clientDao.save(client);
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }


    @RequestMapping(value = "putPuntuacio/{idE}/{idC}/{puntuacio}", method = RequestMethod.PUT)
    public ResponseEntity<Event> putPuntuacio(@PathVariable("idE") Integer idE, @PathVariable("idC") Integer idC, @PathVariable("puntuacio") Integer puntuacio) {

        Optional<Event> oEvent = eventDao.findById(idE);
        Optional<Client> oClient = clientDao.findById(idC);

        if (oClient.isPresent() && oEvent.isPresent()) {
            Event eventFound = oEvent.get();
            eventFound.putPuntuacions(idC, puntuacio);
            eventDao.save(eventFound);
            return ResponseEntity.ok(eventFound);
        }
        return ResponseEntity.notFound().build();


    }


    @RequestMapping(value = "getEventsByArtista/{idA}", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEventsByArtista(@PathVariable("idA") Integer idA) {
        List<Event> events = eventDao.findAll();
        List<Event> eventsDelArtista = new ArrayList<Event>();
        if (!events.isEmpty()) {
            for (Event e : events) {
                if (e.getIdArtista() == idA) {
                    eventsDelArtista.add(e);
                }
            }
        }
        if (!eventsDelArtista.isEmpty()) {
            return ResponseEntity.ok(eventsDelArtista);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "getCompresByClient/{idC}", method = RequestMethod.GET)
    public ResponseEntity<List<Compra>> getCompraById(@PathVariable("idC") Integer idC) {

        Optional<Client> oClient = clientDao.findById(idC);
        if (oClient.isPresent()) {
            Client client = oClient.get();
            return ResponseEntity.ok(client.getTicketsComprats());
        }
        return ResponseEntity.notFound().build();
    }


    @RequestMapping(value = "getClients", method = RequestMethod.GET)
    public ResponseEntity<List<Client>> getClient() {
        List<Client> list = clientDao.findAll();
        if (!list.isEmpty()) {
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.notFound().build();

    }

    @RequestMapping(value = "getClientById/{idC}", method = RequestMethod.GET)
    public ResponseEntity<Client> getClient(@PathVariable("idC") Integer idC) {

        Optional<Client> oClient = clientDao.findById(idC);

        if (oClient.isPresent()) {
            Client clientFound = oClient.get();
            return ResponseEntity.ok(clientFound);
        }
        return ResponseEntity.notFound().build();


    }


    @RequestMapping(value = "addClient/{idUbi}", method = RequestMethod.POST)
    public ResponseEntity<Client> addClient(@PathVariable("idUbi") Integer idUbi, @RequestBody Client client) {
        Optional<Ubicacio> ubiFound = ubicacioDao.findById(idUbi);
        List<Client> clientsActius = clientDao.findAll();
        boolean exists = false;

        for (Client c : clientsActius) {
            if (c.getCorreu().equalsIgnoreCase(client.getCorreu())) {
                exists = true;
                break;
            }
        }
        if (!exists && ubiFound.isPresent()) {
            Ubicacio ubi = ubiFound.get();
            client.setUbicacio(ubi);
            clientDao.save(client);
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "getAdmin", method = RequestMethod.GET)
    public ResponseEntity<List<Admin>> getAdmin() {
        List<Admin> list = adminDao.findAll();
        return ResponseEntity.ok(list);
    }


    @RequestMapping(value = "addAdmin/{idUbi}", method = RequestMethod.POST)
    public ResponseEntity<Admin> addAdmin(@PathVariable("idUbi") Integer idUbi, @RequestBody Admin admin) {
        Optional<Ubicacio> ubiFound = ubicacioDao.findById(idUbi);
        if (ubiFound.isPresent()) {
            Ubicacio ubi = ubiFound.get();
            admin.setUbicacio(ubi);

            adminDao.save(admin);
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "getArtistes", method = RequestMethod.GET)
    public ResponseEntity<List<Artista>> getArtista() {
        List<Artista> list = artistaDao.findAll();
        return ResponseEntity.ok(list);
    }



    @RequestMapping(value = "getArtistById/{idA}", method = RequestMethod.GET)
    public ResponseEntity<Artista> getArtistById(@PathVariable("idA") Integer idA) {
        Optional<Artista> oArtista = artistaDao.findById(idA);
        if (oArtista.isPresent()) {
            Artista artista = oArtista.get();
            return ResponseEntity.ok(artista);
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "addArtista", method = RequestMethod.POST)
    public ResponseEntity<Artista> addArtista(@RequestBody Artista artista) {
        artistaDao.save(artista);
        return ResponseEntity.ok(artista);

    }

    @RequestMapping(value = "getEventbyId/{idE}", method = RequestMethod.GET)
    public ResponseEntity<Event> getEventById(@PathVariable("idE") Integer idE) {
        Optional<Event> oEvent = eventDao.findById(idE);
        if (oEvent.isPresent()) {
            Event eventFound = oEvent.get();
            return ResponseEntity.ok(eventFound);
        }
        return ResponseEntity.notFound().build();


    }

    @RequestMapping(value = "getEvents", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> getEvents() {
        List<Event> oEvents = eventDao.findAll();
        if (!oEvents.isEmpty()) {
            return ResponseEntity.ok(oEvents);
        }
        return ResponseEntity.notFound().build();

    }


    @RequestMapping(value = "createEvent/{idA}/{idU}", method = RequestMethod.POST)
    public ResponseEntity<Event> createEvent(@PathVariable("idA") Integer idA, @PathVariable("idU") Integer idU, @RequestBody Event event) {
        Optional<Artista> oArtista = artistaDao.findById(idA);
        Optional<Ubicacio> oUbicacio = ubicacioDao.findById(idU);
        if (oArtista.isPresent() && oUbicacio.isPresent()) {
            Artista a = oArtista.get();
            Ubicacio u = oUbicacio.get();


            event.setIdArtista(a.getId());
            event.setUbicacio(u);
            Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
            event.setPuntuacions(hm);
            eventDao.save(event);

            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }


    }

    @RequestMapping(value = "createCompra/{idE}/{idC}", method = RequestMethod.POST)
    public ResponseEntity<Compra> createCompra(@PathVariable("idE") Integer idE, @PathVariable("idC") Integer idC, @RequestBody Compra compra) {
        Optional<Event> oEvent = eventDao.findById(idE);
        Optional<Client> oClient = clientDao.findById(idC);

        if (oEvent.isPresent() && oClient.isPresent()) {
            Event e = oEvent.get();
            Client c = oClient.get();

            compra.setEvent(e);
            compra.setIdClient(c.getIdPersona());
            float preuIva = (float) (e.getPreu() + (e.getPreu() * 0.21));
            compra.setPreu(preuIva);
            compraDao.save(compra);
            return ResponseEntity.ok(compra);


        } else {
            return ResponseEntity.notFound().build();
        }
    }

/*
    @RequestMapping(value="addEventToAdmin/{idE}/{idA}", method = RequestMethod.PUT)
    public ResponseEntity<Client> addEventToAdmin(@PathVariable("idE") Integer idE,@PathVariable("idA") Integer idA){
        Optional<Event> evenFound = eventDao.findById(idE);
        Optional<Admin> evenAdmin = adminDao.findById(idA);



    }
*/

    @RequestMapping(value = "addCompraToClient/{idClient}/{idCompra}", method = RequestMethod.PUT)
    public ResponseEntity<Client> addCompraToClient(@PathVariable("idClient") Integer idClient, @PathVariable("idCompra") Integer idCompra) {

        Optional<Compra> compraFound = compraDao.findById(idCompra);

        Optional<Client> clientFound = clientDao.findById(idClient);

        if (compraFound.isPresent() && clientFound.isPresent()) {

            Compra oCompra = compraFound.get();

            Client oClient = clientFound.get();


            oClient.addCompra(oCompra);

            clientDao.save(oClient);


            return ResponseEntity.ok(null);


        } else {
            return ResponseEntity.notFound().build();
        }


    }


    /* @RequestMapping(value ="addEventToArtista/{idArtista}/{idEvent}", method=RequestMethod.PUT)
     public ResponseEntity<Artista> addEventToArtista(@PathVariable("idArtista") Integer idArtista,@PathVariable("idEvent") Integer idEvent){
         Optional<Artista> oArtista = artistaDao.findById(idArtista);
         Optional <Event> oEvent= eventDao.findById(idEvent);

         if(oArtista.isPresent()&&oEvent.isPresent()){
             Artista artistaFound = oArtista.get();
             Event eventFound = oEvent.get();

             artistaFound.getEventsParticipat().add(eventFound);
             artistaDao.save(artistaFound);
             return ResponseEntity.ok(null);

         }else{
             return ResponseEntity.notFound().build();
         }
     }
 */
    @RequestMapping(value = "getUbicacio", method = RequestMethod.GET)
    public ResponseEntity<List<Ubicacio>> getUbicacio() {
        List<Ubicacio> list = ubicacioDao.findAll();
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "addUbicacio", method = RequestMethod.POST)
    public ResponseEntity<Ubicacio> addUbicacio(@RequestBody Ubicacio ubicacio) {
        ubicacioDao.save(ubicacio);
        return ResponseEntity.ok(ubicacio);

    }

    @RequestMapping(value = "delAdmin/{idA}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAdmin(@PathVariable("idA") Integer idA) {
        adminDao.deleteById(idA);

        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "delArtista/{idA}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteArtista(@PathVariable("idA") Integer idA) {
        artistaDao.deleteById(idA);

        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "delClient/{idC}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteClient(@PathVariable("idC") Integer idC) {
        clientDao.deleteById(idC);

        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "delCompra/{idC}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCompra(@PathVariable("idC") Integer idC) {
        compraDao.deleteById(idC);

        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "delEvent/{idE}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteEvent(@PathVariable("idE") Integer idE) {
        eventDao.deleteById(idE);

        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "delUbicacio/{idU}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUbicacio(@PathVariable("idU") Integer idU) {
        ubicacioDao.deleteById(idU);

        return ResponseEntity.ok(null);
    }

}
