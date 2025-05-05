# 📄 Design Document — Trip Management System

## 🗂️ Overview

Aquesta app de gestió de viatges permet a múltiples usuaris iniciar sessió i gestionar els seus viatges de forma privada. Cada usuari pot crear, editar, visualitzar i eliminar viatges associats al seu compte.

---

## 🧩 Data Model

### 1. **User**

Representa un usuari registrat que pot iniciar sessió i gestionar els seus viatges.

```kotlin
data class User(
    val id: String,
    val username: String,
    val password: String
)
