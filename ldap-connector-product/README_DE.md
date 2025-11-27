# Active Directory Connector

**Active Directory (AD)** ist ein von Microsoft für Windows-Domänennetzwerke
entwickelter Verzeichnisdienst, der eine zentralisierte Verwaltung und
Authentifizierung von Benutzern, Computern und anderen Ressourcen innerhalb des
Netzwerks ermöglicht. Er verwendet das **Lightweight Directory Access Protocol
(LDAP)** als zugrunde liegendes Protokoll, um Verzeichnisinformationen
abzufragen und zu verwalten. LDAP ist das Standardprotokoll, das AD zur
Authentifizierung und Autorisierung von Benutzern und Ressourcen innerhalb eines
Netzwerks verwendet.

Der Active Directory-** -Konnektor „ **” von Axon Ivy hilft Ihnen dabei,
Initiativen zur Prozessautomatisierung zu beschleunigen, indem er Active
Directory-Objekte in Ihrem Geschäftsprozess abfragt und schreibt. Dieser
Konnektor:

- Ermöglicht Ihnen die einfache Abfrage Ihrer Active Directory-Einträge.
- Bietet Ihnen die Möglichkeit, Attribute von Active Directory-Objekten zu
  ändern.
- Ermöglicht die Erstellung und Hinzufügung neuer Active Directory-Einträge.
- Ermöglicht das Löschen von Active Directory-Objekten
- Bietet eine Demo Implementierung.

## Demo
### Abfrage-Demo
![Active Directory Connector Demo 1](images/screen1.png "Active Directory
Connector Demo 1") ![Active Directory Connector Demo 2](images/screen2.png
"Active Directory Connector Demo 2") ![Active Directory Connector Demo
3](images/screen3.png "Active Directory Connector Demo 3")

### Demo ändern
![Active Directory Connector Demo 4](images/screen4.png "Active Directory
Connector Demo 4") ![Active Directory Connector Demo 5](images/screen5.png
"Active Directory Connector Demo 5")


## Setup

### Einrichten einer Active Directory-Instanz
- Wenn keine vorhandene Active Directory-Instanz verfügbar ist, können Sie
  mithilfe eines Docker-Containers schnell eine neue Instanz einrichten. Eine
  Beispiel-Docker-Compose-Datei finden Sie unter folgendem Pfad:
  `ldap-connector-demo/docker/docker-compose.yaml`. Diese Einrichtung dient zu
  Demonstrations- und Testzwecken. Um den Container zu starten, geben Sie das
  Admin-Passwort in `ldap-connector-demo/docker/docker-compose.yaml` an und
  führen Sie den folgenden Befehl aus:

```
docker-compose up -d
```

- Wenn Docker auf Ihrem lokalen Rechner nicht verfügbar ist, kann ein
  Online-LDAP-Testserver als schreibgeschützte Instanz verwendet werden
  [Forumsys Online LDAP Test
  Server](https://www.forumsys.com/2022/05/10/online-ldap-test-server/).

**Aktualisieren Sie die Active Directory-Verbindungsvariable „** “. Um dieses
Produkt nutzen zu können, müssen Sie mehrere Variablen konfigurieren. Fügen Sie
den folgenden Block zu Ihrer Datei „ `config/variables.yaml“ hinzu, die sich im
Verzeichnis „` “ unseres Hauptgeschäftsprojekts befindet, das dieses Produkt
nutzen wird:

```
@variables.yaml@
```
