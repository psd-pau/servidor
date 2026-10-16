---
lang: ca-ES
---

# UT2 — Fitxers de suport

CIFP Pau Casesnoves · David Pons · IFC33C · Curs 2026–2027

Copiau la carpeta `src/` a l'arrel del vostre projecte Spring, al costat de `pom.xml`, conservant aquesta estructura:

```text
src/
├── main/resources/
│   ├── application.properties
│   └── data.sql
└── test/http/
    ├── book_constraints.http
    ├── book_pagination.http
    ├── book_read.http
    ├── book_write.http
    ├── test-api.http
    └── university.http
```

Si ja teniu un `application.properties`, revisau-ne el contingut abans de substituir-lo. La configuració inclosa prepara H2 en memòria i carrega les dades de demostració de `data.sql` a cada arrencada, quan ja s'han creat les taules de les entitats.

Arrencau l'aplicació abans d'executar les peticions dels fitxers `.http` des del client HTTP de l'IDE. Les peticions necessiten les classes i les rutes que programarem durant el code along; aquest paquet conté només els fitxers de suport.
