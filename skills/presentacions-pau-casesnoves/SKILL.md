---
name: presentacions-pau-casesnoves
description: Crear o editar presentacions docents del CIFP Pau Casesnoves amb colors corporatius, format consistent i autoria David Pons. Aplicar a diapositives de PowerPoint, PDF, Marp o HTML i als seus temes visuals.
---

# Presentacions de David Pons — CIFP Pau Casesnoves

## Abast i procedència

Aplica aquesta identitat visual quan preparis presentacions docents d'aquest projecte. L'usuari vol consistència entre presentacions i permet adaptar la composició de la plantilla del centre. Les indicacions explícites de cada encàrrec tenen preferència sobre els valors per defecte d'aquesta guia.

Font: [MDE20702 Plantilla Presentació CIFP Pau Casesnoves.pptx](../../materials-anteriors/0613-desenvolupament-web-entorn-servidor/MDE20702%20Plantilla%20Presentaci%C3%B3%20CIFP%20Pau%20Casesnoves.pptx). Els dos verds s'han extret dels colors directes de `ppt/slides/slide1.xml`; Poppins apareix a la portada i Arial als temes i altres diapositives. El document és 16:9 (`9144000 × 5143500` EMU). La resta de mesures d'aquesta guia són convencions de treball per donar consistència a les presentacions, no prescripcions oficials del centre.

## Identitat fixa

- Professor: **David Pons**. Escriu el nom així a la portada i als camps d'autoria que es generin.
- Centre: **CIFP Pau Casesnoves**.
- Llengua: català; conserva els identificadors del codi i els noms propis de les tecnologies.
- Logotip: [assets/logo-pau-casesnoves.png](assets/logo-pau-casesnoves.png), còpia exacta de `ppt/media/image1.png` de la plantilla. Mantén-ne les proporcions i els colors; col·loca'l sobre blanc, amb espai lliure al voltant. Usa'l a la portada; a la resta, només si encaixa sense carregar la diapositiva.
- No inventis cursos acadèmics ni números d'unitat: pren-los de l'encàrrec o de la wiki.

## Paleta

| Funció | Color | Ús |
|---|---|---|
| Verd principal del centre | `#175F16` | Títols, capçaleres, línies i elements principals dels diagrames. |
| Verd viu del centre | `#32CD33` | Accents, destacats, indicadors de progrés i detalls gràfics. |
| Blanc | `#FFFFFF` | Fons habitual i espai de respiració. |
| Text principal | `#202124` | Text corrent i codi sobre fons clar; neutral afegit per a la guia. |
| Text secundari | `#595959` | Peus i referències; gris present als patrons de la plantilla. |
| Fons de suport | `#F3F5F3` | Blocs de codi i requadres; neutral afegit per a la guia. |

El verd viu s'utilitza com a accent gràfic; per a text sobre blanc, usa el verd principal o el text fosc. Si un requadre té fons verd viu, usa text fosc. Els temes interns del PPTX inclouen accents genèrics d'Office de molts colors: la paleta docent de referència és la de la portada.

## Tipografia i disposició

- Llenç **16:9**. En un PPTX nou, usa per defecte `33,87 × 19,05 cm` (13⅓ × 7½ polzades).
- Títols: **Poppins Semibold**; cos: **Arial**; codi: **DejaVu Sans Mono**. Si les fonts no estan disponibles, usa **Liberation Sans Bold / Liberation Sans / Liberation Mono**, respectivament. No confiïs en una substitució silenciosa: comprova les fonts disponibles i utilitza la mateixa combinació a tota la sèrie quan sigui possible.
- Mides orientatives: portada 40–44 pt; títol de diapositiva 30–34 pt; cos 22–26 pt; codi 18–22 pt; peu 11–12 pt. Divideix el contingut quan no hi càpiga amb una mida llegible.
- Marges aproximats d'1,3 cm; títol alineat a l'esquerra i sempre a la mateixa alçada. Reserva una franja inferior per al peu sense superposar-hi contingut.
- Peu habitual: `David Pons · CIFP Pau Casesnoves`, amb número de diapositiva a la dreta. A la portada pot aparèixer l'autoria dins la composició principal.
- Fons majoritàriament blanc, amb accents verds discrets. Mantén constants tipografies, alineacions, estil de requadres i peus al llarg de la presentació.

