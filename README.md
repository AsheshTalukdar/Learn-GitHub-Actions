# Selenium + TestNG + Allure — GitHub Actions Demo

A minimal but "real" test automation project. Logs into
[saucedemo.com](https://www.saucedemo.com/) and asserts things on the
inventory page. Built to be copy-pasted into your own private repo and
run as-is, both locally and on GitHub Actions.

## Structure

```
.
├── pom.xml                          # Maven deps + build config (Selenium, TestNG, Allure, WebDriverManager)
├── testng.xml                       # TestNG suite definition
├── .github/workflows/
│   └── selenium-tests.yml           # the CI/CD pipeline
└── src/test/
    ├── resources/
    │   └── settings.json            # browser/url/credentials config
    └── java/com/example/
        ├── utils/
        │   ├── ConfigReader.java    # reads settings.json (+ system property overrides)
        │   └── DriverFactory.java   # creates Chrome/Firefox WebDriver via WebDriverManager
        ├── pages/
        │   ├── BasePage.java        # shared click/type/getText helpers
        │   ├── LoginPage.java
        │   └── InventoryPage.java
        ├── base/
        │   └── BaseTest.java        # driver setup/teardown + auto screenshot on failure
        └── tests/
            └── LoginTest.java       # the actual test cases + assertions
```

No `chromedriver` binary anywhere — `DriverFactory` uses **WebDriverManager**,
which downloads the correct driver version automatically, locally and in CI.

## Run it locally

You need Java 17+ and Maven installed.

```bash
mvn clean test
```

Override browser/headless mode without touching the file:

```bash
mvn clean test -Dbrowser=firefox -Dheadless=false
```

Generate and open the Allure HTML report locally (requires the
[Allure commandline](https://allurereport.org/docs/install/) tool):

```bash
allure serve allure-results
```

## Run it on GitHub Actions

1. Create a **private** repo on GitHub.
2. Copy all these files into it (keep the folder structure, especially `.github/workflows/`).
3. Commit and push to `main`.
4. Go to the **Actions** tab in your repo — the workflow runs automatically.
5. To run it manually with parameters: Actions tab → "Selenium TestNG Allure CI" →
   **Run workflow** button → pick browser/headless → **Run workflow**.
6. After it finishes, open the run → scroll to **Artifacts** → download `allure-report`.

See the chat explanation for a full walkthrough of triggers, parameters, secrets,
and how this maps onto Jenkins concepts.
