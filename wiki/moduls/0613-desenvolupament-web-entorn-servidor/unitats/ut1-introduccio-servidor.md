# U1. Introducció a la programació en entorn servidor

**Durada programada:** 20 hores, setembre. **Pes de RA1:** 5% del mòdul. Font: [[../../../fonts/programacio-didactica-2026-2027|programació 2026–2027]], fulls `UT1`, `6-Distribució temporal` i `2-ANCORATGE CURRICULAR`.

## RA i criteris d'avaluació

- RA1 — caracterització de la programació web en entorn servidor.
- CA a concretar: `RA1.a` a `RA1.g`: models client/servidor, generació dinàmica, servidors web i d'aplicacions, tecnologies, integració amb marques i avaluació de frameworks.

## Finalitat i enfocament docent

L'alumnat es familiaritza amb el funcionament del servidor i amb la manera com Spring gestiona els objectes de l'aplicació. El professor combina apunts teòrics, esquemes de suport i un projecte Java que desenvolupa en **code along**, fent visibles els conceptes a mesura que apareixen. Aquest ús dels materials del curs anterior ha estat confirmat pel professor.

## Continguts i tecnologia

El marc curricular inclou model client-servidor, eines, generació dinàmica i integració amb llenguatges de marques. Els materials rebuts desenvolupen concretament Spring Core, IoC, DI per constructor i setter, selecció de dependències, beans, scopes, inicialització diferida i cicle de vida. Els esquemes MVC i el document d'estructura situen aquests conceptes dins l'arquitectura per capes del mòdul. L'assignació curricular d'U1 es manté en `RA1`.

## Materials i code along

La [[../../../fonts/materials-ut1-2526|fitxa dels materials d'U1]] enllaça el PDF de 28 pàgines, el projecte `unitat1.zip`, els dos esquemes MVC i el document d'estructura de projectes. També relaciona cada bloc teòric amb les classes i rutes HTTP del projecte.

El fil dels exemples va de `Cotxe` i `Motor` per explicar dependències a comptadors i tickets per als scopes, UUIDs de petició i sessió, un bean d'inicialització costosa i un generador d'informes amb hooks de cicle de vida. És el repertori present als materials, sense fixar sessions ni donar per impartida aquesta seqüència el curs actual.

## Seguiment i avaluació

Hi ha una [[../../../fonts/practica-ut1-reports-2526|pràctica de Spring Core sobre gestió d'informes]], amb enunciat i proposta de solució del curs anterior. Reuneix interfícies i generadors, DI, scopes, cache, cicle de vida i inicialització diferida en un mateix cas. La fitxa documenta també les diferències entre els requisits i el codi rebut.

El professor l'ha aportada com a activitat per practicar: complementa el code along i prepara l'examen, amb ponderació zero segons el criteri general del mòdul.

Les activitats realitzades i les evidències d'avaluació es registraran durant el curs. La comparativa i el prototip que figuraven a la versió inicial de la wiki eren propostes genèriques, no activitats descrites pel professor. El code along queda documentat com a forma de treball; la seva ingesta no introdueix instruments ni ponderacions nous.

## Referències

- [[../../../orientacio-docent|Orientació docent del mòdul]].
- [[../../../fonts/servidor-master-2526|Font de partida]] · [[../desenvolupament|Desenvolupament]].
