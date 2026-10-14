---
lang: ca-ES
---

# Materials de la UT1 — Curs 2026–2027

**El projecte de referència és [unitat1_2627](unitat1_2627/): és el code along que David Pons ha desenvolupat amb l'alumnat a classe enguany.** Confirmació directa del professor: 2026-10-08.

Per revisar o ampliar els apunts i els exemples de la UT1, contrasta les classes, els mètodes, les rutes i els comportaments amb aquest projecte.

| Directori | Funció |
|---|---|
| `unitat1_2627/` | Projecte realment emprat amb l'alumnat el curs 2026–2027. Referència actual dels exemples. |
| `UT1_2627/` | Proves que el professor va preparar abans de classe. Antecedent de preparació. |
| `unitat1/` | Còpia del code along anterior amb ampliacions preparades. Antecedent de materials. |

Els noms `unitat1_2627` i `UT1_2627` identifiquen directoris diferents i tenen funcions diferents. Conserva als materials els identificadors del codi de classe, inclosos `Unitat1Contoller`, `ShopingCartFactory` i `ShopingCartService`.

## Excepció: `ProvaHashController`

Per indicació expressa del professor, omet `ProvaHashController.java` quan sincronitzis o ampliïs aquests apunts. Correspon a una explicació complementària de `HashMap` per cobrir un coneixement previ que faltava. El fitxer es conserva al projecte; aquesta explicació no s'incorpora al dossier d'introducció a Spring.

## Documents i context

**Estat dels apunts: revisats per indicació de David Pons el 2026-10-08.** El DOCX i el PDF s'han generat del Markdown revisat amb la [skill de documents per a l'alumnat](../../../skills/documents-alumnat-pau-casesnoves/SKILL.md). Són apunts teòrics per lliurar a l'alumnat, amb els exemples del code along i les seves explicacions; no són l'enunciat de la pràctica ni una solució completa del gestor d'informes.

| Fitxer | Contingut i funció |
|---|---|
| [introduccio-a-spring.md](introduccio-a-spring.md) | Font principal del contingut revisat. Les revisions conceptuals i dels exemples es fan aquí. Metadades `status: "Revisat"`, `reviewed: 2026-10-08` i `lang: ca-ES`. |
| [introduccio-a-spring.docx](introduccio-a-spring.docx) | Versió editable dels mateixos apunts, amb estils, taules natives i codi editable; identitat del CIFP Pau Casesnoves i autoria David Pons. Pot rebre edicions manuals: cal comprovar-les abans de sobreescriure'l. |
| [introduccio-a-spring.pdf](introduccio-a-spring.pdf) | Versió de distribució i impressió per a l'alumnat, exportada del DOCX anterior amb LibreOffice. Té 30 pàgines A4, text seleccionable, marcadors de navegació i llengua `ca-ES`. No és una font d'edició independent. |
| [activitat-spring-core-reports.md](activitat-spring-core-reports.md) | Enunciat de la pràctica del gestor d'informes, separat dels apunts. `ReportJobFactory` és el disseny previst per a la solució d'aquesta pràctica; no és una classe del code along dels carrets. |
| [activitat-spring-core-reports.docx](activitat-spring-core-reports.docx) | Editable de l'enunciat de la pràctica, ja existent. No s'ha regenerat en aquesta exportació dels apunts. |
| [activitat-spring-core-reports.pdf](activitat-spring-core-reports.pdf) | PDF de l'enunciat de la pràctica, ja existent. No s'ha regenerat en aquesta exportació dels apunts. |
| [solucio-practica-reports/](solucio-practica-reports/) | Projecte de solució de la pràctica actual, derivat del ZIP de 2025–2026. |

Context docent i procedència:

