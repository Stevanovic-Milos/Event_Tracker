# Uputstvo za setup Angulara i Spring Boot-a sa MySQL serverom

## Instalacija Node.js

Potrebno je instalirati Node.js LTS verziju (LTS verzija se uvek preferira u kodiranju zbog dužeg veka održavanja i izostanka „breaking changes“ u periodu od 5 godina, zavisno od ponude LTS-a, ali je uvek duže od standardne verzije). Trenutno najnovija LTS verzija Node.js-a je 22.17.0. Potrebno je otići na sledeći link: [https://nodejs.org/en/download](https://nodejs.org/en/download). Na stranici odaberite ovu verziju i preuzmite .msi (samo za Windows) ili .exe fajl. Nakon preuzimanja, instalirajte fajl i dajte sve potrebne dozvole.

Nakon instalacije, otvorite terminal. Za Windows terminal preporučujem instalaciju Windows Terminala preko Microsoft Store-a jer je mnogo lakši za upotrebu od samog PowerShell-a, iako je PowerShell engine u pozadini. Pokrenite sledeće komande:

- `node -v` (rezultat mora biti **22.17.0** ako je uspešno instalirano)
- `npm -v` (rezultat bi trebao biti **10.9.2**)

Bitno je da se ove verzije podudaraju sa navedenim verzijama kako bi se izbegle greške zbog nekompatibilnosti okruženja.

## Instalacija Angulara

Za instalaciju Angulara, pokrenite sledeću komandu u terminalu:

```bash
npm install -g @angular/cli
```

Ova komanda omogućava generisanje Angular projekta unutar naše Spring aplikacije, koji će biti naš front-end. Da biste proverili da li je sve ispravno instalirano, pokrenite:

```bash
ng v
```

Očekivani rezultat je tekst sa natpisom **ANGULAR CLI** i ispod njega:

```
@angular-devkit/architect    0.2000.5 (cli-only)
@angular-devkit/core         20.0.5 (cli-only)
@angular-devkit/schematics   20.0.5 (cli-only)
@schematics/angular          20.0.5 (cli-only)
```

Ako je ovo rezultat, Angular je uspešno instaliran i možemo nastaviti.

## Instalacija Jave

Proverite da li je Java instalirana na uređaju unosom sledeće komande u terminalu:

```bash
java -version
```

Očekivani rezultat je nešto poput:

```
java version "21.0.7" 2025-04-15 LTS
Java(TM) SE Runtime Environment (build 21.0.7+8-LTS-245)
Java HotSpot(TM) 64-Bit Server VM (build 21.0.7+8-LTS-245, mixed mode, sharing)
```

Ako je verzija Jave različita od **21.0.7**, potrebno je deinstalirati trenutnu verziju. Idite na:

**Control Panel > Programs > Programs and Features**

Pronađite verziju Jave koju imate (možete pretražiti „Java“ u polju za pretragu gore desno), kliknite dva puta i prihvatite deinstalaciju programa. Nakon toga, vratite se u terminal i ponovo pokrenite:

```bash
java -version
```

Očekivani rezultat je:

```
java: The term 'java' is not recognized as a name of a cmdlet, function, script file, or executable program.
Check the spelling of the name, or if a path was included, verify that the path is correct and try again.
```

Ako dobijete ovu poruku, uspešno ste deinstalirali Javu. Sada je potrebno preuzeti i instalirati verziju 21.0.7. Kopirajte sledeći link:

[https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe](https://download.oracle.com/java/21/latest/jdk-21_windows-x64_bin.exe)

Ovaj link automatski preuzima potrebnu verziju Jave. Instalirajte je, ostavite podrazumevanu lokaciju: **C:\Program Files\Java\jdk-21**.

Nakon instalacije, u start meniju ukucajte „environment“ i odaberite **Edit environment variables for your account**. Otvoriće se prozor. Na dnu desno kliknite na dugme **Environment Variables**. Otvoriće se prozor sa dva taba: **User variables** i **System variables**. U **System variables** proverite da li postoji **JAVA_HOME**. Ako ne postoji, kliknite na **New** ispod **System variables**, unesite:

- **Variable name**: JAVA_HOME
- **Variable value**: C:\Program Files\Java\jdk-21

Kliknite **OK**, zatvorite sve prozore, i u terminalu pokrenite:

```bash
java -version
```

Očekivani rezultat je:

```
java version "21.0.7" 2025-04-15 LTS
Java(TM) SE Runtime Environment (build 21.0.7+8-LTS-245)
Java HotSpot(TM) 64-Bit Server VM (build 21.0.7+8-LTS-245, mixed mode, sharing)
```

Ako je ovo rezultat, Java je uspešno instalirana.

## Instalacija Postmana

Postman je alat za testiranje rada i funkcionalnosti API poziva. Nije neophodan, ali je veoma koristan jer omogućava uvid u odgovore API-ja na određene zahteve, što je korisno za debagovanje. Preuzmite i instalirajte Postman sa sledeće adrese:

[https://www.postman.com/downloads/](https://www.postman.com/downloads/)

Instalacija je intuitivna i ne zahteva dodatne alate.

## Setup Spring Boot-a

Idite na sledeću adresu: [https://start.spring.io/](https://start.spring.io/) i podesite sledeće:

- **Project**: Maven
- **Language**: Java
- **Spring Boot**: 3.5.3
- **Project Metadata**:
  - **Group**: com.stevanovicm
  - **Artifact**: Event_Tracker
  - **Name**: Event_Tracker
  - **Description**: Event Tracker is a web application for managing and tracking events.
  - **Package name**: com.stevanovicm.Event_Tracker
  - **Packaging**: jar
  - **Java**: 21

Sa desne strane, u polju **Dependencies**, kliknite na **Add Dependency** i dodajte:

- MySQL Driver (MySQL JDBC driver)
- Spring Web
- Spring Data JPA

Klikom na **Generate** preuzima se **Event_Tracker.zip** sa podešenim Spring-om.

Za bolju organizaciju, u folderu **Documents** napravite folder **Noop** i u njega raspakujte preuzeti **Event_Tracker** fajl. Pokrenite projekat tako što ćete otvoriti folder **C:\Users\[username]\OneDrive\Documents\Noop\Event_Tracker**, desni klik na **pom.xml**, odabrati **Open in Terminal**, i u terminalu ukucati:

```bash
idea .
```

Ovo otvara folder u IntelliJ IDEA programu. IntelliJ bi trebao automatski rešiti sve Maven dependencije. Otvorite **src/main** i pronađite **Main.java** fajl. Gore desno, ako SDK nije podešen, kliknite i odaberite **Java 21 SDK**. IntelliJ će završiti ostalo.

## Konekcija sa bazom

Preuzmite MySQL Workbench sa sledećeg linka:

[https://dev.mysql.com/downloads/file/?id=541637](https://dev.mysql.com/downloads/file/?id=541637)

Preuzima se najnovija verzija MySQL Workbench-a. Nakon preuzimanja, pokrenite instalaciju. Odaberite **Custom** instalaciju radi veće kontrole. Proširite sekciju sa MySQL serverom, Workbench-om i Shell-om. Kada dođete do x64 fajlova, kliknite na zelenu strelicu koja pokazuje desno da ih dodate u proizvode za instalaciju. Instaliraćete:

- MySQL Server
- MySQL Workbench (UI kontrola nad bazom)
- MySQL Shell (CLI kontrola nad bazom)

Kliknite **Next**, zatim **Execute**, i sačekajte da se preuzmu fajlovi. Nastavite sa **Next** do kraja, ostavite podrazumevane postavke. Port bi trebao biti **3306**. Kada dođete do postavke lozinke, postavite lozinku na **FtnCacak2025***. Sve ostalo ostavite podrazumevano i nastavite do kraja instalacije. Na kraju, Workbench i Shell bi trebalo da se pokrenu. U Workbench-u, u tabu **MySQL Connections**, videćete konekciju **Local instance MySQL80**, što znači da je MySQL uspešno instaliran.

Dodajte MySQL u **Path** promenljivu. U start meniju ukucajte „environment“, odaberite **Edit system environment variables**, kliknite na **Environment Variables**, i u **System variables** pronađite **Path**. Dvaput kliknite na **Path**, kliknite na **New**, i dodajte:

**C:\Program Files\MySQL\MySQL Server 8.0\bin**

Kliknite **OK** svuda i zatvorite prozore. U terminalu pokrenite:

```bash
mysql --version
```

Očekivani rezultat je:

```
C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe Ver 8.0.42 for Win64 on x86_64 (MySQL Community Server - GPL)
```

Ako dobijete ovaj rezultat, MySQL je uspešno instaliran. Dalje radimo preko Workbench-a (UI aplikacija).

Pokrenite Workbench, odaberite konekciju **Local instance MySQL80**, unesite korisničko ime **root** i lozinku **FtnCacak2025***. Na serveru kreirajte bazu sledećim komandama:

```sql
CREATE DATABASE EventTrackerdb;
USE EventTrackerdb;
```

Ovim smo kreirali bazu i prešli na nju. Kreiranje i dodavanje tabela radimo kako projekat napreduje.

## Povezivanje baze u Spring Boot-u

Vratite se u IntelliJ na projekat. Otvorite fajl **application.properties** u folderu **resources** i podesite lokalnu MySQL bazu:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/EventTrackerdb
spring.datasource.username=root
spring.datasource.password=FtnCacak2025*
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

Objašnjenje:

- **spring.datasource.url**: JDBC konektor za MySQL sa portom **3306** i bazom **EventTrackerdb**.
- **spring.datasource.username**: Podrazumevano korisničko ime je **root**.
- **spring.datasource.password**: Lozinka postavljena prilikom instalacije MySQL-a (**FtnCacak2025***).
- **spring.jpa.show-sql**: Prikazuje SQL upite u konzoli radi lakšeg debagovanja.
- **spring.jpa.generate-ddl**: Hibernate automatski generiše SQL naredbe za kreiranje/ažuriranje šeme baze.
- **spring.jpa.hibernate.ddl-auto**: Hibernate ažurira šemu baze pri pokretanju aplikacije, dodaje nove tabele i kolone, ali ne briše postojeće.
- **spring.jpa.properties.hibernate.dialect**: Specificira Hibernate dijalekt za MySQL 8, optimizovan za njegove karakteristike.

Sa ovim je Spring Boot povezan sa MySQL serverom. Kreirajte novu Java klasu u folderu sa **Main.java** pod nazivom **Event** za testiranje konekcije. Pošto nismo dodali Lombok u početnom setup-u, otvorite **pom.xml**, kraj **<dependencies>** taba kliknite na **Add Starters**, potražite **Lombok**, odaberite ga i kliknite **OK**. Sačekajte da IntelliJ doda dependenciju. Lombok eliminiše potrebu za ručnim pisanjem getera i setera korišćenjem anotacije **@Data**.

Klasa **Event** izgleda ovako:

```java
package com.stevanovicm.Event_Tracker;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// Označava da je ova klasa JPA entitet (mapira se na tabelu u bazi).
@Entity
// Nema potrebe za geterima i seterima, Lombok automatski generiše get i set metode.
@Data
// Eksplicitno definiše naziv tabele u bazi (u ovom slučaju EVENTS).
@Table(name = "EVENTS")
public class Event {

    // Deklarisali smo kolonu ID tabele, identifikator @Id automatski ga čini jedinstvenim.
    @Id
    @Column(name = "ID")
    private int id;

    // Deklarisali smo kolonu za naziv eventa.
    @Column(name = "EVENTNAME")
    private String eventName;
}
```

Ovo je klasičan primer Spring Boot klase za rad sa entitetima tabele. Pokrenite aplikaciju, i tabela **EVENTS** sa kolonama **ID** i **EVENTNAME** pojaviće se u Workbench-u (potrebno je osvežiti prikaz). Ovim je MySQL uspešno povezan sa Spring Boot-om.

## Kreiranje front-end-a i konekcija

Navigirajte u folder **Event_Tracker** i otvorite terminal. Unesite sledeću komandu:

```bash
ng new Event_Tracker_Front
```

Odaberite sledeće opcije:

- **Zoneless app**: No
- **Stylesheet**: Sass (SCSS)
- **SSR**: No

Ovo kreira Angular projekat unutar glavnog foldera i instalira npm pakete. Angular je kreiran sa podrazumevanom konfiguracijom, ali još nije povezan sa back-end-om. Da bi se front-end i back-end povezali, front-end mora pozivati API endpoint back-end-a i preuzimati podatke iz responsa. Za sada, pokrenite u terminalu:

```bash
npm start
```

ili

```bash
ng serve
```

U terminalu ćete dobiti link, klikom na koji se otvara Angular front-end. Ako je sve uspešno, prelazimo na implementaciju konekcije.

## Konekcija back-end-a i front-end-a

### Back-end deo

Kreirajte četiri foldera: **Objectsdb**, **Controllers**, **Repositorys**, **Services**. U **Objectsdb** folderu nalaziće se entiteti (npr. **Event** klasa). U **Controllers** folderu kreirajte **EventController**:

```java
package com.stevanovicm.Event_Tracker.Controllers;

import com.stevanovicm.Event_Tracker.Objectsdb.Event;
import com.stevanovicm.Event_Tracker.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

// Označava da je ova klasa Spring REST kontroler.
@RestController
// Deklarisemo osnovni URL.
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    // Dependency injection, povezujemo se sa servisom koji ima sve operacije.
    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // Vraća listu svih eventova.
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Vraća event na osnovu ID-a.
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Kreira novi event.
    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    // Ažurira postojeći event.
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @RequestBody Event event) {
        Optional<Event> updatedEvent = eventService.updateEvent(id, event);
        return updatedEvent.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Briše event.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        boolean deleted = eventService.deleteEvent(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
```

Kontroler koristi **EventService** za komunikaciju sa bazom i šalje odgovore na URL **/api/events**. Sledeći je **EventService**:

```java
package com.stevanovicm.Event_Tracker.Services;

import com.stevanovicm.Event_Tracker.Repositorys.EventRepository;
import com.stevanovicm.Event_Tracker.Objectsdb.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Označava da je ova klasa Spring Bean.
@Service
public class EventService {
    private final EventRepository eventRepository;

    // Spring automatski ubacuje implementaciju povezanu sa repozitorijumom.
    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Vraća sve evente.
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Nalazi event na osnovu ID-a.
    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    // Kreira novi event.
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Ažurira postojeći event na osnovu ID-a.
    public Optional<Event> updateEvent(Integer id, Event updatedEvent) {
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();
            event.setEventName(updatedEvent.getEventName());
            return Optional.of(eventRepository.save(event));
        }
        return Optional.empty();
    }

    // Briše event.
    public boolean deleteEvent(Integer id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
```

**EventRepository** izgleda ovako:

```java
package com.stevanovicm.Event_Tracker.Repositorys;

import com.stevanovicm.Event_Tracker.Objectsdb.Event;
import org.springframework.data.jpa.repository.JpaRepository;

// Apstrakcija između servisa i baze podataka.
public interface EventRepository extends JpaRepository<Event, Integer> {
}
```

Pokrenite server (**localhost:8080**) i testirajte API endpoint **localhost:8080/api/events** preko Postmana.

### Front-end deo

Otvorite Angular projekat u VS Code-u. Kreirajte komponentu **home**:

```bash
ng g c home
```

Ovo kreira folder **home** sa fajlovima **home.component.ts**, **home.component.html**, i **home.component.scss**. U **home.component.ts** koristimo servis **api.service.ts** (kreiran u folderu **home**) za pozive na back-end API. Servis se subskrajbuje na odgovore, a dobijeni podaci (niz eventova) se smeštaju u niz **events** i prikazuju u **home.component.html** koristeći Angularove direktive poput ***ngIf** i ***ngFor** (omogućene uvozom **CommonModule**).

U **app.component.html** se nalazi samo **<router-outlet>**, koji je importovan u **app.component.ts**. Ruter u **app.config.ts** definiše rutu **/home** koja prikazuje **home** komponentu. U **app.config.ts** su navedeni **providers** za **Router** i **HttpClient** (za HTTP pozive). Modeli se nalaze u folderu **Models** kao TypeScript interfejsi radi bolje definicije tipova.

Pokrenite back-end, zatim front-end (**ng serve**). Pristupite adresi **/home**, gde će se prikazati eventovi iz MySQL tabele. Ovim je konekcija baze, back-end-a i front-end-a uspešno završena.

