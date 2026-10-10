---
lang: ca-ES
---

# UT2 — Diagrama de les peces de Spring Data JPA

## Procedència i inventari

**Imatge aportada per David Pons el 2026-10-09** com a suport addicional per visualitzar les peces de persistència. Fitxer: [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/spring-jpa-diagram.png|`materials/0613-desenvolupament-web-entorn-servidor/UT2/spring-jpa-diagram.png`]]. No s'ha indicat l'autoria original ni una URL de procedència; l'aportació del professor no s'interpreta com una atribució de creació.

| Característica | Valor observat |
|---|---|
| Format | PNG, RGBA de 8 bits, no entrellaçat |
| Dimensions | 454 × 536 píxels; composició vertical |
| Llengua dels rètols | Anglès |
| Estat | Imatge original conservada sense modificacions |

## Contingut examinat

La imatge presenta `SPRING BOOT APPLICATION` a la part superior; dins un requadre central hi ha les franges `SPRING DATA JPA`, `JAVA PERSISTENCE API`, `HIBERNATE` i `JDBC`. A la part inferior, un cilindre `RDBMS` representa el gestor de base de dades. Les fletxes bidireccionals connecten l'aplicació amb Spring Data JPA i amb JPA, i el conjunt central amb la base de dades.

El diagrama ajuda a relacionar repositoris, API de persistència, implementació ORM, accés JDBC i gestor relacional. La lectura docent es manté coherent amb les distincions dels apunts: JPA és l'especificació/API i Hibernate n'és la implementació. La representació per franges és un esquema de responsabilitats, no una seqüència obligatòria de crides a cinc biblioteques independents. La connexió directa a JPA mostra una possibilitat d'ús; el fil de la unitat empra repositoris Spring Data JPA.

## Incorporació als materials d'alumnat

S'ha inserit als [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/acces-a-dades-amb-spring.md|apunts d'accés a dades]], a l'apartat **1.4. Com encaixen JPA, Hibernate i Spring Data JPA?**, després de la taula de funcions. El Markdown usa una ruta relativa, text alternatiu en català i el peu «Figura 1. Relació entre les peces de persistència d'una aplicació Spring Boot».

El text de lectura explica `JDBC` i `RDBMS`, identifica H2 com el gestor de l'exemple i relaciona el rètol històric `JAVA PERSISTENCE API` amb el nom actual Jakarta Persistence i els imports `jakarta.persistence`. Els rètols originals en anglès es conserven. El [[../../materials/0613-desenvolupament-web-entorn-servidor/UT2/README.md|README local]] recull el recurs per preservar-ne la vinculació. El 2026-10-10 s'ha incrustat al DOCX dels apunts i al PDF exportat d'aquest document, conservant les proporcions i revisant visualment els rètols.

**Traçabilitat docent orientativa:** suport visual per a `RA6.a`, identificació de les tecnologies d'accés a bases de dades. Font curricular: `raw/sources/2526_Servidor-master/00_general/RA.md`, apartat RA6. La incorporació de la imatge no acredita una activitat impartida ni una evidència d'avaluació.

La [[../moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut2-acces-dades|fitxa d'UT2]] enllaça aquest suport. Les fonts tècniques que sustenten l'explicació de les peces es conserven a la [[code-along-ut2-2526|fitxa del code along de referència]].
