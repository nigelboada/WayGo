# 📄 Design Document — Trip Management System

## 🗂️ Overview

Aquesta app de gestió de viatges permet a múltiples usuaris iniciar sessió i gestionar els seus viatges de forma privada. Cada usuari pot crear, editar, visualitzar i eliminar viatges associats al seu compte.

---

## 🧩 Data Model

### 1. **User**

Representa un usuari registrat que pot iniciar sessió i gestionar els seus viatges.

```kotlin
data class User(
    val id: Int,
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

## 🗃 Database Schema

S'utilitza Room per gestionar una base de dades SQLite local. L'esquema conté dues taules: users i trips.

### **Entities**

#### 1. **UserEntity**

```kotlin
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val password: String
)

```

#### 2. **TripEntity**

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

## 📦 Room Setup

### AppDatabase.kt

```kotlin
@Database(entities = [UserEntity::class, TripEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tripDao(): TripDao
}
```

## 🧠 Repository Layer

### TripRepository

Encapsula accés a TripDao i fa les conversions entre Trip (domini) i TripEntity (persistència).

```kotlin
suspend fun getAllTripsForUser(userId: String): List<Trip>
suspend fun addTrip(trip: Trip, userId: String)
suspend fun updateTrip(trip: Trip, userId: String)
suspend fun deleteTrip(tripId: Int)
```

### Conversió

```kotlin
// TripEntity → Trip
private fun TripEntity.toTrip() = Trip(...)

// Trip → TripEntity
private fun Trip.toTripEntity(userId: String) = TripEntity(...)
```

## 🔄 Usage Flow

 1. L’usuari inicia sessió (guardant el userId a la sessió).
 2. Quan es carrega TripListScreen, el ViewModel crida loadTrips(userId) per obtenir els viatges.
 3. Només es mostren viatges on trip.userId == currentUserId.
 4. Qualsevol acció de crear/editar es fa passant l’userId a la base de dades.

## 🧪 Notes i Extensions

✅ Seguretat lògica: cada accés passa per userId i no hi ha barreja de dades.

🧩 Extensió fàcil: es pot afegir una taula activities relacionada amb tripId.

🗑️ Reset de la base de dades: pots fer context.deleteDatabase("your_database_name") per esborrar-la.






