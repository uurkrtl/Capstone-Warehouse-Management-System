# Lagerverwaltungssystem

Dieses Lagerverwaltungssystem ist eine moderne Anwendung, die entwickelt wurde, um den Bestandsverwaltungsprozess für Unternehmen zu optimieren. Es bietet eine benutzerfreundliche Oberfläche für die Verwaltung von Produkten, Kategorien und Bestellungen, um die Effizienz und Genauigkeit der Lagerverwaltung zu erhöhen. Die Anwendung bietet außerdem verschiedene Reporting-Tools.

Das Projekt ist mit Docker containerisiert und wird mit einer CI/CD-Pipeline über GitHub Actions bereitgestellt. Auf die Anwendung kann unter [ugurkartal.net](https://ugurkartal.net/) zugegriffen werden.
(Benutzername: `demo`, Passwort: `useruser`)

## Funktionen

-   **Produktverwaltung**: Einfaches Hinzufügen, Bearbeiten und Löschen von Produkten.
-   **Kategorienverwaltung**: Organisation von Kategorien.
-   **Bestellverwaltung**: Erfassung und Verwaltung von Bestellungen für einen reibungslosen Ablauf.
-   **Berichterstattung**: Erstellung von Berichten über Produkte mit niedrigem Lagerbestand oder nicht vorrätige Produkte, Lagerbewegung, Produktkaufhistorie, Verkaufszusammenfassung usw.
-   **Lieferantenverwaltung**: Verwaltung der Lieferanteninformationen und -beziehungen für einen effizienten Einkaufsprozess.
-   **Einkaufsverwaltung**: Überwachung und Verwaltung des gesamten Einkaufsprozesses, einschließlich Bestellverwaltung und Lieferantenkommunikation.
-   **Benutzerfreundliche Oberfläche**: Intuitive Benutzeroberfläche für eine einfache Navigation und Bedienung.

Diese Anwendung wird nur mit gültigen Benutzername und Passwort genutzt. Wenn Benutzer Admin Role haben, können auch Benutzerverwaltung Module sehen und nutzen.

Sie enthält einige logische Überprüfungen. Beispielsweise kann ein Produkt, das auf Lager ist, nicht passiv gemacht werden. Das Produkt kann nicht in Mengen verkauft werden, die den Lagerbestand überschreiten. Eine Kategorie, die Produkte enthält, kann nicht passiv gemacht werden.

## Technologien

- Java
- Spring Boot
- MongoDB
- JUnit
- Modelmapper
- Lombok
- React
- Bootstrap
- Docker
- CI/CD Pipeline mit GitHub Actions

## Screenshots

> Homepage

![enter image description here](https://github.com/uurkrtl/Capstone-Warehouse-Management-System/assets/52300746/e7b8c802-1d7f-4f2f-97cc-901bf3e79fdb)

> Produktlistenseite

![enter image description here](https://github.com/uurkrtl/Capstone-Warehouse-Management-System/assets/52300746/0d849a29-ae94-426b-861e-e39a0562677a)

> Produktdetailseite

![enter image description here](https://github.com/uurkrtl/Capstone-Warehouse-Management-System/assets/52300746/5660da01-75f0-4019-ad96-01fb017b3523)

> Berichtsseite

![enter image description here](https://github.com/uurkrtl/Capstone-Warehouse-Management-System/assets/52300746/19138ab4-5265-4c7b-aad2-e291146dbe46)

> Backend-Struktur

![enter image description here](https://github.com/uurkrtl/Capstone-Warehouse-Management-System/assets/52300746/2cde1b6e-8186-44e0-b37e-d0feebe7d6c2)

> Frontend-Struktur

![enter image description here](https://github.com/uurkrtl/Capstone-Warehouse-Management-System/assets/52300746/f37e71b3-f19c-40e2-9504-cca1b8e860d4)