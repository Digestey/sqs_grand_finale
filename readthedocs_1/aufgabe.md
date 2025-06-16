# Dokumentation mit Sphinx und Read the Docs – Schritt-für-Schritt-Anleitung

## Aufgabe 1: Sphinx einrichten und lokale Dokumentation bauen

### Voraussetzungen

- Git ist installiert
- Python 3 ist installiert (z. B. über pyenv, Homebrew, Systeminstallation)

### Schritt 1: Repository vorbereiten

1. Forke [das Übungsrepository](https://github.com/FlixFix/sqs_grande_finale) und klone deinen Fork lokal auf deinen Rechner.

```bash
git clone https://github.com/DEIN_GITHUB_BENUTZERNAME/sqs_grande_finale.git
cd sqs_grande_finale
```

2. Lege ein Verzeichnis `docs/` für die Dokumentation an:

```bash
mkdir docs
cd docs
```

### Schritt 2: Sphinx installieren

1. Installiere Sphinx:

```bash
pip install sphinx
```

### Schritt 3: Sphinx Quickstart ausführen

```bash
sphinx-quickstart
```

Wichtige Eingaben:

- Root path: `.`
- Separate source and build dirs: `n`
- Project name: Suche dir einen sinnvollen Projektnamen aus
- Author name: Dein Name
- Project release: z. B. `0.1`
- Project language: `en`

Ergebnis: Es werden `conf.py`, `index.rst`, `Makefile` usw. erstellt.

### Schritt 4: Erste Dokumentation erzeugen

```bash
make html
```

Ergebnis: Die HTML-Dokumentation wird in `_build/html/` erstellt.

Öffne z. B. `docs/_build/html/index.html` im Browser. Das kannst Du auch direkt aus der Konsole:

```shell
# auf mac
open docs/_build/html/index.html

# auf linux
xdg_open docs/_build/html/index.html

# auf windows
start docs/_build/html/index.html
```

### Schritt 5: Custom Theme installieren, damit alles bisschen schöner aussieht


In der Datei `docs/requirements.txt` (Falls diese noch nicht existiert, lege sie an):

```
furo
```

Dann lokal installieren:

```bash
pip install -r requirements.txt
```

Ändere den Eintrag html_theme in der Datei `docs/conf.py` so, dass das custom theme auch verwendet wird:

```
html_theme = 'furo'
```

und baue dann alles erneut:


```bash
make html
```


### Schritt 5: Index-Datei anpassen

Standardmäßig verwendet sphinx rst-Files. Du kannst aber auch einfach markdown-Files oder auch beides gleichzeitig verwenden. Damit sphinx markdown parsen kann, musst du den markdown-parser noch in `docs/conf.py` einbinden. Füge dazu folgenden Eintrag unter extensions hinzu:


```
extensions = [
    'myst_parser',
]

```

## Aufgabe 2: Dokumentation mit Read the Docs verbinden

### Schritt 1: Read the Docs verbinden

1. Gehe auf [https://readthedocs.org](https://readthedocs.org) und erstelle einen Account.
2. Verbinde dein GitHub-Konto mit Read the Docs.
3. Klicke auf "Import a Project" und wähle dein Repository aus.
4. Read the Docs erkennt automatisch `conf.py` in `docs/` und baut deine Dokumentation.

Projektseite z. B.: `https://dein-projekt.readthedocs.io/en/latest/`

## Zusatzaufgabe 3: OpenAPI-Dokumentation integrieren

### Schritt 1: OpenAPI-YAML exportieren

Je nach verwendeter Programmiersprache gibt es bereits OpenAPI Generatoren, die euch automatisiert OpenAPI Spezifikationen basierend auf euren Rest-Controllern erzeugen.
Wenn dein Backend z. B. mit Spring Boot (wie hier im Beispielprojekt) arbeitet und Swagger/OpenAPI nutzt, kannst du die API-Dokumentation so exportieren. WICHTIG: Dazu muss deine Anwendung laufen.
Baue und starte also zuerst die Anwendung im Ordner `playwright_2`:

```shell
cd playwright_2
mvn clean install -DskipTests
docker compose up -d
```

Danach solltest du die Anwendung bspw. über IntelliJ oder die Konsole starten können. Lasse dir dann mit folgenden Befehl die OpenAPI Spec generieren:

```bash
curl http://localhost:8080/v3/api-docs.yaml -o docs/openapi.yaml
```

### Schritt 2: Erweiterung installieren

In der Datei `docs/requirements.txt` (Falls diese noch nicht existiert, lege sie an):

```
sphinxcontrib-openapi
```

Dann lokal installieren:

```bash
pip install -r requirements.txt
```

Füge die extension außerdem noch zu den Extensions in `docs/conf.py` hinzu:

```
extensions = [
    ...
    'sphinxcontrib.openapi'
]
```

### Schritt 3: Neue Datei `api.rst` anlegen

Inhalt von `docs/api.rst`:

```
API-Dokumentation
=================

.. openapi:: openapi.yaml
   :request:
   :group:
   :examples:
```

### Schritt 4: In `index.md` einbinden

Bearbeite `docs/index.md`:

```md
# Welcome to the SQS Testing Docs 🧪

```{toctree}
:maxdepth: 2
:caption: Contents:

api.rst
```

### Schritt 6: Alles lokal prüfen

Wenn du nun die folgenden Befehle ausführst, solltest du deine OpenAPI Spezifikation lokal im Browser sehen können:

```bash
make html
open _build/html/index.html # oder xdg_open oder start, je nach Betriebssystem
```

### Schritt 5: Commit und Push

```bash
git add docs/openapi.yaml docs/api.rst docs/index.md
git commit -m "OpenAPI-Dokumentation hinzugefügt"
git push
```

Read the Docs baut beim nächsten Push die aktualisierte Dokumentation.