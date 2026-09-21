# DAW — Programació en entorn servidor

Aquest repositori conté el material d'un mòdul de **Programació en entorn servidor** del cicle formatiu de **Desenvolupament d'Aplicacions Web (DAW)**.

El curs té un enfocament eminentment **pràctic**, centrat en el desenvolupament d'aplicacions web utilitzant **Java i l'ecosistema Spring**. A més d'implementar funcionalitats, l'objectiu principal és que l'alumnat aprengui a construir aplicacions **ben estructurades i mantenibles**.

---

# Filosofia del curs

El desenvolupament del curs posa un èmfasi especial en les **bones pràctiques d'arquitectura** i en la separació clara de responsabilitats dins d'una aplicació web.

Tot i que no s'introdueixen formalment tots els patrons de disseny utilitzats per Spring, l'alumnat treballa amb una arquitectura que pot considerar-se una **Clean Architecture light aplicada a Spring**.

Aquesta arquitectura es basa en una separació clara de capes:

domain
↓
repository
↓
service
↓
controller
↓
view / API


Principis fonamentals que es treballen durant el curs:

- separació de responsabilitats
- estructura clara del projecte
- mantenibilitat del codi
- reutilització de components
- control de la lògica de negoci

Un principi central del curs és:

> cada capa ha de fer només la seva feina.

En particular:

- el **repository** retorna exactament les dades necessàries
- el **service** conté la lògica de negoci
- el **controller** orquestra la petició HTTP
- la **vista** només presenta informació

---

# Tecnologies utilitzades

El curs es desenvolupa íntegrament dins l'ecosistema **Spring**.

Principals tecnologies utilitzades:

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- REST APIs
- Thymeleaf
- DTOs i mappers

També es treballen aspectes transversals com:

- gestió d'excepcions amb `@ControllerAdvice`
- separació entre model de domini i representació externa
- bones pràctiques d'accés a dades
- seguretat bàsica en aplicacions web

---

# Organització del repositori

El repositori està estructurat en diversos directoris que reflecteixen els diferents tipus de materials del curs.


00_general
01_conceptes
02_practiques
03_projectes
04_avaluacio


## 00_general

Conté el context general del mòdul i els documents curriculars principals.

Inclou:

- context de l'assignatura
- resultats d'aprenentatge i criteris d'avaluació
- relació entre unitats i resultats d'aprenentatge
- continguts oficials del mòdul

---

## 01_conceptes

Material conceptual utilitzat per introduir els diferents blocs tecnològics del curs.

Inclou explicacions sobre:

- arquitectura web
- funcionament del model client-servidor
- accés a dades
- serveis web
- arquitectura d'aplicacions Spring

---

## 02_practiques

Exercicis pràctics guiats per consolidar els conceptes del curs.

Les pràctiques estan orientades a:

- entendre el funcionament dels diferents components de l'arquitectura
- aplicar bones pràctiques de desenvolupament
- familiaritzar-se amb l'ecosistema Spring

---

## 03_projectes

Projectes més complets que integren diversos conceptes del curs.

L'objectiu és que l'alumnat sigui capaç de desenvolupar una aplicació web amb:

- estructura clara
- separació de capes
- accés a dades
- APIs
- interfície web

---

## 04_avaluacio

Materials relacionats amb l'avaluació del mòdul.

Inclou:

- criteris d'avaluació
- rúbriques
- descripció de projectes avaluables
- altres instruments d'avaluació.

---

# Objectiu final del curs

L'objectiu del mòdul és que l'alumnat sigui capaç de desenvolupar aplicacions web funcionals, però sobretot que entengui **com estructurar correctament una aplicació en entorn servidor**.

Més enllà de dominar un framework concret, el curs pretén transmetre una forma de pensar l'arquitectura del programari que sigui aplicable a altres tecnologies i entorns.


## Additional Documentation

- Course pedagogical context → `CONTEXT_ASSIGNATURA.md`
- Learning outcomes (RA/CA) → `RA_CA.md`
- Curriculum contents → `CONTINGUTS.md`
- Mapping units to learning outcomes → `MAPA_RA_UNITATS.md`


