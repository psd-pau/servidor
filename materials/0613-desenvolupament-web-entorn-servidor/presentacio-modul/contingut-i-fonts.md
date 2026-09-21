# Presentació del mòdul 2026–2027

Professor: David Pons

## 1. Desenvolupament web en entorn servidor

0613 · DAW · 2026–2027

Desenvolupament web
en entorn servidor

David Pons · CIFP Pau Casesnoves

01

La lògica que fa funcionar
una aplicació web

David Pons

CIFP Pau Casesnoves

230 hores · 7 hores setmanals · Java i Spring

Font / notes del professor: Presentació del mòdul. Font horària: 1-INTRODUCCIÓ!C7,F7. Professor: David Pons.

## 2. Què passa quan fas clic a «Reservar»?

01 · UNA IDEA GENERAL

Què passa quan fas clic a «Reservar»?

David Pons · CIFP Pau Casesnoves

02

El navegador envia una petició. El servidor decideix què pot fer i retorna una resposta.

Navegador

L’usuari demana
reservar un llibre.

Servidor

Comprova disponibilitat
i aplica les regles.

Dades

Consulta i desa
la reserva.

→

→

En aquest mòdul construirem la part del servidor.

Font / notes del professor: Exemple docent il·lustratiu: reserva d’un llibre. No descriu un projecte imposat al curs.

## 3. Aprendre a construir aplicacions ben organitzades

01 · UNA IDEA GENERAL

Aprendre a construir aplicacions ben organitzades

David Pons · CIFP Pau Casesnoves

03

01

Responsabilitats clares

Cada classe i cada capa tenen una funció dins l’aplicació.

02

Dades i serveis

Consultam dades, aplicam regles de negoci i exposam funcionalitats.

03

Qualitat del codi

Cercam aplicacions comprensibles, mantenibles i comprovables.

Java i Spring seran les eines habituals del curs.

Font / notes del professor: Font: wiki/orientacio-docent.md i CONTEXT_ASSIGNATURA.md del ZIP docent.

## 4. Els fonaments que connectarem

02 · EL MÒDUL DINS DAW

Els fonaments que connectarem

David Pons · CIFP Pau Casesnoves

04

Programació

Classes, objectes, interfícies i estructures de control.

Bases de dades

Model de dades, consultes i persistència.

Llenguatges de marques

HTML i formats d’intercanvi d’informació.

Entorns de desenvolupament

Eines per escriure, depurar i provar el codi.

Font / notes del professor: Relacions pedagògiques sintetitzades per a aquesta presentació. Noms dels mòduls contrastats amb RD 686/2010, https://www.boe.es/buscar/doc.php?id=BOE-A-2010-9269, i RD 405/2023, https://www.boe.es/eli/es/rd/2023/05/29/405. No s’atribueixen hores ni ponderacions als altres mòduls.

## 5. D’una interfície a una aplicació completa

02 · EL MÒDUL DINS DAW

D’una interfície a una aplicació completa

David Pons · CIFP Pau Casesnoves

05

Client i interfícies

Interacció al navegador, formularis i presentació de la informació.

Entorn servidor

Peticions HTTP, regles de negoci, dades i serveis web.

Desplegament

Publicació i configuració de l’aplicació sobre la infraestructura.

Sistemes informàtics aporta la base d’entorn i xarxa.
El projecte intermodular permet combinar aquests aprenentatges.

Font / notes del professor: Relacions pedagògiques sintetitzades per a aquesta presentació. Noms dels mòduls contrastats amb RD 686/2010, https://www.boe.es/buscar/doc.php?id=BOE-A-2010-9269, i RD 405/2023, https://www.boe.es/eli/es/rd/2023/05/29/405. No s’atribueixen hores ni ponderacions als altres mòduls.

## 6. Com treballarem: programarem junts

03 · DESENVOLUPAMENT DEL MÒDUL

Com treballarem: programarem junts

