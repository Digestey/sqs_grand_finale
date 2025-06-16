# reStructuredText Cheatsheet für Sphinx & Read the Docs

---

## Allgemeine Syntax

### **Überschriften**
```rst
======================
H1 (gleiches Zeichen)
======================

Überschrift 2
-------------

Unterüberschrift
^^^^^^^^^^^^^^^^
```

### **Textformatierung**
```rst
*kursiv*, **fett**, ``Inline-Code``

:ref:`Link zu Abschnitt <abschnitt-id>`
`Externer Link <https://example.com>`_

.. note:: Das ist ein Hinweis.
.. warning:: Vorsicht!
```

### **Listen**
```rst
- Punkt 1
  - Unterpunkt

1. Nummeriert
2. Liste

Definition List:
Begriff
  Definition
```

### **Codeblöcke**
```rst
.. code-block:: python

   def hello():
       print("Hallo Welt")
```

```rst
.. code-block:: bash

   pip install -r requirements.txt
```

### **Bilder**
```rst
.. image:: images/logo.png
   :alt: Mein Logo
   :width: 200px
```

### **Tabellen (Grid)**
```rst
+------------+------------+
| Spalte 1   | Spalte 2   |
+============+============+
| Inhalt A   | Inhalt B   |
+------------+------------+
```

---

## Sphinx-Spezifisch

### **Table of Contents**
```rst
.. toctree::
   :maxdepth: 2
   :caption: Inhalt:

   einleitung
   api
   benutzung
```

### **Automatische API-Dokumentation**
```rst
.. automodule:: mypackage.mymodule
   :members:
   :undoc-members:
```

### **NumPy/Google Docstrings mit napoleon**
```python
def hello(name):
    """Gibt eine Begrüßung zurück.

    Parameters
    ----------
    name : str
        Der Name des Nutzers

    Returns
    -------
    str
        Begrüßungstext
    """
```

### **To-Do Erweiterung**
```rst
.. todo::
   Diese Methode muss noch dokumentiert werden.

.. todolist::
```

### **Intersphinx Links**
```python
intersphinx_mapping = {
    'python': ('https://docs.python.org/3', None),
}
```
```rst
Siehe auch :class:`python:dict`
```

---

## OpenAPI Rendering Extension

### Beispiel: `.rst` mit eingebetteter OpenAPI-Dokumentation

```rst
.. openapi:: ./openapi.yaml
   :rendering: interactive
   :hide-hostname:
```

### Optionen:
- `:rendering:` → `interactive` oder `redoc`
- `:hide-hostname:` → optional, blendet Serveradresse aus
- `openapi.yaml` muss Teil des Repos sein (z. B. im `docs/`-Ordner)

### Abhängigkeit (in `requirements.txt`)
```txt
sphinxcontrib-openapi
sphinxcontrib-redoc  # optional für Redoc-Rendering
```

### Aktivierung in `conf.py`
```python
extensions = [
    'sphinxcontrib.openapi',
    'sphinxcontrib.redoc',
]
```

---

## requirements.txt – warum?

- Wird von Read the Docs verwendet, um Python-Umgebung aufzubauen.
- Muss enthalten:
```txt
sphinx
sphinx-rtd-theme
sphinxcontrib-openapi
```

---

## Nützliche Direktiven im Überblick

| Direktive             | Funktion                            |
|-----------------------|-------------------------------------|
| `.. toctree::`        | Inhaltsverzeichnis                  |
| `.. automodule::`     | API-Doku aus Python-Modul           |
| `.. code-block::`     | Syntax-Highlighting für Code        |
| `.. image::`          | Bild einfügen                       |
| `.. note::`           | Hinweisbox                          |
| `.. todo::`           | Offene Punkte sichtbar machen       |
| `.. openapi::`        | OpenAPI YAML/JSON einbinden         |