- [Fitxa del code along impartit](../../../wiki/fonts/code-along-ut1-2627.md): procedència, inventari de classes i rutes, i criteris de sincronització.
- [Fitxa de la UT1](../../../wiki/moduls/0613-desenvolupament-web-entorn-servidor/unitats/ut1-introduccio-servidor.md): desenvolupament docent i traçabilitat curricular.
- [Materials d'U1 del curs anterior](../../../wiki/fonts/materials-ut1-2526.md): antecedents i evolució editorial; les anotacions històriques d'esborrany no descriuen l'estat actual dels apunts.

## Primera proposta d'examen: reprografia

Preparada el 2026-10-14 per revisió de David Pons. Context nou; conserva selecció de dependències, dependència opcional, inicialització diferida i cicle de vida. Incorpora request amb proxy, prototype amb factory i provider i configuració de beans; exclou sessió i carret. Barem **provisional**: sis parts d'1,5 punts i configuració d'1 punt, total 10. El temps disponible confirmat és de 3 hores (180 minuts) per llegir i resoldre; data de realització i materials permesos sense fixar; no consta com a aprovada ni realitzada.

| Fitxer | Funció |
|---|---|
| [examen-ut1-reprografia-proposta-v1.md](examen-ut1-reprografia-proposta-v1.md) | Font de l'enunciat; els comentaris indiquen salts i espai de resposta. |
| [examen-ut1-reprografia-proposta-v1.docx](examen-ut1-reprografia-proposta-v1.docx) | Editable sobre la plantilla del centre, UT1, IFC33C i curs 26/27. Comprovar edicions manuals abans de regenerar. |
| [examen-ut1-reprografia-proposta-v1.pdf](examen-ut1-reprografia-proposta-v1.pdf) | Enunciat compactat pel professor a quatre pàgines el 2026-10-14. Conservar aquesta revisió abans de regenerar. |
| [examen-ut1-reprografia-proposta-v1-criteris.md](examen-ut1-reprografia-proposta-v1-criteris.md) | Document exclusiu del professorat: barem, CA, evidències i referències de correcció. |
| [examen-ut1-reprografia-proposta-v1-solucionari.md](examen-ut1-reprografia-proposta-v1-solucionari.md) | Set enunciats amb respostes Java, justificacions, crèdit parcial i alternatives. Font del solucionari en flux continu, sense salts fixos ni espais de resposta. |
| [examen-ut1-reprografia-proposta-v1-solucionari.docx](examen-ut1-reprografia-proposta-v1-solucionari.docx) i [PDF](examen-ut1-reprografia-proposta-v1-solucionari.pdf) | Versió amb respostes, separada de l'enunciat. PDF exportat d'aquest DOCX. |
| [eines/GeneraExamenReprografia.java](eines/GeneraExamenReprografia.java) | Generador Java amb XML i ZIP estàndard, sense Python. Parteix de la plantilla i rebutja sobreescriure una sortida existent. |

La [fitxa de preparació](../../../wiki/avaluacio/ut1-proposta-examen-reprografia-2026-2027.md) documenta les decisions i l'abast. El generador pren tres arguments: plantilla DOCX, font Markdown i sortida DOCX. Calcula el curs actual amb Europe/Madrid i deixa la data de realització buida; per a una prova amb data o curs diferents cal adaptar els camps. Exportar amb LibreOffice i un perfil temporal separat, amb PDF etiquetat. Conserva taula, marges, imatge, referències de capçalera i peu i camps automàtics de la plantilla. No usar-lo per regenerar altres tipus de materials.

Per al solucionari, afegeix `--solucionari` com a quart argument: identifica les metadades i el peu, tracta els subencapçalaments i manté els blocs curts de codi amb la seva introducció. El PDF d'enunciat ha estat compactat manualment pel professor; els comentaris de salts i espais del Markdown de la primera proposta no representen aquesta maquetació. No regenerar l'enunciat amb aquests comentaris sense sincronitzar abans la revisió del professor. En generar el solucionari s'han conservat intactes tant el PDF com el DOCX d'enunciat.

**Verificació de la primera exportació, 2026-10-14:** 52 paràgrafs i línies de font íntegres al DOCX i PDF, 14 parts XML vàlides, logotip intacte, autoria i llengua comprovades. Aquella exportació tenia set pàgines A4, un apartat per pàgina i espai de resposta; és l'antecedent de la revisió manual posterior del professor, que ara té quatre pàgines. Codi `MDO20301`, fonts incrustades i puntuacions provisionals comprovades sobre 10.

**Verificació del solucionari, 2026-10-14:** 243 paràgrafs i línies literals íntegres al DOCX i PDF, 14 parts XML vàlides, logotip intacte, autoria David Pons, IFC33C, curs 26/27 i llengua `ca-ES`. PDF etiquetat A4 amb fonts Poppins i DejaVu Sans Mono incrustades i numeració automàtica. Quinze tipus Java compilats amb Spring 7.0.9 en un directori temporal, afegint imports i un contracte simulat de `MarcaAigua`; compilació, sense execució del context.

## Abans d'editar o regenerar: instruccions per al futur agent

1. Llegeix aquest README, `wiki/index.md`, `wiki/orientacio-docent.md` i les fitxes de context enllaçades. Verifica **el contingut i la funció de cada fitxer** abans d'editar-lo: teoria, enunciat, editable i PDF tenen finalitats diferents encara que comparteixin conceptes.
2. Per verificar els exemples dels apunts, consulta `unitat1_2627`, respectant l'exclusió de `ProvaHashController`. No substitueixis aquesta referència per les còpies de preparació ni incorporis la pràctica d'informes com si fos codi d'aquest projecte.
3. Mantén el contingut revisat, els casos de callbacks i la distinció entre codi actual i variants. Els apunts són autònoms i no duen bibliografia; les fonts i decisions internes es documenten a la wiki.
4. Abans de regenerar, compara el DOCX existent amb el Markdown i revisa si David Pons hi ha fet canvis manuals. Integra o conserva aquests canvis abans de sobreescriure. El circuit és **Markdown → DOCX → PDF exportat d'aquest DOCX**; qualsevol canvi de contingut o maquetació de l'editable exigeix tornar a exportar el PDF.
5. Aplica la skill del centre, conserva `ca-ES`, autoria i identificadors del codi, i comprova tant el contingut com la maquetació final. L'índex ha d'ocupar una pàgina dedicada i les nou entrades han de ser clicables al DOCX i al PDF. Els apartats principals 1–9 comencen en pàgina nova; els subapartats mantenen la lectura contínua. Actualitza aquesta taula i `wiki/log.md` quan canviï l'estat o la funció d'un document.

## Regeneració dels apunts

[eines/genera-introduccio-spring.ps1](eines/genera-introduccio-spring.ps1) és el generador específic d'aquests apunts, no material per lliurar a l'alumnat. Usa PowerShell i .NET/Open XML per crear el DOCX i LibreOffice per exportar-lo a PDF, sense Python. Requereix Poppins SemiBold, Arial i DejaVu Sans Mono; comprova que estiguin instal·lades. Conserva perfils i fitxers intermedis dins el directori temporal del sistema.

Des de l'arrel del repositori, després de revisar les possibles edicions manuals:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File materials/0613-desenvolupament-web-entorn-servidor/UT1/eines/genera-introduccio-spring.ps1 -Sobreescriu
```

L'opció de política d'execució només afecta aquest procés. El generador admet `-Entrada`, `-Sortida` i `-LibreOffice`; sense `-Sobreescriu` rebutja substituir documents existents. Si canvia la sintaxi del Markdown, revisa també el suport del generador: no és un convertidor Markdown general.

**Verificació de l'exportació del 2026-10-08:** contingut complet del DOCX contrastat amb el Markdown; 14 taules i 44 blocs de codi o esquemes, amb 459 línies conservades literalment. Els 844 paràgrafs no buits de l'editable s'han localitzat al text extret del PDF. S'han comprovat autoria, llengua, fonts, numeració i mida A4, i revisat visualment la primera pàgina, pàgines denses, taules, codi i darrera pàgina. Aquesta verificació documental no acredita l'execució del projecte Java.

**Ajust de navegació i paginació, 2026-10-08:** per indicació del professor, l'índex ocupa exclusivament la pàgina 2 i enllaça els nou apartats. Els inicis d'apartat i les destinacions dels enllaços s'han verificat a les pàgines 3, 5, 6, 10, 11, 18, 21, 25 i 28 del PDF actual, de 30 pàgines. L'índex utilitza hipervincles interns a marcadors, sense números de pàgina escrits manualment. Aquest canvi de maquetació no modifica el contingut del Markdown revisat.

La confirmació del projecte de classe no fixa per si sola sessions, hores efectives, ordre exacte de programació o resultats d'avaluació. Les còpies de preparació es mantenen com a antecedents.
