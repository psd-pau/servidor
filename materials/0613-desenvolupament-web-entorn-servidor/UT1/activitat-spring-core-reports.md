---
lang: ca-ES
author: David Pons
title: "UT1 — Gestor d'informes amb Spring Core"
---

# UT1 — Gestor d'informes amb Spring Core

**CIFP Pau Casesnoves · IFC33C · Curs 2026–2027**  
**Mòdul:** 0613. Desenvolupament web en entorn servidor  
**Professor:** David Pons

## Objectiu

Implementar una aplicació Spring Boot que generi informes en tres formats: PDF, HTML i CSV. L'activitat aplica els conceptes treballats en el code along: IoC, DI, selecció de dependències, beans, scopes, cicle de vida, inicialització diferida i configuració explícita de beans.


## Punt de partida

Crea un projecte Java amb Spring Boot, Maven i Spring Web. Organitza les classes segons la seva responsabilitat: controladors, serveis, model i configuració. Els controladors han de delegar la lògica als serveis.

Els informes PDF es poden simular amb una cadena identificada com a PDF; no cal generar un fitxer PDF binari. Els informes HTML han de contenir marques HTML senzilles i els CSV han de tenir una estructura CSV real. Pots emprar un contingut fix de demostració, sense formularis ni base de dades.

## Requisits

### 1. Interfície i generadors

Defineix la interfície `ReportGenerator` i implementa tres versions gestionades per Spring:

- `PdfReportGenerator`.
- `HtmlReportGenerator`.
- `CsvReportGenerator`.

Totes han de poder generar un informe a partir del seu ID i del contingut. Marca el generador PDF amb `@Primary` perquè sigui l'opció per defecte quan s'injecti un `ReportGenerator` sense qualificador. Selecciona explícitament els generadors HTML i CSV amb `@Qualifier` als punts d'injecció corresponents.

Les rutes de generació han de fer servir aquestes dependències injectades: s'ha de poder observar tant la selecció per defecte com les seleccions explícites.

### 2. Servei de generació i dependència opcional

Implementa `ReportService` i organitza les variants necessàries per als tres formats:

- Injecta les dependències obligatòries per constructor, inclosos els generadors.
- Injecta `WatermarkService` com a dependència opcional per setter.
- El mètode `generateReport()` ha de crear el treball, preparar el contingut, generar l'informe, desar-lo a la memòria cau i actualitzar les estadístiques.

Si hi ha `WatermarkService`, afegeix una marca d'aigua textual al contingut abans de passar-lo al generador. Així la marca també quedarà dins el camp de contingut del CSV i no trencarà la seva estructura.

L'aplicació ha de poder arrencar i generar informes sense el bean `WatermarkService`. Comprova el comportament amb aquest bean i sense ell.

### 3. Treballs i estadístiques: scopes

Crea un bean `ReportJob` amb scope `prototype`. Cada informe nou ha de tenir una instància nova de `ReportJob` i un ID únic.

El servei ha d'obtenir un nou bean del contenidor cada vegada que genera un informe. Injectar una única instància al constructor d'un servei singleton no és suficient per complir aquest requisit.

Crea també un bean `ReportStatistics` singleton que mantengui el recompte global d'informes generats, compartit entre PDF, HTML i CSV. Cada informe generat correctament ha d'incrementar el total una vegada.

### 4. Memòria cau i cicle de vida

Implementa `ReportCache` com a bean singleton amb un mapa intern que relacioni l'ID de cada informe amb el seu contingut generat.

- Amb `@PostConstruct`, afegeix informes predefinits al mapa. No basta mostrar un missatge: hi ha d'haver dades disponibles.
- Desa al mapa tots els informes nous, sigui quin sigui el format.
- Amb `@PreDestroy`, buida el mapa i registra un missatge que permeti comprovar la neteja en aturar l'aplicació de manera ordenada.

Els informes precarregats no han d'incrementar les estadístiques de generació. El recompte inicial ha de ser zero.

### 5. Motor d'exportació i inicialització diferida

Simula un `ExportEngine` que necessiti deu segons per inicialitzar-se. El retard ha de formar part de la inicialització del bean.

Aquest motor només s'ha de crear quan un usuari demani una exportació. Arrencar l'aplicació, generar informes i consultar les estadístiques no l'ha d'inicialitzar.

