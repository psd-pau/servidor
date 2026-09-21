# Context de l'assignatura

Aquest mòdul introdueix l’alumnat en el desenvolupament d’aplicacions web en entorn servidor dins el cicle de **Desenvolupament d'Aplicacions Web (DAW)**.

L’assignatura té un enfocament eminentment **pràctic**, però amb un fort èmfasi en l’estructura i l’arquitectura del programari. L’objectiu no és només que l’alumnat sigui capaç de fer que una aplicació funcioni, sinó que aprengui a construir aplicacions **ben organitzades, mantenibles i coherents arquitectònicament**.

El desenvolupament del curs es realitza utilitzant **Java i l’ecosistema Spring**, evitant barrejar diferents frameworks de servidor per tal de consolidar una forma clara de treballar.

---

# Filosofia pedagògica

El curs prioritza la comprensió de **com s’organitza una aplicació web** per damunt de l’aprenentatge de funcionalitats concretes d’un framework.

Molts cursos de programació en servidor es limiten a introduir eines i tecnologies de manera successiva. En canvi, aquest mòdul intenta que l’alumnat entengui **com estructurar correctament una aplicació backend**.

Aquest enfocament ajuda els estudiants a desenvolupar una manera de pensar transferible a altres frameworks i llenguatges.

---

# Arquitectura utilitzada

Les aplicacions desenvolupades durant el curs segueixen una arquitectura per capes que pot considerar-se una **Clean Architecture light aplicada a Spring**.

La separació de responsabilitats es basa en l’estructura següent:
domain
↓
repository
↓
service
↓
controller
↓
view / API

Cada capa té una responsabilitat concreta dins el sistema.

**Domain**

Representa el model conceptual del problema. Inclou entitats i altres elements relacionats amb el domini de l’aplicació.

**Repository**

Gestiona l’accés a dades i la persistència. Les consultes han de retornar exactament la informació necessària per al cas d’ús corresponent.

**Service**

Implementa la lògica de negoci i coordina les operacions de l’aplicació.

**Controller**

Gestiona les peticions HTTP i delega el treball a la capa de serveis. No ha de contenir lògica de negoci.

**View / API**

S’encarrega de presentar la informació a l’usuari o a clients externs.

---

# Principis de desenvolupament treballats

Durant el curs es reforcen diversos principis de desenvolupament que ajuden a construir aplicacions més clares i mantenibles.

## Separació de responsabilitats

Cada component del sistema ha de tenir una funció clara i limitada. Aquesta separació facilita la comprensió del codi i la seva evolució.

## Precisió en l’accés a dades

Les consultes al repositori han de retornar **exactament la informació necessària**, evitant recuperar dades innecessàries o filtrar-les en capes superiors.

Aquest principi es resumeix sovint així:

> la consulta ha de retornar exactament el que es demana, ni una fila més ni una menys.

## Lògica de negoci als serveis

La lògica de negoci es concentra a la capa de serveis. Els controladors s’encarreguen únicament de gestionar la comunicació HTTP.

## Simplicitat de la vista

Les plantilles Thymeleaf contenen **lògica mínima**. Normalment només s’utilitzen estructures simples com `th:each` per mostrar dades.

## Ús de DTOs

Les dades exposades a controladors i APIs es transmeten mitjançant **DTOs (Data Transfer Objects)** per evitar exposar directament les entitats del domini.

Quan és necessari s’utilitzen **mappers** per transformar entre entitats i DTOs.

---

# Pràctiques professionals introduïdes al curs

A més dels conceptes del currículum oficial, el curs introdueix algunes pràctiques habituals en el desenvolupament backend professional.

## Pensament orientat a casos d'ús

Els serveis no es plantegen com simples contenidors d’operacions CRUD. En canvi, s’intenta que representin **casos d’ús reals de l’aplicació**.

Per exemple:
createOrder
cancelOrder
getUserOrders

en lloc de limitar-se a operacions genèriques sobre entitats.

Aquest enfocament ajuda a relacionar la lògica del sistema amb les operacions del domini.

## Distinció entre domini i capa d'aplicació

Es reforça la distinció entre el **model de domini** i la **capa d'aplicació**.

El directori `domain` representa el model del problema, mentre que la capa `service` implementa els casos d'ús de l'aplicació coordinant repositoris i altres components.

Això evita que els serveis es converteixin en simples contenidors de mètodes CRUD sense significat funcional.

## Logging estructurat

S’introdueix l’ús de sistemes de logging utilitzant **SLF4J** en lloc de missatges de consola.

Es presenten els nivells principals de logging:

- INFO
- WARN
- ERROR

L’objectiu és acostumar l’alumnat a utilitzar mecanismes de diagnòstic adequats.

## Introducció a les proves

Es fa una introducció bàsica a les proves automatitzades centrada en la **capa de serveis**, que és on resideix la lògica de negoci.

Això permet mostrar que el comportament del sistema es pot verificar sense dependre de la capa HTTP.

---

# Seguretat

La seguretat es tracta com un **aspecte transversal** de l’aplicació.

Durant el curs s’introdueixen conceptes bàsics com:

- autenticació d’usuaris
- control d’accés
- validació de dades d’entrada
- gestió adequada d’errors

L’objectiu és que l’alumnat entengui que la seguretat forma part del disseny de l’aplicació i no és un element que s’afegeix posteriorment.

---

# Objectiu final del curs

L’objectiu del mòdul és que l’alumnat sigui capaç de desenvolupar aplicacions web funcionals però sobretot **ben estructurades**.

Més enllà de dominar un framework concret, el curs pretén transmetre una forma de pensar el desenvolupament backend basada en:

- separació clara de responsabilitats
- arquitectura coherent
- mantenibilitat del codi
- bones pràctiques professionals