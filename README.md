Android Flag Quiz App
The Flag Quiz App is an interactive mobile application built with Kotlin that tests users' knowledge of world flags. The app uses a local SQLite database to randomly present flag-based quiz questions in an engaging and educational format.


GitHub Repository
https://github.com/bablookumarmuz/Android-Flag-Quiz-App


Features
Randomly generated flag-based quiz questions.
Multiple-choice answers with four options.
Tracks correct, wrong, and unanswered questions.
Displays final results after completing the quiz.
Local SQLite database for offline access.
Clean, responsive, and easy-to-use UI.



Tech Stack
Language: Kotlin

Database: SQLite (preloaded from assets)

Architecture: Fragment-based Navigation

UI: ViewBinding

IDE: Android Studio



 Project Structure
 com.techmania.flagquiz
│
├── database
│   ├── DatabaseCopyHelper.kt
│   └── FlagsDao.kt
│
├── model
│   └── FlagsModel.kt
│
├── view
│   ├── FragmentHome.kt
│   ├── FragmentQuiz.kt
│   ├── FragmentResult.kt
│
├── MainActivity.kt
└── assets
    └── countries.db



Setup Instructions
1. Clone the Repository:
   git clone https://github.com/bablookumarmuz/Android-Flag-Quiz-App.git
2. Open the project in Android Studio.
3. Place the database:
  Ensure that the countries.db file is correctly placed inside the assets directory.
4. Run the project:
   Select an emulator or a physical device.
   Click Run to install and launch the app.
5. Test the App:
  Start the quiz.
  Select answers.
  Review the results at the end.



Database
The SQLite database is preloaded with country names and corresponding flag image names. It is copied from the assets folder to the device storage on the first app launch using a custom DatabaseCopyHelper.



Dependencies
AndroidX Navigation Component

ViewBinding

SQLite


Acknowledgements
This project is inspired by flag quiz games for educational purposes.

Flag icons are sourced from publicly available resources.


License
This project is open-source and available for educational and personal use.
