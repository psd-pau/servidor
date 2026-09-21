# Mòdul de Programació en entorn servidor

## Propòsit

Preparar alumnat de DAW per desenvolupar aplicacions web de servidor funcionals, estructurades i mantenibles.

## Enfocament docent de partida

La tecnologia de referència és Java amb Spring Boot, Spring MVC, Spring Data JPA, Thymeleaf i APIs REST. El valor educatiu no és memoritzar un framework, sinó justificar i aplicar una separació clara de responsabilitats:

`domain → repository → service → controller → view / API`

Les consultes han de retornar les dades necessàries per al cas d'ús; la lògica de negoci viu als serveis; els controladors gestionen HTTP; i la vista conté només la lògica de presentació imprescindible. La seguretat, el logging, la validació, les proves i la documentació es treballen de manera transversal.

## Enllaços

- [[fonts/servidor-master-2526|Font docent de partida]]
- [[moduls/0173-programacio-entorn-servidor|Hub curricular del mòdul]]
- [[matrius/mapa-ra-unitats|Mapa RA–unitats]]