## Disposicions reutilitzables

Tria la disposició segons el contingut i reutilitza-la al llarg de la sèrie:

- **Portada:** unitat o tema, títol, centre, David Pons i logotip.
- **Inici de bloc:** títol breu i pregunta o propòsit del bloc, amb més espai en blanc.
- **Concepte:** títol que expressi la idea i una explicació breu amb exemple o esquema quan aporti informació.
- **Codi comentat:** fragment de codi editable en un requadre i explicació adjacent o a sota. Ressalta les línies rellevants sense dependre només del color.
- **Recapitulació:** idees apreses o comprovacions relacionades amb el que s'ha programat, quan el contingut ho justifiqui.

Aquestes disposicions no fixen un nombre de diapositives ni obliguen a incloure tots els tipus en cada encàrrec.

## Estil visual del codi

Els fragments de codi han de reconèixer-se visualment com un bloc diferenciat del text explicatiu, amb l'estil següent a tota la sèrie:

- Requadre amb fons `#F3F5F3`, marge interior d'uns 0,35 cm i una línia lateral fina en verd `#175F16`. Fons general de diapositiva blanc.
- Tipografia monoespaiada definida més amunt, normalment 20 pt; no baixis de 18 pt per encabir-hi una classe sencera. Mostra el fragment rellevant o reparteix-lo en diverses diapositives.
- Capçalera discreta amb el nom real del fitxer o classe i, si cal, el llenguatge. Mantén la indentació del projecte i evita retalls o salts automàtics que trenquin la lectura del codi.
- Ressaltat de sintaxi estable: text i operadors `#202124`; paraules clau `#6F2DA8`; tipus i anotacions `#005A9C`; literals de cadena `#175F16`; nombres `#8A4300`; comentaris `#595959`. Aquests colors són una convenció funcional per al codi i complementen els verds corporatius de la diapositiva.
- Ressalta les línies que s'expliquen amb una franja de fons `#DDEEDD` i un marcador lateral o una crida breu. Conserva la sintaxi i el contrast del text; no atenuïs les altres línies fins a fer-les il·legibles.
- En PPTX, aplica el color a fragments de text editables; en HTML o Marp, configura el tema del ressaltador perquè respecti aquesta paleta. Evita captures de l'IDE com a representació habitual del codi.
- Els identificadors dins una frase també van en monoespaiada. Les explicacions llargues van fora del requadre de codi.

## Presentacions per al code along

Totes les unitats del mòdul segueixen el mètode descrit a [l'orientació docent](../../wiki/orientacio-docent.md): el professor prepara un projecte Spring resolt i programa les classes amb l'alumnat, fent pauses per explicar els conceptes.

Quan la presentació acompanyi un projecte concret:

- Organitza els conceptes segons la progressió del codi acordada o documentada.
- Identifica les classes, mètodes o rutes que es comenten amb els noms reals del projecte.
- Mostra fragments petits que es puguin explicar durant una pausa. Mantén indentació i salts de línia; prioritza text editable per al codi.
- Pots marcar les transicions a l'IDE amb una etiqueta coherent, com «Al projecte», i indicar què s'hi observarà.
- Les notes del professor poden recollir el punt d'aturada, l'explicació o la demostració prevista. Marca com a proposta qualsevol seqüència encara no confirmada.

## Producció i revisió

Respecta el format demanat. Si no s'especifica, prefereix un PPTX editable; afegeix una versió PDF si s'ha demanat o si facilita el lliurament. Per a Marp o HTML, trasllada-hi els mateixos colors, proporcions, tipografies i disposicions. Desa els materials al lloc pertinent de `materials/`, amb un nom que identifiqui unitat i tema.

En una edició, conserva el contingut útil i aplica els criteris comuns sense substituir els originals històrics. Consulta els materials i les fitxes de la unitat per concretar el contingut.

Abans de lliurar, revisa el resultat renderitzat: autoria, paleta, llegibilitat, marges, retalls, superposicions, proporcions del logotip, codi i substitucions tipogràfiques. Si no es pot renderitzar, indica aquesta limitació i comprova almenys l'estructura del fitxer; no donis per feta una revisió visual. Una presentació només de guió o esquema no requereix exportació gràfica.