David Pons · CIFP Pau Casesnoves

06

01

Preparació

El professor prepara el projecte Spring amb les classes resoltes.

02

Code along

Construïm el codi a classe i ens aturam per explicar els conceptes.

03

Pràctica

Aplicau i repetiu el que hem treballat per preparar l’examen.

Aquest mètode es manté a totes les unitats.

Font / notes del professor: Metodologia confirmada pel professor el 2026-09-21; wiki/orientacio-docent.md.

## 7. Quatre unitats, un recorregut

03 · DESENVOLUPAMENT DEL MÒDUL

Quatre unitats, un recorregut

David Pons · CIFP Pau Casesnoves

07

U1 · Introducció al servidor

Comprendre l’entorn i Spring.

U2 · Accés a dades

Connectar l’aplicació amb les dades.

U3 · Serveis web

Exposar i consumir funcionalitats.

U4 · Aplicacions web

Integrar les peces en una aplicació.

Font / notes del professor: Font: IFC33C - Desenvolupament web en entorn servidor.xlsx, full 6-Distribució temporal, fila 5; full 2-ANCORATGE CURRICULAR, A4:F12. Títols dels RA extrets del full amb espais normalitzats. Els percentatges indiquen el pes de cada RA en la nota global del mòdul. Els resums d’unitat incorporen l’enfocament docent de wiki/orientacio-docent.md.

## 8. U1 · Introducció a la programació en entorn servidor

03 · UNITATS I RESULTATS D’APRENENTATGE

U1 · Introducció a la programació
en entorn servidor

David Pons · CIFP Pau Casesnoves

08

Entendrem l’entorn servidor i com Spring crea i connecta els objectes: IoC, injecció de dependències i beans.

RESULTAT D’APRENENTATGE

PES GLOBAL

RA1

Selecciona les arquitectures i tecnologies de programació Web en entorn servidor, analitzant les seves capacitats i característiques pròpies

5%

Durada · 20 hores

Font / notes del professor: Font: IFC33C - Desenvolupament web en entorn servidor.xlsx, full 6-Distribució temporal, fila 5; full 2-ANCORATGE CURRICULAR, A4:F12. Títols dels RA extrets del full amb espais normalitzats. Els percentatges indiquen el pes de cada RA en la nota global del mòdul. Els resums d’unitat incorporen l’enfocament docent de wiki/orientacio-docent.md.

## 9. U2 · Accés a dades

03 · UNITATS I RESULTATS D’APRENENTATGE

U2 · Accés a dades

David Pons · CIFP Pau Casesnoves

09

Connectarem l’aplicació amb les dades: consultar, crear, modificar i eliminar informació amb repositoris i serveis.

RESULTAT D’APRENENTATGE

PES GLOBAL

RA6

Desenvolupa aplicacions d’accés a magatzems de dades, aplicant mesures per a mantenir la seguretat i la integritat de la informació.

20%

Durada · 40 hores

Font / notes del professor: Font: IFC33C - Desenvolupament web en entorn servidor.xlsx, full 6-Distribució temporal, fila 6; full 2-ANCORATGE CURRICULAR, A4:F12. Títols dels RA extrets del full amb espais normalitzats. Els percentatges indiquen el pes de cada RA en la nota global del mòdul. Els resums d’unitat incorporen l’enfocament docent de wiki/orientacio-docent.md.

## 10. U3 · Serveis web

03 · UNITATS I RESULTATS D’APRENENTATGE

U3 · Serveis web

David Pons · CIFP Pau Casesnoves

10

Crearem i consumirem serveis web i reutilitzarem informació i components d’altres sistemes.

RESULTATS D’APRENENTATGE

PES GLOBAL

RA7

Desenvolupa serveis Web reutilitzables i accessibles mitjançant protocols web, verificant el seu funcionament.

20%

RA9

Desenvolupa aplicacions Web híbrides seleccionant i utilitzant llibreries de codi i repositoris heterogenis de informació

