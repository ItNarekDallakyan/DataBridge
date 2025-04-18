# 📦 DataBridge - Secure Multi-App Data Sharing on Android

## Overview
**DataBridge** is a three-module Android system designed for seamless and secure inter-app data sharing using a custom `ContentProvider` backed by `Room`. Traditional mechanisms like `SharedPreferences` fail with app boundaries and size limits — DataBridge solves this by introducing a shared `DataLink` module to bridge `App1` and `App2`.

## 🔧 Modules

| Module     | Description                                                                 |
|------------|-----------------------------------------------------------------------------|
| `App1`     | Source app that writes data to `DataLink`.                                 |
| `App2`     | Consumer app that reads data from `DataLink`.                              |
| `DataLink` | Headless library module (no activities) that exposes a `ContentProvider` and Room DB for secure, scalable data sharing. |

## 🚀 Architecture

```
App1     App2
 │         │
 └───▶ ContentResolver ◀───┐
            │              │
       DataStoreContentProvider
            │
          Room DB
```

- **Storage**: `Room` handles structured persistence.  
- **Access Layer**: `ContentProvider` exposes CRUD operations via URI and permission-bound access.  
- **Security**: Custom `signature`-level permission restricts access to trusted apps only.

## 🔐 Security

Data sharing is protected using:

```xml
<permission
    android:name="narek.dallakyan.PERMISSION_ACCESS_DATA"
    android:protectionLevel="signature" />
```

Apps must:
1. Be signed with the same key.
2. Declare usage with `<uses-permission />`.

## 📂 Example Usage

### Write (App1):
```kotlin
val values = ContentValues().apply {
    put("key", "user_token")
    put("value", "abc123xyz")
}
val uri = Uri.parse("content://narek.dallakyan.datalink.dataProvider/data_store")
context.contentResolver.insert(uri, values)
```

### Read (App2):
```kotlin
val uri = Uri.parse("content://narek.dallakyan.datalink.dataProvider/data_store")
val cursor = context.contentResolver.query(uri, null, null, arrayOf("user_token"), null)
cursor?.use {
    if (it.moveToFirst()) {
        val value = it.getString(it.getColumnIndexOrThrow("value"))
        // Use the value
    }
}
```

## 🛠 Technologies

- 🏗 **Room** (AndroidX) – robust and scalable local database.
- 🔌 **ContentProvider** – native Android IPC layer.
- ⚙️ **Kotlin Coroutines** – async database access in `ContentProvider`.
- 🛡️ **Custom Permissions** – prevents unauthorized access.

## 📦 Build & Run

1. Clone the project.
2. Install all 3 apps on the same device/emulator.
3. Ensure they are signed with the **same certificate**.
4. Trigger data write from `App1` and read from `App2`.

## 📈 Future Improvements

- Support `bulkInsert()` for batch operations.
- Add version control to stored entries.
- Extend MIME types for more structured querying.
- Add unit/instrumentation tests for `DataStoreContentProvider`.

## 👨‍💻 Author

**Narek Dallakyan**  
Senior Android Engineer | Kotlin & Jetpack Compose Enthusiast  
📧 it.narek.dallakyan@gmail.com  
🔗 [LinkedIn](https://www.linkedin.com/in/narekdallakyan/)