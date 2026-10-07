# Instruccions del projecte

Aquest repositori és una base de coneixement per preparar el mòdul `0613. Desenvolupament web en entorn servidor` del cicle de Desenvolupament d'Aplicacions Web (DAW).

## Llengua i criteri docent

- Respon i redacta la wiki en català.
- El català és la llengua d'impartició i dels materials generats. Configura també la llengua de correcció i les metadades dels documents com a `ca-ES` quan el format ho admeti, i conserva-la en les exportacions.
- El centre de gravetat de la planificació són els resultats d'aprenentatge (RA), els criteris d'avaluació (CA) i les evidències observables.
- Les tecnologies (actualment Java i Spring) són mitjans per assolir els RA; es poden actualitzar si se'n manté la traçabilitat curricular.
- Diferencia entre contingut curricular, decisió docent i proposta de millora.
- Totes les unitats segueixen un code along: el professor prepara un projecte Spring amb les classes Java ja programades i després les programa amb l'alumnat, aturant-se per explicar els conceptes. En preparar materials, pren aquest mètode com a base i documenta el projecte de referència, la progressió del codi i els punts d'explicació quan es concretin.

## Estructura i manteniment

- `raw/` conté fonts originals i és immutable. No hi modifiquis, reanomenis ni eliminis documents.
- `wiki/` és la capa viva: síntesis, decisions, enllaços i desenvolupament de les unitats.
- `materials/` conté materials vigents per a l'alumnat; `materials-anteriors/` conserva materials reutilitzables o històrics.
- `wiki/orientacio-docent.md` recull l'enfocament personal del mòdul extret del ZIP `2526_Servidor-master.zip`; consulta'l abans de planificar o ampliar materials.
- El full de programació d'aula 2026–2027 aporta hores, RA, ponderacions i espais de seguiment. Les taules d'activitats s'emplenen a mesura que avança el curs: no interpretis els camps buits com una mancança ni inventis activitats com si ja s'haguessin impartit.
- Consulta `wiki/index.md` i les pàgines pertinents abans de respondre sobre el mòdul.
- Quan incorporis una font o una decisió pedagògica útil, actualitza `wiki/index.md` si cal i afegeix una entrada cronològica a `wiki/log.md`.
- La pàgina `wiki/moduls/0613-desenvolupament-web-entorn-servidor.md` és el resum curricular. El detall operatiu viu a `wiki/moduls/0613-desenvolupament-web-entorn-servidor/desenvolupament.md` i a les fitxes d'unitat.
- No presentis aquesta wiki com una programació didàctica oficial: és una eina de treball del professorat.

## Convencions

- Per crear o editar presentacions d'aquest projecte, llegeix i aplica `skills/presentacions-pau-casesnoves/SKILL.md`. Defineix la paleta del centre, la tipografia, les disposicions i el tractament del codi; el nom del professor és **David Pons**. La plantilla del centre és una referència visual adaptable.
- Per crear o editar dossiers, apunts, guies de pràctica o enunciats en format editable i PDF, llegeix i aplica `skills/documents-alumnat-pau-casesnoves/SKILL.md`. Per defecte genera DOCX i el PDF exportat d'aquest mateix document, amb identitat del CIFP Pau Casesnoves i autoria David Pons. No cal aplicar-la per editar només un Markdown.
- Per crear o editar exàmens, llegeix i aplica `skills/examens-pau-casesnoves/SKILL.md`. Empra la plantilla d'examen del centre, que preval sobre el format genèric de dossiers. El grup és **IFC33C** i el nom fix del mòdul és **Desenvolupament web en entorn servidor** (0613), denominació curricular del BOE en català; el curs acadèmic es calcula dinàmicament.
- Els exàmens són problemes de **resposta oberta en paper**, amb parts puntuades separadament. Han d'avaluar que l'alumnat identifica i aplica les eines, anotacions, llibreries i conceptes treballats a la UT. Descriu requisits i comportaments que permetin deduir la solució, sense indicar explícitament l'eina que s'avalua. Els exemples de scopes, lazy, REST o JPA no són una llista obligatòria per a cada prova.
- La programació del mòdul es fa amb **Java i Spring**. Aquest entorn no disposa de Python; les eines de generació de materials han de funcionar sense aquesta dependència.

- Empra els codis `RA1` a `RA9` i `RAx.a` a `RAx.h` de manera consistent.
- Cada unitat ha d'indicar els RA i CA pertinents i pot registrar productes, materials, activitats, avaluació i millores a mesura que es concretin o es duguin a terme. Distingeix sempre una activitat realitzada d'una proposta.
- Conserva la referència exacta al document font quan facis una síntesi curricular.