5%

Durada · 50 hores

Font / notes del professor: Font: IFC33C - Desenvolupament web en entorn servidor.xlsx, full 6-Distribució temporal, fila 7; full 2-ANCORATGE CURRICULAR, A4:F12. Títols dels RA extrets del full amb espais normalitzats. Els percentatges indiquen el pes de cada RA en la nota global del mòdul. Els resums d’unitat incorporen l’enfocament docent de wiki/orientacio-docent.md.

## 11. U4 · Aplicacions web

03 · UNITATS I RESULTATS D’APRENENTATGE · 1/2

U4 · Aplicacions web

David Pons · CIFP Pau Casesnoves

11

Integrarem pàgines dinàmiques, formularis, estat, autenticació i interacció client-servidor amb una arquitectura per capes.

RESULTATS D’APRENENTATGE

PES GLOBAL

RA2

Escriu sentències executables per un servidor Web reconeixent i aplicant procediments de integració del codi en llenguatges de marques.

10%

RA3

Escriu blocs de sentències embeguts en llenguatges de marques, seleccionant i utilitzant les estructures de programació

10%

Continua amb RA4, RA5 i RA8 →

Font / notes del professor: Font: IFC33C - Desenvolupament web en entorn servidor.xlsx, full 6-Distribució temporal, fila 8; full 2-ANCORATGE CURRICULAR, A4:F12. Títols dels RA extrets del full amb espais normalitzats. Els percentatges indiquen el pes de cada RA en la nota global del mòdul. Els resums d’unitat incorporen l’enfocament docent de wiki/orientacio-docent.md.

## 12. U4 · Aplicacions web: continuació

03 · UNITATS I RESULTATS D’APRENENTATGE · 2/2

U4 · Aplicacions web: continuació

David Pons · CIFP Pau Casesnoves

12

RESULTATS D’APRENENTATGE

PES GLOBAL

RA4

Desenvolupa aplicacions Web embegudes en llenguatges de marques analitzant i incorporant funcionalitats segons especificacions.

10%

RA5

Desenvolupa aplicacions Web identificant i aplicant mecanismes per a separar el codi de presentació de la lògica de negoci.

10%

RA8

Genera pàgines Web dinàmiques analitzant i utilitzant tecnologies del servidor Web que afegeixin codi al llenguatge de marques

10%

Durada de tota U4 · 120 hores

Font / notes del professor: Font: IFC33C - Desenvolupament web en entorn servidor.xlsx, full 6-Distribució temporal, fila 8; full 2-ANCORATGE CURRICULAR, A4:F12. Títols dels RA extrets del full amb espais normalitzats. Els percentatges indiquen el pes de cada RA en la nota global del mòdul. Els resums d’unitat incorporen l’enfocament docent de wiki/orientacio-docent.md.

## 13. Avaluació: practicar i demostrar què sabem

04 · AVALUACIÓ I QUALIFICACIÓ

Avaluació: practicar i demostrar què sabem

David Pons · CIFP Pau Casesnoves

13

Pràctiques

Serveixen per aprendre i preparar l’examen.

Tenen ponderació 0% en la nota.

Exàmens en paper

Les proves individuals permeten valorar els resultats d’aprenentatge treballats.

Les pràctiques obligatòries s’han de lliurar i aprovar.

Font / notes del professor: Font: full 4-AVALUACIÓ!A4:A10 i 2-ANCORATGE CURRICULAR!A4:F12. Precisió directa del professor: exàmens en paper, pràctiques per preparar-los i recuperació dels RA suspesos a final de curs. El llindar de 5 o més es pren del full de càlcul.

## 14. Cal aprovar tots els resultats d’aprenentatge

04 · AVALUACIÓ I QUALIFICACIÓ

Cal aprovar tots els resultats d’aprenentatge

David Pons · CIFP Pau Casesnoves

14

Cada RA ≥ 5

Una bona nota en un RA no compensa un altre RA suspès.

