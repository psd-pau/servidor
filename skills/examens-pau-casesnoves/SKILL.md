---
name: examens-pau-casesnoves
description: Crear o editar exàmens en paper de resposta oberta del mòdul 0613 per al grup IFC33C del CIFP Pau Casesnoves, amb casos d'aplicació i puntuació per parts, en DOCX i PDF amb la plantilla del centre. Usar per a proves d'avaluació, no per a dossiers, apunts o guies de pràctica.
---

# Exàmens — CIFP Pau Casesnoves

## Plantilla i resultat

Parteix d'una còpia de [assets/plantilla-examen.docx](assets/plantilla-examen.docx), facilitada per David Pons el 7 d'octubre de 2026. Conserva l'original de la skill. No reconstrueixis el document des de zero: la plantilla conté format directe, a més d'estils, logotip, taula d'identificació i camps automàtics de pàgina.

Lliura per defecte un **DOCX editable i el PDF exportat d'aquest mateix DOCX** a la carpeta de materials del mòdul i la UT corresponents. Respecta un format diferent si es demana. Autoria: **David Pons**, també a les propietats del fitxer. No afegeixis una portada.

La llengua d'impartició i dels documents és el **català**. Estableix **`ca-ES`** tant a les metadades de llengua del DOCX com a la llengua de correcció dels estils i del text, incloses capçaleres, peus i apartats afegits. Conserva aquesta llengua en exportar el PDF i comprova-la quan el convertidor ofereixi metadades de llengua. La plantilla local ja està adaptada al català.

Per a l'exportació i la revisió del document, aplica les seccions «Producció del DOCX i del PDF» i «Revisió i lliurament» de [../documents-alumnat-pau-casesnoves/SKILL.md](../documents-alumnat-pau-casesnoves/SKILL.md). En exàmens preval **la maquetació d'aquesta plantilla** sobre la maquetació genèrica de dossiers d'aquella skill.

## Dades que s'han d'emplenar

Conserva les etiquetes de la taula; emplena les cel·les buides adjacents.

| Element | Valor |
| --- | --- |
| `PROVA D’AVALUACIÓ UNITAT X` | Substitueix només `X` pel número de la UT de l'examen. |
| `Curs` | Curs acadèmic en format `AA/AA`, calculat de manera dinàmica. |
| `Grup` | `IFC33C`, tret que l'encàrrec indiqui un altre grup. |
| `Mòdul` | **Desenvolupament web en entorn servidor**, nom fix del mòdul 0613, denominació curricular del BOE en català. |
| `Data` | Data de realització si està indicada; altrament deixa-la en blanc. |
| `Qualificació` i `Alumne/a` | En blanc a la versió per a l'alumnat, llevat d'una petició expressa. |

Determina el curs a partir de la **data de realització de l'examen**, si es coneix; en cas contrari, de la data actual a Europe/Madrid. Com a convenció de càlcul, de setembre a desembre el primer any és l'any natural; de gener a agost és l'any anterior. Exemples: 07/10/2026 → `26/27`; 15/02/2027 → `26/27`; 07/10/2027 → `27/28`. Si l'usuari indica expressament un curs, usa'l. No deixis `26/27` fixat per als exàmens futurs.

Identifica la UT a l'encàrrec o als materials pertinents. Consulta `wiki/index.md`, `wiki/orientacio-docent.md`, el hub `wiki/moduls/0613-desenvolupament-web-entorn-servidor.md`, el seu `desenvolupament.md` i la fitxa de la UT per verificar denominacions, RA i CA (`RAx.a`, etc.). El mòdul del projecte és **0613. Desenvolupament web en entorn servidor**, de DAW. Contrasta també els materials i el projecte de code along que es vulguin avaluar: una previsió curricular no demostra que una eina ja s'hagi treballat a classe.

Si falta una dada essencial d'unitat o d'abast avaluat i no es pot deduir del context, demana només la informació que falta; pots avançar amb la resta de la prova. No inventis una UT ni continguts ja impartits.

## Estils i composició

- Conserva A4 vertical, marges, espai de capçalera, amplades i vores de la taula, logotip, capçaleres i peus de la plantilla.
- Títol de la prova: **Poppins, 14 pt, negreta i centrat**. Camps d'identificació: **Poppins, 11 pt**, etiquetes en negreta i valors en rodona.
- Cos dels enunciats: **Poppins, 11 pt**, a partir del paràgraf de cos de la plantilla. El seu estil `normal1` hereta Times New Roman de 10 pt: conserva o explicita el format Poppins d'11 pt en el cos; assignar només `normal1` no és suficient.
- Clona els paràgrafs i les propietats dels fragments pertinents per conservar format directe i estils. No reassignis tot el text d'un paràgraf si això elimina els fragments amb format.
- Conserva el codi documental **MDO20301** i els camps **PAGE/NUMPAGES** del peu; actualitza'ls abans d'exportar. No substitueixis el peu pel genèric dels dossiers.
- Usa text, taules i codi editables; codi monoespaiat quan calgui. Revisa salts i espai de resposta segons el tipus de prova. Comprova Poppins a l'entorn de renderització; si no està disponible, identifica la substitució necessària i qualsevol diferència visual.

## Preparació de la capçalera

El helper [scripts/prepara-examen.ps1](scripts/prepara-examen.ps1) crea una còpia de la plantilla amb els camps emplenats i l'autoria, sense canviar estils, imatges ni peus. Funciona amb PowerShell i .NET, sense dependència de Python. **Prepara la base; no redacta les preguntes ni exporta el PDF.**

