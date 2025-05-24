# 💸 MyBudget – Android Expense Tracker App

![MyBudget Banner](https://img.shields.io/badge/Budget-Tracker-blueviolet?style=for-the-badge&logo=android&logoColor=white)

**MyBudget** is a modern and visually appealing Android app designed to help users manage their daily expenses with ease. It enables tracking, analyzing, and managing personal finances on-the-go using beautiful charts and intuitive UI, built using Kotlin and SQLite.

---

## 📱 Features

| Feature | Description |
|--------|-------------|
| 🏠 **Dashboard** | View total expenses and all transactions in a clean, card-based layout |
| ➕ **Add Expense** | Input expenses with category and amount using smart dropdown UI |
| 📊 **Analytics** | Interactive pie chart with category-wise spending and total summary |
| 📄 **Invoice Generator** | Generate PDF invoices of expenses for any selected date range |
| 👤 **Profile** | View/edit user info, update profile picture from gallery |
| ☁️ **Persistent Storage** | All data stored in local SQLite, with shared preferences for profile |
| ⚙️ **Clean Architecture** | Built using MVVM principles, modular and scalable |

---

## 🧑‍💻 Tech Stack

- **Language**: Kotlin  
- **UI**: XML (ConstraintLayout, Material Components)  
- **Database**: SQLite (via `SQLiteOpenHelper`)  
- **Charts**: [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)  
- **PDF Generation**: Android PDF APIs  
- **Persistence**: SharedPreferences  
- **Animations**: View animations + custom transitions  

---

## 📸 Screenshots

| Home Dashboard | Add Expense | Analytics |
|----------------|-------------|-----------|
| ![Home](assets/home.png) | ![Add](assets/add.png) | ![Analytics](assets/chart.png) |

---

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/harpreet-03/MyBudget.git
cd MyBudget

2. Open in Android Studio
	•	Open the project directory
	•	Allow Gradle to sync dependencies
	•	Run the app on emulator or physical device (API 21+)

3. Add Required Permissions in AndroidManifest.xml
`<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />`