Una nota de RA inferior a 5 no s’arrodoneix per convertir-la en un aprovat.

Cada prova individual ha de tenir almenys un 5 per fer mitjana.

Font / notes del professor: Font: full 4-AVALUACIÓ!A4:A10 i 2-ANCORATGE CURRICULAR!A4:F12. Precisió directa del professor: exàmens en paper, pràctiques per preparar-los i recuperació dels RA suspesos a final de curs. El llindar de 5 o més es pren del full de càlcul.

## 15. L’arrodoniment és el darrer pas

04 · AVALUACIÓ I QUALIFICACIÓ

L’arrodoniment és el darrer pas

David Pons · CIFP Pau Casesnoves

15

01

Comprovar els RA

Tots han d’estar aprovats amb una nota igual o superior a 5.

02

Aplicar els pesos

Calcular la nota ponderada amb els percentatges globals dels RA.

03

Arrodonir la nota

Aplicar l’arrodoniment natural a la nota final del mòdul.

L’arrodoniment només s’aplica després de superar tots els RA.

Font / notes del professor: Font: full 4-AVALUACIÓ!A4:A10 i 2-ANCORATGE CURRICULAR!A4:F12. Precisió directa del professor: exàmens en paper, pràctiques per preparar-los i recuperació dels RA suspesos a final de curs. El llindar de 5 o més es pren del full de càlcul.

## 16. Tres exemples per entendre-ho

04 · AVALUACIÓ I QUALIFICACIÓ

Tres exemples per entendre-ho

David Pons · CIFP Pau Casesnoves

16

Un RA té 4,9

El RA continua suspès.

Encara que la mitjana sigui alta, cal recuperar-lo.

Tots aprovats: 6,49

La nota ponderada final és 6,49.

Arrodoniment natural: 6.

Tots aprovats: 6,50

La nota ponderada final és 6,50.

Arrodoniment natural: 7.

Font / notes del professor: Font: full 4-AVALUACIÓ!A4:A10 i 2-ANCORATGE CURRICULAR!A4:F12. Precisió directa del professor: exàmens en paper, pràctiques per preparar-los i recuperació dels RA suspesos a final de curs. El llindar de 5 o més es pren del full de càlcul. Exemples numèrics il·lustratius.

## 17. Recuperació dels RA suspesos

04 · AVALUACIÓ I QUALIFICACIÓ

Recuperació dels RA suspesos

David Pons · CIFP Pau Casesnoves

17

A final de curs

Es podran recuperar els resultats d’aprenentatge que hagin quedat suspesos.

La recuperació comprovarà els aprenentatges no assolits.
Segons la programació, la nota màxima de les proves de recuperació és 5.

Per superar el mòdul, tots els RA han de quedar aprovats.

Font / notes del professor: Font: full 4-AVALUACIÓ!A4:A10 i 2-ANCORATGE CURRICULAR!A4:F12. Precisió directa del professor: exàmens en paper, pràctiques per preparar-los i recuperació dels RA suspesos a final de curs. El llindar de 5 o més es pren del full de càlcul.

## 18. Altres criteris que hem de tenir clars

04 · AVALUACIÓ I QUALIFICACIÓ

Altres criteris que hem de tenir clars

David Pons · CIFP Pau Casesnoves

18

Absències a proves

Cal justificar-les dins el termini i pels motius admesos pel centre; en cas contrari, la prova es qualifica amb 0.

Còpia o plagi

La qualificació de l’activitat o prova afectada és 0.

Ús d’IA generativa

Només quan el professor l’autoritzi expressament per a una activitat i amb un ús traçable.

Font / notes del professor: Font: full 4-AVALUACIÓ!A4:A10 i 2-ANCORATGE CURRICULAR!A4:F12. Precisió directa del professor: exàmens en paper, pràctiques per preparar-los i recuperació dels RA suspesos a final de curs. El llindar de 5 o més es pren del full de càlcul.
