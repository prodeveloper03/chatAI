
 
 # chatAI

# Set Up Instruction:
-> git clone https://github.com/prodeveloper03/chatAI.git <br>
-> Switch to master branch <br>
-> Android Studio Meerkat | 2024.3.1 or above <br>
-> AGP : 8.4.0 <br>
-> JDK - 17 <br>

# Install the App 
-> Build through Android Studio <br>
-> APK: https://drive.google.com/file/d/1WB7jp2_pwOjaZbwIjnzjZQy47_UNgv10/view?usp=sharing <br>


---------------------------------------------------
                                             Architecture
---------------------------------------------------

# MVVM + Clean Architecture
Combines best practices: reactive UI with ViewModels, clean separation of layers, and easier debugging.<br>

<img width="1038" height="923" alt="image" src="https://github.com/user-attachments/assets/eae27646-dff4-4399-8389-a2e9792fa754" />

# Separation of Concerns
-> Each layer has a single responsibility. UI only observes ViewModels; ViewModels only  Repositories manage data.<br>

# Testability
-> Business logic lives in domain and repository, which can be tested independently of Android UI.<br>

# Scalability
-> Feature-specific UI modules (chat, home) make it easy to add new features without modifying existing code.<br>

# Maintainability
-> DI and utils ensure easy swapping of implementations (e.g., local DB vs remote API).<br>

---------------------------------------------------
                                             Layered Structure
---------------------------------------------------

# activity
-> Contains Android Activities (entry points/screens)<br>
-> Responsibilities: Hosting composables, managing navigation, observing ViewModels.<br>

# data
-> Handles data sources and repositories.<br>

# Subfolders:
-> local: Database, SharedPreferences, or any offline storage.<br>
-> repository: Single source of truth for data. Combines local and remote sources.<br>

# domain
-> Contains business logic, use-cases, and models specific to your app.<br>
-> Keeps app logic independent of frameworks (e.g., Compose, Retrofit).<br>

# di
-> Dependency injection setup using Hilt/Manual DI.<br>
-> Provides instances of repositories, use-cases, network clients, etc.<br>

# navigation

-> Encapsulates navigation graph, routes, and deep links.<br>
-> Separates navigation logic from UI for maintainability.<br>

# ui
-> Composable screens and components.<br>

# utils
-> Helper classes or extension functions (e.g., formatters, validators).<br>





----------------------------------------------------
                                              APP DEMO AND SCREENSHOTS
----------------------------------------------------                   
  
▶️ [Watch Demo Video](https://drive.google.com/file/d/1JrZDfCdwsD0V4W94_48cJ-UWQjH7_anz/view?usp=sharing)







---------------------------------------------------  

<img width="433" alt="Screenshot 2021-05-15 at 7 53 05 PM" src="https://github.com/user-attachments/assets/66ed44c3-5285-4397-8254-7a03e049cb92">




---------------------------------------------------  
<img width="432" alt="Screenshot 2021-05-15 at 7 53 28 PM" src="https://github.com/user-attachments/assets/8ea758ef-6164-46dc-b15d-6755d6f2e306">




---------------------------------------------------  
<img width="430" alt="Screenshot 2021-05-15 at 7 53 50 PM" src="https://github.com/user-attachments/assets/9365d53e-4ac8-43e2-9345-d17547b72581">



---------------------------------------------------  
<img width="432" alt="Screenshot 2021-05-15 at 7 53 28 PM" src="https://github.com/user-attachments/assets/5d883489-49b7-4755-87fe-8e4dddd26dec">



---------------------------------------------------  
<img width="432" alt="Screenshot 2021-05-15 at 7 53 28 PM" src="https://github.com/user-attachments/assets/fe74e255-8c45-4b1b-a088-93c246db2030">



---------------------------------------------------  
<img width="432" alt="Screenshot 2021-05-15 at 7 53 28 PM" src="https://github.com/user-attachments/assets/204ffab2-800d-44a9-b657-a6d3f36843d1">




---------------------------------------------------  
<img width="432" alt="Screenshot 2021-05-15 at 7 53 28 PM" src="https://github.com/user-attachments/assets/e998cab6-787d-4417-a778-fe18ec941c2a">





---------------------------------------------------  
List of bonus features implemented
---------------------------------------------------  
-> Message bubble appearance <br>
-> Smart timestamp formatting <br>
-> Typing indicator animation <br>
-> Pagination support<br>
-> Image caching<br>
-> Image compression before saving<br>
-> Long-press message to copy text<br>
-> Navigation transition animation<br>
-> Bottom Sheet<br>

