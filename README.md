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
```
2. Open in Android Studio
	•	Open the project directory
	•	Allow Gradle to sync dependencies
	•	Run the app on emulator or physical device (API 21+)

3. Add Required Permissions in AndroidManifest.xml
```<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />```

📂 Project Structure
```
MyBudget/
├── app/
│   ├── java/com/example/mybudget/
│   │   ├── HomeDashBoard.kt
│   │   ├── AddExpense.kt
│   │   ├── AnalyticsActivity.kt
│   │   ├── Invoice.kt
│   │   ├── ProfileActivity.kt
│   │   ├── ProfileBottomSheetFragment.kt
│   │   └── database/ExpenseDatabaseHelper.kt
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

⸻

🧠 Design Highlights
	•	Material Design 3 compliance
	•	BottomNavigationView for intuitive navigation
	•	Use of CardViews, shadows, and custom background drawables
	•	Chips and spinners for smooth category selection

⸻

📈 Analytics Module
	•	Expense data grouped by category
	•	Pie chart visualization (MPAndroidChart)
	•	Total spend calculated dynamically from SQLite
	•	Future: Bar chart, spending trends, monthly breakdown

⸻

🧾 Invoice Feature
	•	Users can select a custom date range
	•	Generates a PDF invoice of all expenses
	•	Downloaded to device and can be shared
	•	Future: Export via email or Google Drive

⸻

👤 Profile Section
	•	User name and email (editable via bottom sheet)
	•	Tap profile picture to update from gallery
	•	Stored locally using SharedPreferences

⸻

🧩 Future Integrations (Roadmap)
	•	🔗 Firebase Authentication (for user login)
	•	☁️ Cloud sync of expenses
	•	📅 Monthly / Weekly calendar expense view
	•	📤 Export data to Excel/CSV
	•	📊 Line chart trends by month
	•	🌓 Dark mode toggle
	•	🔔 Budget limit alerts
	•	🔒 Fingerprint/Face Unlock for secure access

⸻

🤝 Contributing

Pull requests are welcome! For major changes, please open an issue first to discuss the idea.
	1.	Fork the repo
	2.	Create a new branch (git checkout -b feature/feature-name)
	3.	Commit changes (git commit -am 'Add new feature')
	4.	Push to the branch (git push origin feature-name)
	5.	Open a Pull Request

⸻

📃 License

This project is open source and available under the MIT License.

⸻

🙌 Acknowledgements
	•	PhilJay’s MPAndroidChart
	•	Android Team & Material Components
	•	Kotlin and Android Open Source Community

⸻

🚀 Made with passion by Harpreet Singh

<i>“Track your money like you track your goals – every rupee counts.”<\i> 💰📊📱