Quan s'utilitzi, ha de recuperar els informes de `ReportCache` i retornar-ne el contingut conjunt, identificant cada informe pel seu ID. Aquesta exportació pot ser textual; no cal convertir els informes a un altre format.

La primera crida d'exportació ha de mostrar el retard d'inicialització i les següents no l'han de repetir. Exportar no crea informes nous ni incrementa les estadístiques.

### 6. Configuració del generador CSV

Empra Apache Commons CSV per implementar `CsvReportGenerator`. Afegeix aquesta dependència dins l'element `dependencies` del `pom.xml`:

```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-csv</artifactId>
    <version>1.14.1</version>
</dependency>
```

Crea una classe `ReportConfig` anotada amb `@Configuration` que declari, mitjançant `@Bean`, un objecte `CSVFormat` amb aquestes característiques:

- Separador de camps: punt i coma (`;`).
- Capçalera: `id` i `contingut`.

`CsvReportGenerator` ha de rebre aquest bean per constructor i emprar-lo per generar el CSV. Cada informe CSV ha de contenir la capçalera i una fila amb l'ID del treball i el contingut de l'informe.

Exemple de sortida:

```csv
id;contingut
550e8400-e29b-41d4-a716-446655440000;Informe de demostració
```

Empra `CSVPrinter` per escriure la fila, de manera que la llibreria tracti els separadors, les cometes i els salts de línia que pugui contenir el text. Pots escriure sobre un `StringWriter` i retornar-ne el contingut. Crea el writer i el printer per a cada generació; el bean compartit és el `CSVFormat`.

Com a suport, consulta els mètodes `CSVFormat.DEFAULT.builder()`, `setDelimiter(...)`, `setHeader(...)` i `get()`, i el constructor de `CSVPrinter` i el mètode `printRecord(...)`.

Canviar el separador a `ReportConfig` i reiniciar l'aplicació ha de modificar la sortida CSV sense modificar el generador. Explica per què es declara aquest objecte amb `@Bean` en lloc d'anotar la classe `CSVFormat` amb `@Component`.

### 7. Controlador HTTP

Implementa un `ReportController` amb les rutes següents. Pots separar l'exportació en un altre controlador si mantens la ruta indicada.

| Petició | Comportament |
|---|---|
| `GET /report/pdf` | Genera un informe amb el generador PDF seleccionat per defecte. |
| `GET /report/html` | Genera un informe amb el generador HTML seleccionat explícitament. |
| `GET /report/csv` | Genera un informe amb el generador CSV seleccionat explícitament. |
| `GET /report/stats` | Mostra el nombre total d'informes generats. |
| `GET /report/export` | Utilitza l'`ExportEngine` i retorna els informes de la memòria cau. |

La resposta CSV ha d'indicar el tipus de contingut `text/csv` i emprar UTF-8. L'ID del treball ha de ser visible en cada informe per poder comprovar que canvia entre generacions.

## Comprovacions

Recull evidències breus d'aquestes comprovacions:

1. Genera un informe de cada format i comprova que el recompte global és tres.
2. Genera dos informes del mateix format i comprova que els IDs dels treballs són diferents.
3. Comprova que l'exportació inclou els informes precarregats i els nous, i que no altera el recompte.
4. Comprova que es pot generar un informe amb marca d'aigua i sense el bean que la proporciona.
5. Observa que el motor d'exportació no s'inicialitza en arrencar ni en generar un informe; compara la primera exportació amb la segona.
6. Canvia el separador CSV a la configuració, reinicia i comprova el canvi. Prova també un contingut que inclogui un punt i coma o cometes amb el separador original.
7. Atura l'aplicació de manera ordenada i comprova l'execució de la neteja de la memòria cau.

## Evidències del treball

Prepara el projecte executable i una explicació breu amb les comprovacions anteriors. Identifica on es veu la injecció per constructor i setter, l'opcionalitat, l'ús de `@Primary` i `@Qualifier`, els dos scopes, els hooks de cicle de vida, la inicialització diferida i el bean declarat amb `@Configuration` i `@Bean`.

## Referències

- [Documentació de CSVFormat](https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVFormat.html).
- [Documentació de CSVPrinter](https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVPrinter.html).
- [Historial de versions d'Apache Commons CSV](https://commons.apache.org/proper/commons-csv/changes.html), amb la versió 1.14.1 emprada a l'activitat.
