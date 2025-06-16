# Playwright E2E Setup-Anleitung für `playwright_2/testing`

Diese Anleitung beschreibt Schritt für Schritt, wie Playwright mit TypeScript für End-to-End-Tests in eurem Projekt eingerichtet wird. Die Tests werden im Verzeichnis `playwright_2/testing` abgelegt.

---

## Schritt 1: Playwright mit TypeScript initialisieren

Im Terminal ausführen:

```bash
cd playwright_2/testing
npm init playwright@latest
```


Bestätigt hier alle Abfragen mit den Standardeinstellungen.

Dies erstellt:
- Einen `tests/`-Ordner mit Beispieltests
- Die Datei `playwright.config.ts`
- Einträge in der `package.json`
- Alle benötigten Abhängigkeiten

---

## Schritt 2: Autogenerierte Beispieltests löschen

Die voreingestellten Beispieltests entfernen:

```bash
rm tests/example.spec.ts
rm tests-examples/demo-todo-app.spec.ts
```

---

## Schritt 3: `playwright.config.ts` anpassen

In der Datei `playwright.config.ts` folgendes konfigurieren, damit die Anwendung manuell gestartet werden kann:

```ts
import { defineConfig } from '@playwright/test';

export default defineConfig({
    testDir: './tests',
    timeout: 30000,
    use: {
        baseURL: 'http://localhost:5173',
        headless: true,
        viewport: { width: 1280, height: 720 },
    },
    webServer: undefined // Anwendung wird manuell gestartet
});
```

Damit nutzt Playwright eure lokal gestartete Anwendung, ohne sie automatisch zu starten.

---

## Schritt 4: Backend und Frontend manuell starten

### Anwendung bauen

Baut zu Beginn die gesamte Anwendung, indem ihr im Root-Verzeichnis des Repositories den maven-build startet:

```shell
mvn clean install -DskipTests
```

Im Frontend müsst ihr zusätzlich noch einmal:
 ```shell
 npm install
 npm run build
 ```
ausführen. Dies installiert die Dependencies und generiert euch den Client für euer Backend basierend auf der OpenAPI Spezifikation.

### Backend & Frontend starten

Nutzt IntelliJ oder eure Konsole, um das Backend im Ordner `backend/SqsTestingApplication.jar` zu starten (Wichtig: Damit das Backend läuft, müssen auch die Docker-Container im docker-compose.yaml im backend Modul laufen). Started danach das Frontend, indem ihr im Ordner `frontend` den folgenden Befehl ausführt:

```
npm run dev
```


Stellt sicher, dass das Frontend unter [http://localhost:5173](http://localhost:5173) erreichbar ist und überprüft einmal manuell, dass auch Requests an das Backend geschickt werden, indem ihr bpsw. die Suchfunktion testet.

---

## Schritt 5: Playwright im UI-Modus starten

In einem neuen Terminal:

```bash
cd playwright_2/testing
npx playwright test --ui
```

Damit öffnet sich die grafische Oberfläche von Playwright Test. Hier könnt ihr:
- Alle Tests sehen
- Einzelne Tests ausführen
- Testverläufe visuell nachverfolgen

---

## Schritt 6: Ersten eigenen Test schreiben

Erstelle eine Datei `tests/library.spec.ts` mit folgendem Inhalt:

```ts
import { test, expect } from '@playwright/test';

test('should show page title', async ({ page }) => {
    await page.goto('/');
    await expect(page.getByRole('heading', { name: '📚 Library Dashboard' })).toBeVisible();
});
```

### Hinweis:
- Die Seite muss bereits über `npm run dev` im Frontend verfügbar sein.
- Der Test prüft, ob der Haupttitel korrekt angezeigt wird.

---

Starte jetzt noch einmal die playwright UI und lasse dort deine Tests laufen:

```shell
npx playwright test --ui
```

Jetzt könnt ihr mit weiteren Tests beginnen, z.B. um die Buchlisten und Suchfunktion zu testen.


---

## Schritt 7: Playwright in der CI ausführen (GitHub Actions)

### CI-Konfiguration: `.github/workflows/ci.yml`

Damit die Playwright-Tests automatisch in der CI (GitHub Actions) ausgeführt werden, erweitere deinen Workflow um einen neuen Job `run-playwright-tests`.

### Verwendete Kernfunktionen:

| Funktion                      | Beschreibung                                                   |
|------------------------------|----------------------------------------------------------------|
| `mvn package -DskipTests`    | Baut das Backend, ohne Tests auszuführen                       |
| `docker compose up`          | Startet die Datenbankcontainer für Spring Boot                 |
| `npm run build` + `serve`    | Baut das React-Frontend und startet einen stabilen HTTP-Server |
| `wait-on`                    | Wartet, bis die Anwendung erreichbar ist, bevor Tests starten  |
| `npx playwright test`        | Führt die Tests headless aus                                   |
| `upload-artifact`            | Lädt den HTML-Testreport als CI-Artefakt hoch                  |

---

### Beispiel für CI-Schritte (gekürzt):

```yaml
run-playwright-tests:
  name: Run Playwright UI Tests
  needs: build
  runs-on: ubuntu-latest

  steps:
    - name: Checkout Repository
      uses: actions/checkout@v4

    - name: Set-up Java & Node.js
      uses: actions/setup-java@v4
      with:
        java-version: '17'
        distribution: 'temurin'

    - name: Install Node.js
      uses: actions/setup-node@v4
      with:
        node-version: 18

    - name: Build backend
      working-directory: ./playwright_2/backend
      run: mvn clean package -DskipTests

    - name: Startup database docker container
      run: docker compose -f "./playwright_2/backend/docker-compose.yaml" up -d --build --wait --wait-timeout 30

    - name: Install and build frontend dependencies
      working-directory: ./playwright_2/frontend
      run: |
        npm install
        npm run build

    - name: Serve static frontend build
      working-directory: ./playwright_2/frontend
      run: npx serve dist --listen 5173 &

    - name: Wait for frontend to become available
      working-directory: ./playwright_2/testing
      run: |
        npm install
        npx playwright install --with-deps
        npm install wait-on
        npx wait-on http://localhost:5173

    - name: Run Playwright tests
      working-directory: ./playwright_2/testing
      run: npx playwright test --reporter=html

    - name: Upload test report
      if: always()
      uses: actions/upload-artifact@v4
      with:
        name: playwright-report
        path: playwright_2/testing/playwright-report/
```

---

 **Tipp**: Wenn der Github Workflow durchgelaufen ist, könnt ihr den HTML-Testreport im GitHub UI als Artefakt herunterladen und lokal öffnen (`index.html`).