# Setup

1. Starten von Docker
2. Bauen des Webshops
 ```shell
# im Root Verzeichnis
mvn clean install -DskipTests
```
3. Starten des Webshops
```shell
# Startet den Webshop plus Datenbank
cd ./loadtest_3
docker-compose up -d --build --force-recreate webshop
```
4. Testen des Webshops durch curl oder mit `slightly-brittle-webshop/src/test/http/books_controller_request.http`. Das Ergebnis sollte ein leeres JSON Dokument sein
```shell
curl -X GET --location "http://localhost:8080/api/books" \
    -H "Accept: application/json"
```
5. Bauen des k6 Containers (nur einmal notwendig)
```shell
docker-compose build k6
```
6. Starten des k6 Containers
```shell
docker-compose run --rm k6
```

# Übung 01 - Testen des Setups
_____

* Falls noch nicht geschehen, führe die Punkte im Setup durch
* Der `ls` Befehl im k6 Container sollte die Ordner `data` und `scripts` zeigen
  * Unter `scripts` werden die Uebungen gemounted. 
* Führe nun einen einfachen Test aus. Achte beim referenzieren der Scripte auf das führende `./`. So weiß k6, dass das Script auf der lokalen Festplatte liegt
```shell
k6 run ./scripts/uebung01/simpleTest.js 
```
* Das Ergebnis auf der Kommandozeile sollte keine Fehler zeigen
* Aus dem Container kommst du mit `Strg+D`

# Übung 02 - Dein erster eigener Last Test
_____

* Erweitere das Script in Uebung 2, sodass daraus ein Stress Test wird
  * Füge genügend Aufrufe gegen die API des Webshoppes hinzu
  * Der Einfachheitshalber verzichten wir auf Authentifizierung 
* Versuche dabei die Metriken zu interpretieren und das Limit deines Systems zu finden
  * Die Definition des Limits ist dir überlassen, z.B. Antwortzeiten im Schnitt über 2s
  * Ist das mit den hier gezeigten Metriken sinnvoll möglich?

Unter `slightly-brittle-webshop/src/test/http/books_controller_request.http` findest du die API des Webshopes.
Beispiel Daten können über die Methoden `getRandomStore` und `getRandomBook` abgefragt werden. 
Mittels `console.log` kann geloggt werden. 


# Übung 03 - Visualisierung der Ergebnisse
_____

* k6 bietet die Möglichkeiten Metriken und Laufzeitinformationen aufzuzeichen, jedoch nicht darzustellen. Dazu kann Grafana und ein beliebiger Datenspeicher genutzt werden
* Wir verwenden dafür influxDB.
* Starte den Grafana Container. Der InfluxDB Container wird automatisch gestartet
```shell
docker-compose up -d grafana
```
* Starte deinen in Uebung 2 geschriebenen Test wie folgt erneut:
```shell
k6 run --out influxdb=http://influxdb:8086/myk6db ./scripts/uebung02/stressTest.js
```
* Konfiguriere Grafana
* Über den Browser deiner Wahl `localhost:4000` aufrufen
  * Credentials lauten `admin:admin`
* [Add Data Source](https://grafana.com/docs/grafana/latest/datasources/add-a-data-source/)
  * InfluxDB ->
    * URL: `http://influxdb:8086/`
    * Database :`myk6db`
* [Import Dashboard](https://grafana.com/docs/grafana/v9.0/dashboards/export-import/#import-dashboard) -> `id: 10660`
* Öffne das neu erstelle Dashboard
* Versuche jetzt erneut das Limit deines Systems zu finden

Leider ist der Graph zum Anzeigen der Fehler pro Sekunde defekt. Zum Korrigieren bitte auf die Einstellungen des Graphen wechseln und dort die Query wie folgt anpassen:
```
from default http_req_failed where expected_response = false
```

# Übung 04 - Thresholds
_____

* Versuche die Anzahl an virtuellen Nutzer, an denen dein System an sein Limit gerät, mithilfe von [Thresholds](https://k6.io/docs/using-k6/thresholds/) zu bestimmen 

# Übung 05 - Finde das Bottleneck
_____

Im Branches 
* `load-test/uebung-05-01` 
* `load-test/uebung-05-02` 
ist je ein Bug eingebaut worden. Nutze das script in `scripts/uebung05/bottleneckTest.js` und beobachte die Metriken. Kannst du erahnen, wo die Fehler liegen?

Hints: 
* Nach jedem Branch Wechsel sollte der Webshop neu gebaut werden und der Container mit dem Webshop mittels `docker-compose up --d --build --force-recreate webshop` neu gestartet werden 
* Bei Problemen hilft manchmal ein harter neustart des containers. Am schnellsten per `docker stop $(docker ps -q)`
* Manchmal lohnt ein Blick in die logs der Container: `docker log -f <containerId>` 
* Mittels `docker stats` kann der Resourcen Verbrauch der Docker Container beobachtet werden 



# Übung 06 - Open or Closed?
_____

* Schreibe einen Test mit dem Open Model. [k6 Dokumentation](https://k6.io/docs/using-k6/scenarios/arrival-rate/)