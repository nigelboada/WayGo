# 🎯 Estratègia de Ramificació (Branching Strategy) – WayGo

Aquest document descriu l’estratègia de ramificació adoptada per l’equip de desenvolupament del projecte **WayGo** per tal de mantenir una estructura clara, escalable i col·laborativa durant el desenvolupament.

---

## 📁 Estructura general de branques

Utilitzem una estratègia basada en **Git Flow simplificat**, amb les branques següents:

### `main` (o `master`)
- Conté el **codi més estable i llest per a producció**.
- Cada versió publicada (release) surt d'aquí.
- No s’hi fa cap desenvolupament directe.

### `develop` *(opcional, segons escala del projecte)*
- Branca d’integració de funcionalitats.
- Aquí es fusionen les noves funcionalitats abans de passar-les a `main`.

### `sprintXX` (ex: `sprint01`, `sprint02`)
- Branca temporal creada per cada sprint de desenvolupament.
- Pot servir com a branca base per desenvolupar funcionalitats relacionades amb un sprint concret.
- Es fusiona a `develop` o `main` al final del sprint, segons calgui.

### `feature/nom-funcio`
- Per a noves funcionalitats o millores concretes.
- Es creen a partir de `develop` o de la branca de sprint activa.
- Exemples:
    - `feature/login-screen`
    - `feature/navigation-graph`

### `bugfix/nom-bug`
- Per corregir errors detectats durant el desenvolupament.
- Es creen a partir de `develop` (o `main` si és una correcció urgent).

---

## 🔄 Flux de treball

1. Crea una branca nova a partir de la base adequada (`develop` o `sprintXX`):
   ```bash
   git checkout -b feature/nom-funcio
