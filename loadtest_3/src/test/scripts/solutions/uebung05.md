#### Lösung Uebung 5

In der Applikation wurden drei Fehler eingebaut:

1. Transaction falsch gesetzt

Die Annotation `Transactional` im Application Layer besagt, das der nachfolgende Code und, in diesem Kontext, alle Zugriffe auf die Datenbank, teil einer Transaktion sein sollen. Damit kann bei Fehlern der komplette Use Case einfach zurückgerollt werden.
Zusätzlich definieren wir einen Rahmen, wie wir auf die Datenbank zugreifen, siehe [Isolation Levenl bei Transaktionen](https://learn.microsoft.com/en-us/sql/odbc/reference/develop-app/transaction-isolation-levels?view=sql-server-ver16).

In der ursprünglichen Implementierung wurde der Default von Postgres verwendet, also Read-commited. Auf dem defekten Branch haben wir uns in der Klasse `SearchApplicationImpl` für `Serializable` entschieden. Dadurch werden Transaktionen seriell durchgeführt.
2. Synchronized

Hier wurde in der Klasse `OrderApplicationImpl` die Methoden mittels synchronized künstlich limitiert.


