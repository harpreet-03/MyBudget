# 💸 MyBudget – Android Expense Tracker App

![Android](https://img.shields.io/badge/platform-Android-green?style=flat-square&logo=android)
![Kotlin](https://img.shields.io/badge/code-Kotlin-orange?style=flat-square&logo=kotlin)
![Build](https://img.shields.io/badge/build-passing-brightgreen?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-blue?style=flat-square)

**MyBudget** is a modern, intuitive, and feature-rich Android app designed to help users manage daily expenses and budgets efficiently. Built using **Kotlin**, **MVVM architecture**, and **SQLite**, it enables smart tracking, voice-based input, insightful analytics, and PDF invoice generation — all from your phone.

---

## Live Demo 📱📸

<p align="center">
  <img src="assets/liveDemo.gif" alt="MyBudget Demo" width="400"/>
</p>

---

## 📱 Features

| Feature | Description |
|--------|-------------|
| 🏠 **Dashboard** | View total expenses, monthly budget, and all transactions in a clean layout |
| ➕ **Add Expense** | Input expenses with category and amount using dropdowns |
| 🎙️ **Voice Input** | Add expenses using speech (category + amount) with live speech recognition |
| 🧾 **Invoice Generator** | Generate PDF invoices with category-wise breakdown |
| 📊 **Analytics** | Interactive pie chart of expenses by category; dynamic totals |
| 🗑️ **Long-Press Delete + Undo** | Delete transactions via long-press with Snackbar undo support |
| 🎯 **Monthly Budget** | Set and edit monthly budget; persistent across sessions |
| 👤 **Profile** | Manage user info and profile image with local storage |
| ☁️ **Offline-First** | All data stored in SQLite and SharedPreferences |

---

## 🧑‍💻 Tech Stack

- **Language**: Kotlin  
- **UI**: XML (ConstraintLayout, Material Components)  
- **Architecture**: MVVM  
- **Database**: SQLite (`SQLiteOpenHelper`)  
- **Speech Input**: `SpeechRecognizer`, live recognition, keyword extraction  
- **PDF Generation**: Android PDFDocument APIs  
- **Persistence**: SharedPreferences  
- **Charts**: [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)  
- **Animations**: View animations, transitions, and Snackbar  

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
```
2. Open in Android Studio
   
	•	Open the project directory
	•	Allow Gradle to sync dependencies
	•	Run the app on emulator or physical device (API 21+)

4. Add Required Permissions in AndroidManifest.xml
   
```<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />```

📂 Project Structure
```
MyBudget/
├── app/
│   ├── java/com/example/mybudget/
│   │   ├── HomeDashBoard.kt
│   │   ├── AddExpense.kt
│   │   ├── AnalyticsActivity.kt
│   │   ├── InvoiceFragment.kt
|   |   ├── SplashScreen.kt
|   |   ├── ExpenseAdapter.kt
|   |   ├── EnterNameActivity.kt
|   |   ├── GetStarted.kt
|   |   ├── Expense
│   │   ├── ProfileActivity.kt
│   │   ├── ProfileBottomSheetFragment.kt
│   │   └── ExpenseDatabaseHelper.kt
│   └── res/
│       ├── layout/ (UI XMLs)
│       ├── drawable/ (icons, backgrounds)
│       ├── anim/ (view animations)
│       └── values/colors.xml, strings.xml, themes.xml
```

🔐 Permissions

The app requests permission to access your gallery to update the profile image:

	•	READ_MEDIA_IMAGES (Android 13+)
	•	READ_EXTERNAL_STORAGE (pre-Android 13)


🧠 Design Highlights

	•	Material Design 3 compliance
	•	BottomNavigationView for intuitive navigation
	•	Use of CardViews, shadows, and custom background drawables
	•	Chips and spinners for smooth category selection


📈 Analytics Module

	•	Expense data grouped by category
	•	Pie chart visualization (MPAndroidChart)
	•	Total spend calculated dynamically from SQLite
	•	Future: Bar chart, spending trends, monthly breakdown


🧾 Invoice Feature

	•	Users can select a custom date range
	•	Generates a PDF invoice of all expenses
	•	Downloaded to device and can be shared
	•	Future: Export via email or Google Drive


👤 Profile Section

	•	User name and email (editable via bottom sheet)
	•	Tap profile picture to update from gallery
	•	Stored locally using SharedPreferences


🧩 Future Integrations (Roadmap)

	•	🔗 Firebase Authentication (for user login)
	•	☁️ Cloud sync of expenses
	•	📅 Monthly / Weekly calendar expense view
	•	📤 Export data to Excel/CSV
	•	📊 Line chart trends by month
	•	🌓 Dark mode toggle
	•	🔔 Budget limit alerts
	•	🔒 Fingerprint/Face Unlock for secure access


🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss the idea.

	1.	Fork the repo
	2.	Create a new branch (git checkout -b feature/feature-name)
	3.	Commit changes (git commit -am 'Add new feature')
	4.	Push to the branch (git push origin feature-name)
	5.	Open a Pull Request


🙌 Acknowledgements

	•	PhilJay’s MPAndroidChart
	•	Android Team & Material Components
	•	Kotlin and Android Open Source Community



🚀 <strong>Made with passion by Harpreet Singh</strong>

<i>“Track your money like you track your goals – every rupee counts.”</i> 💰📊📱

