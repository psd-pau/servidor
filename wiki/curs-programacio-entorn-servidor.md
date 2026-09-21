# 0613. Desenvolupament web en entorn servidor

## Propòsit

Preparar alumnat de DAW per desenvolupar aplicacions web de servidor funcionals, estructurades i mantenibles.

Per al curs 2026–2027, la [[fonts/programacio-didactica-2026-2027|programació del centre]] preveu 230 hores, a raó de 7 hores setmanals. El codi 0613 consta al [RD 405/2023](https://www.boe.es/eli/es/rd/2023/05/29/405).

## Enfocament docent de partida

La tecnologia de referència és Java amb Spring Boot, Spring MVC, Spring Data JPA, Thymeleaf i APIs REST. El valor educatiu és justificar i aplicar una separació clara de responsabilitats:

`domain → repository → service → controller → view / API`

Les consultes han de retornar les dades necessàries per al cas d'ús; la lògica de negoci viu als serveis; els controladors gestionen HTTP; i la vista conté només la lògica de presentació imprescindible. La seguretat, el logging, la validació, les proves i la documentació es treballen de manera transversal.

La [[orientacio-docent|orientació docent]] desenvolupa aquests principis i les pràctiques personals recollides al ZIP original.

## Enllaços

- [[presentacio-modul|Presentació inicial del mòdul per a l'alumnat]].

- [[fonts/servidor-master-2526|Font docent de partida]]
- [[orientacio-docent|Orientació docent detallada]]
- [[fonts/programacio-didactica-2026-2027|Programació didàctica 2026–2027]]
- [[moduls/0613-desenvolupament-web-entorn-servidor|Hub curricular del mòdul]]
- [[matrius/mapa-ra-unitats|Mapa RA–unitats]]
