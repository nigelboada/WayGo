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
```

### 2. **Trip**

Aquest element representarà un viatge que és creat per algun usuari que fa login a l'aplicació (amb un previ registre).

```kotlin
data class Trip(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val activities: List<Activity> = emptyList()
)
```

📝 Els viatges estan vinculats a un userId intern (via TripEntity) i poden tenir activitats associades (encara que aquestes no estiguin persistides actualment).

🗃️ Database Schema

S'utilitza Room per gestionar una base de dades SQLite local. L'esquema conté dues taules: users i trips.

### **Entities**

# 1. **UserEntity**

```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val password: String
)

```

# 2. **TripEntity**

```kotlin
@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val location: String,
    val startDate: String,
    val endDate: String,
    val userId: String // Foreign key (logical, not enforced)
)
```

🔐 userId actua com a clau forana lògica que vincula els viatges amb un usuari específic. No hi ha una restricció SQL formal, però el filtratge es fa en codi.








