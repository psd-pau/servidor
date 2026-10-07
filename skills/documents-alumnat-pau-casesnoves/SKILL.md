---
name: documents-alumnat-pau-casesnoves
description: Crear o editar dossiers, apunts, guies de pràctica i enunciats del mòdul 0613 per a l'alumnat IFC33C del CIFP Pau Casesnoves a partir d'un o diversos Markdown, amb DOCX editable i PDF equivalent, identitat visual del centre i autoria David Pons. Per a exàmens i presentacions, usar les skills pròpies.
---

# Documents per a l'alumnat — CIFP Pau Casesnoves

## Resultat i abast

Genera, per defecte, **un DOCX editable i un PDF exportat del mateix DOCX**. Respecta un format editable diferent si l'usuari el demana. El text, el codi, les llistes i les taules han de ser elements editables, no captures de pàgines.

Aplica la skill a apunts, dossiers conceptuals, guies de pràctica, activitats i enunciats. La petició determina el tipus de document: no transformis uns apunts en una fitxa d'exercicis ni afegeixis una programació didàctica. Les presentacions es regeixen per la seva skill pròpia. Per a proves d'avaluació, aplica [../examens-pau-casesnoves/SKILL.md](../examens-pau-casesnoves/SKILL.md); la plantilla d'examen preval sobre la maquetació d'aquesta guia.

Desa els resultats al directori pertinent de `materials/`, habitualment al costat del Markdown principal, amb el mateix nom base i extensions `.docx` i `.pdf`. Mantén els originals de `raw/` i `materials-anteriors/` intactes. No modifiquis el contingut font per exigències de maquetació; si cal una versió adaptada, desa-la separadament i explica'n els canvis.

## Lectura i criteri editorial

Llegeix íntegrament els Markdown seleccionats i els seus recursos necessaris. Si hi ha diversos fitxers, identifica el fil conductor i elimina només duplicacions clares, conservant les explicacions, els exemples i les referències. Respecta l'ordre demanat; si no n'hi ha, usa una progressió comprensible i registra qualsevol reorganització significativa.

Per a material curricular del projecte, consulta `wiki/index.md`, `wiki/orientacio-docent.md`, el hub `wiki/moduls/0613-desenvolupament-web-entorn-servidor.md` i la fitxa d'UT pertinent per confirmar denominacions, RA i CA (`RAx.a`, etc.). Aquest context orienta l'edició; no s'ha de copiar automàticament al document de l'alumne. El nom del mòdul és fix: **Desenvolupament web en entorn servidor** (0613), denominació curricular del BOE en català. El grup és **IFC33C**, llevat d'una indicació expressa diferent. Pren el curs acadèmic i la unitat de l'encàrrec o d'una font identificable; si no es poden determinar, omet-los.

La programació docent es fa amb **Java i Spring**, no amb Python. Totes les unitats segueixen un **code along** a partir d'un projecte Spring que el professor prepara resolt. Quan es preparen o amplien materials, relaciona les explicacions amb el projecte de referència, la progressió de classes Java i els punts d'aturada que s'hagin concretat. En una simple conversió de format, conserva el contingut existent; no inventis una seqüència d'aula ni presentis propostes com a activitats realitzades.

- Escriu en català; conserva identificadors de codi, noms de tecnologies i referències normatives exactes.
- Estableix **`ca-ES`** a les metadades de llengua del DOCX i a la llengua de correcció dels estils i del text, incloses capçaleres i peus. Conserva-la en exportar el PDF i comprova-la quan el convertidor ofereixi metadades de llengua. El català és la llengua d'impartició de les classes.
- Preserva el contingut docent. Convertir a DOCX no autoritza resumir-lo, eliminar matisos o inventar activitats, dades, hores o pesos de qualificació.
- Mantén les solucions i l'autocorrecció quan formen part dels apunts originals. Si es demana una versió sense respostes, separa explícitament document d'alumnat i solucions, cadascun amb el seu PDF si es demana.
- Distingeix obligació normativa, interpretació docent i proposta quan aquesta distinció afecti el contingut. Evita omplir el dossier amb notes internes de manteniment.
- Una petició de document autònom exigeix exemples, dades i instruccions suficients dins el document. Resol les remissions necessàries o incorpora el fragment pertinent; no imposis dependències d'altres activitats. Les fonts bibliogràfiques poden continuar com a consulta opcional.

