---
lang: ca-ES
---

# Solució de la pràctica d'informes d'UT1

Projecte Maven amb Spring Boot. La classe d'entrada és `SolucioPractica1Application`.

Per provar-lo, executa `./mvnw spring-boot:run` i consulta `/report/pdf`, `/report/html`, `/report/csv`, `/report/stats` i `/report/export`. `ReportService` coordina la generació. `ReportJobFactory` demana un treball nou a Spring en cada informe; `ReportCache` conserva els informes i `ReportStatistics` en compta els nous.

`ReportConfig` declara `CSVFormat` amb `@Bean` perquè és una classe d'Apache Commons CSV que no podem anotar amb `@Component`. El generador rep aquest format per constructor i crea un `CSVPrinter` per a cada informe. Si es canvia el separador a `ReportConfig` i es reinicia l'aplicació, la sortida CSV canvia sense tocar `CsvReportGenerator`.

La marca d'aigua és opcional. Per observar la generació sense marca, es pot desactivar temporalment el bean `WatermarkService` i tornar a arrencar l'aplicació.
