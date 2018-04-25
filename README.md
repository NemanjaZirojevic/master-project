# Master projekat 
Implementacija programa za pregled cena akcija i ulaganja na berzi.
Tehnologije koriscenje za izradu projekta : 
Reactive Spring Boot + Spring Security + MongoDB + MySQL + Hibernate + Thymeleaf + Bootstrap css +Jquery

## Pokretanje aplikacije 
Za pokretanje aplikacije neophodno je pokretanje instance MongoDB i MySQL baze sa korisnickim privilegijama definisanim 
u application.properties fajlu.
## Stranica za logovanje
![login](https://user-images.githubusercontent.com/35013838/39249233-7ac00058-489e-11e8-9ca4-b926e69e6b0a.png)
## Stranica za registraciju novih korisnika
![register](https://user-images.githubusercontent.com/35013838/39249455-01ba45fa-489f-11e8-9de9-7bd94a80009f.png)
## izgled pocetne stranice
![main](https://user-images.githubusercontent.com/35013838/39249511-1b7cba0e-489f-11e8-9e82-d2797cb07786.png)
### Opis rada aplikacije :
Korisnik moze da izabere jednu od ponudjenih kompanija (Google,Amazon,Ebay,Yahoo),nakon cega se interaktivno prikazuje real-time grafikon 
sa cenama akcija.
Unosom predvidjene cene akcija,vremena i iznosa koji ulaze,nakon isteka vremena koje je unio ukoliko je cena akcija kompanije u tom
trenutku veca od predvidjenje cene koju je korisnik predvidio iznos koji je korisnik ulozio se uvecanja za 90%,a u suprotnom se smanjuje za 90%.