## Identitat visual

La identitat compartida amb les presentacions procedeix de la plantilla del centre «MDE20702 Plantilla Presentació CIFP Pau Casesnoves.pptx». Els criteris de pàgina següents són convencions docents de documents, no una plantilla oficial normativa.

- Centre: **CIFP Pau Casesnoves**. Autoria: **David Pons**, també a les propietats del fitxer.
- Logotip: [assets/logo-pau-casesnoves.png](assets/logo-pau-casesnoves.png). És una còpia del recurs de la skill de presentacions; no cal carregar aquella skill ni accedir a altres repositoris. Conserva proporcions i colors, sobre blanc. Usa'l a la capçalera inicial o portada, normalment amb 3–4 cm d'amplada.
- Verd principal `#175F16`: títols, capçaleres i línies. Verd viu `#32CD33`: accents gràfics discrets. No l'empris com a text corrent sobre blanc.
- Text `#202124`; text secundari `#595959`; fons de suport `#F3F5F3`; fons general blanc.
- Títols: Poppins Semibold; cos: Arial; codi: DejaVu Sans Mono. Comprova les fonts disponibles abans de generar. Si no estan instal·lades, usa una combinació coherent de substitució, preferentment Liberation Sans / Liberation Mono, o fonts verificades a l'entorn. Evita substitucions silencioses entre DOCX i PDF.

## Maquetació de lectura

Usa **A4 vertical**, amb marges orientatius de 2,2 cm i espai reservat per capçalera i peu. Cos de 11 pt, interlineat aproximat d'1,15 i separació de 6 pt després de paràgraf. Prefereix alineació esquerra; no introdueixis espais manuals per justificar text.

En documents breus, usa una capçalera inicial compacta amb logotip, títol, mòdul i autoria. En dossiers llargs, pot convenir una portada amb títol de 24–28 pt. No hi afegeixis una portada buida de contingut per rutina. Un índex és útil si facilita navegar; si és automàtic, actualitza'l abans d'exportar i comprova'n els números.

Defineix **estils reals del processador de textos**: títol, subtítol, encapçalaments jeràrquics, cos, llistes, codi, peu i llegenda. Mides orientatives: encapçalament 1 de 17–19 pt, encapçalament 2 de 13–15 pt, encapçalament 3 d'11–12 pt. Usa «mantén amb el següent» als encapçalaments i control de línies vídues/orfes. No converteixis cada secció en una pàgina nova.

Peu discret amb `David Pons · CIFP Pau Casesnoves` i **camp automàtic de número de pàgina**. Capçalera interior amb títol abreujat o mòdul si ajuda a identificar pàgines impreses. Evita repetir tota l'autoria que el Markdown pugui dur al primer paràgraf si ja s'ha incorporat a la composició inicial.

### Taules

Genera taules natives editables amb capçalera distingible, contrast suficient i amplades proporcionals al contingut. Repeteix la fila de capçalera quan la taula ocupa més d'una pàgina. Evita partir files curtes; permet continuar files que no cabrien en una pàgina sencera. Cos habitual de 10–11 pt.

Si una taula és massa ampla, prova amplades diferents o una reformulació que conservi les correspondències. Una secció A4 horitzontal és acceptable per a una comparativa extensa. No redueixis el text fins a fer-lo il·legible ni converteixis la taula en una imatge. Conserva notes, unitats i fonts associades.

### Codi, exemples i requadres

El codi ha de conservar literalment identificadors, puntuació, salts i indentació. Usa text editable monoespaiat de 9–10 pt, fons `#F3F5F3` i marge interior. Distingeix codi, sortida i explicació. Els identificadors en línia també van en monoespaiada.

Comprova línies llargues: usa una disposició més ampla o continuacions tipogràfiques explícites sense alterar el codi executable. No converteixis cometes en cometes tipogràfiques dins codi. Conserva blocs curts junts; permet continuar blocs llargs amb una indicació de continuació si és necessària.