Des de l'arrel del projecte:

```powershell
& ./skills/examens-pau-casesnoves/scripts/prepara-examen.ps1 -Unitat 2 -Sortida ./materials/0613-desenvolupament-web-entorn-servidor/UT2/examen-ut02.docx
```

El helper fixa el nom **Desenvolupament web en entorn servidor** i el grup per defecte **IFC33C**. Opcions: `-Grup`, `-Curs`, `-DataExamen` (data ISO `2027-02-15`). Sense `-DataExamen`, calcula el curs amb la data actual però deixa la cel·la Data buida. El helper rebutja sobreescriure un fitxer existent; per editar un examen ja creat, treballa sobre aquell document i conserva les modificacions del professor.

Si Windows bloqueja l'execució de scripts locals, pots executar aquest helper amb `powershell -NoProfile -ExecutionPolicy Bypass -File <ruta-del-helper>` i els mateixos arguments; l'opció afecta només aquell procés i no modifica la política del sistema.

## Contingut i comprovació

### Model docent: resoldre un cas en paper

**Decisió docent de David Pons, 2026-10-07:** els exàmens de servidor plantegen un problema que l'alumnat resol amb **resposta oberta en paper**, aplicant les eines, anotacions, llibreries i conceptes treballats a la UT. L'objectiu és evidenciar que sap **identificar què necessita a cada part del problema i emprar-ho correctament**. No preparis qüestionaris tipus test com a format habitual ni importis la penalització de respostes seleccionades del projecte d'IA.

Parteix d'un cas coherent amb diversos apartats avaluables separadament. Demana classes, fragments de codi, signatures, anotacions, consultes o justificacions quan siguin evidències pertinents; una enumeració de noms d'eines no substitueix la seva aplicació. Ajusta el volum al treball manuscrit: demana el codi rellevant i proporciona dades o estructura de suport quan permetin evitar feina mecànica sense resoldre la decisió que s'avalua. No exigeixis executar un projecte ni lliurar fitxers com a resposta a l'examen en paper.

### Enunciats que exigeixen decidir

Descriu **comportaments i restriccions del problema**, no la recepta tècnica que els resol. El cas ha de permetre deduir l'eina amb informació suficient i sense pistes que facin trivial la decisió.

- Si es vol avaluar la gestió d'estat per usuari, descriu què s'ha de conservar entre peticions i com s'ha d'aïllar entre usuaris; no ordenis «crea un bean amb scope session» ni donis `@SessionScope` al títol, al codi de suport o al barem visible.
- Si es vol avaluar la inicialització diferida, descriu quan cal disposar d'un component i el cost o efecte de crear-lo; no ordenis «aplica `@Lazy`». Distingeix aquest objectiu de la càrrega diferida de relacions JPA si és el concepte treballat.
- Altres parts poden exigir components amb cicle de vida de petició, operacions HTTP d'una API REST, entitats i relacions JPA o un repositori amb una consulta ajustada al cas d'ús, sempre que pertanyin a l'abast treballat.

Aquests exemples il·lustren el criteri de redacció; **no són una llista obligatòria ni han d'aparèixer tots a cada examen**. Usa apartats amb noms funcionals i puntuació visible, sense revelar l'anotació o l'eina esperada. Es poden explicitar dades, contractes i condicions necessàries; evita l'ambigüitat gratuïta. Si diverses solucions treballades satisfan els requisits, admet-les al criteri de correcció o concreta millor el cas quan calgui discriminar una decisió determinada.

### Puntuació per parts i traçabilitat

Desglossa la valoració del cas en parts observables. En el document de preparació del professor, relaciona cada part amb els **RA i CA pertinents, l'evidència esperada i els punts**. La traçabilitat i la solució tècnica detallada no s'han de copiar automàticament a l'enunciat de l'alumnat.

El barem ha de permetre donar crèdit parcial a la identificació i a l'aplicació de l'eina quan siguin evidències diferenciables. Valora les parts separadament, amb dependències explícites, per evitar que un únic error inicial anul·li tota la prova o es penalitzi repetidament. Distingeix els errors conceptuals dels errors menors de transcripció manuscrita segons l'objectiu avaluat; les anotacions, signatures o consultes essencials sí que poden ser part de l'evidència exigida.

Redacta en català. No inventis durada, materials permesos, pesos o penalitzacions. Si proposes punts o criteris de crèdit parcial encara no acordats, identifica'ls com a **proposta docent** i comprova que la suma correspon al total indicat. Emplena «Criteris de qualificació» amb el barem acordat, sense donar-hi pistes de solució. No afegeixis penalitzacions de tipus test a les respostes obertes. Quan es demani un solucionari, desa'l separadament de la prova per a l'alumnat, amb el desglossament de correcció i les alternatives acceptables.

### Comprovació final

Abans de lliurar, comprova que el cas és resoluble amb l'abast treballat, exigeix triar i aplicar les eines i no en revela la resposta als títols, instruccions, fragments de suport o criteris visibles. Revisa que els apartats es poden valorar separadament i que els punts sumen el total previst. Distingeix sempre una prova preparada d'una prova efectivament realitzada en actualitzar la wiki.

Verifica també número de UT, curs, grup **IFC33C**, títol complet del mòdul, absència de `UNITAT X`, estils i logotip conservats, espai suficient per a respostes manuscrites —inclòs el codi— i numeració actualitzada. Revisa visualment el PDF, especialment la capçalera amb el títol llarg del mòdul, el peu i els salts entre apartats. Si no pots exportar o fer revisió visual, lliura el que està complet i explica el pas pendent amb precisió.