Un requadre d'exemple, definició o advertiment ha de respondre al contingut, amb títol i contrast llegibles també en blanc i negre. No introdueixis requadres decoratius a cada paràgraf. Un enunciat pot tenir espai de resposta si es demana treballar-hi; no l'afegeixis per defecte a un dossier de lectura.

### Imatges, esquemes i fonts

Resol imatges relatives des del directori del Markdown corresponent, no des del directori d'execució. Conserva proporcions, llegendes, autoria i procedència. Una imatge vinculada ha d'anar incrustada al DOCX perquè funcioni fora del repositori.

Renderitza Mermaid o altres esquemes que el processador no interpreti; no deixis el codi de Mermaid com si fos el diagrama final. Conserva la font editable del diagrama en un fitxer identificable si el gràfic s'ha de regenerar. Prefereix elements editables quan sigui viable, sense rasteritzar el text corrent del document. Comprova contrast i resolució per a impressió.

Mantén els enllaços externs com a hipervincles amb títol descriptiu. Converteix wikilinks i remissions internes a referències comprensibles; no deixis rutes locals de desenvolupament com a instruccions per a l'alumnat. Quan el PDF s'hagi d'imprimir, procura que les fonts continuïn identificables per títol, organisme i referència. No exigeixis llegir una web per entendre una explicació que havia de ser autònoma.

## Producció del DOCX i del PDF

Aquest entorn **no disposa de Python**. Usa eines disponibles que no en depenguin: **Pandoc amb document de referència** si tracta bé el Markdown seleccionat, automatització de Word/LibreOffice o **PowerShell i .NET/Open XML** per controlar el DOCX. No instal·lis Python ni traslladis dependències del repositori d'IA per generar materials. Si crees un generador per a un document complex, conserva'l al costat dels materials o dins un subdirectori `eines/`, amb paràmetres d'entrada identificables. Dirigeix memòria cau i perfils temporals a una ubicació temporal mitjançant variables específiques.

Exporta el **DOCX definitiu** a PDF amb LibreOffice/Word o un convertidor que renderitzi aquest DOCX. Amb LibreOffice, usa un perfil temporal separat perquè una instància oberta no intercepti la conversió. Executa amb llista d'arguments quan l'API ho permeti i comprova codi de sortida i fitxer resultant. No generis el PDF independentment amb una segona maquetació HTML: podria divergir de l'editable.

Després de qualsevol canvi de contingut o maquetació al DOCX, torna a exportar. Comprova que el PDF és el resultat actual, no una còpia antiga amb el mateix nom. No sobreescriguis edicions manuals del DOCX sense revisar-les si la petició és actualitzar un document ja modificat pel professor.

Si falta una eina de conversió, completa l'editable i identifica el pas pendent. No lliuris un HTML reanomenat com a DOCX ni afirmis que el PDF existeix o està verificat quan no s'ha pogut generar.

## Revisió i lliurament

Comprova el resultat observable, no només que existeixin els fitxers:

1. Contrast de contingut amb les fonts Markdown: seccions, exemples, taules, codi, referències i respostes conservats. Les adaptacions autoritzades s'han d'identificar.
2. DOCX vàlid amb estils, taules i text editable, autoria correcta i imatges incrustades. Evita sintaxi Markdown residual com `**`, delimitadors de taula o wikilinks sense convertir.
3. PDF exportat del DOCX actual, amb mida de pàgina prevista, text seleccionable, enllaços útils i sense pàgines buides inesperades. Les seccions horitzontals han de mantenir mida A4.
4. Revisió visual de portada/capçalera inicial, pàgina densa, taula ampla, codi i darrera pàgina, i altres pàgines on s'observin incidències. Comprova marges, salts, numeració, fonts, retalls i llegibilitat.

Si disposes d'eines de renderització, genera vistes temporals per inspeccionar-les. Si la revisió visual o l'exportació no són possibles, indica exactament què s'ha comprovat i què queda pendent; no donis per fetes comprovacions gràfiques.

Lliura enllaços al DOCX i al PDF, amb una frase sobre les adaptacions o limitacions materials. Si s'ha creat un generador, enllaça'l quan ajudi a regenerar el resultat. Actualitza l'índex de materials i el log quan sigui pertinent al projecte, sense canviar la seqüència curricular per una simple exportació.
