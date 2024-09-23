#Recipe Management Application
This is a web-based Recipe Management Application built using Spring MVC and Spring Security. The application allows users to manage their recipes, including adding, viewing, and deleting recipes,
while incorporating user authentication and profile management.

Features:
1.User Registration & Authentication:
  Users can register and log in to manage their recipes.
  
2.Recipe Management:
  Add new recipes with name, ingredients, and instructions.
  View, search, and delete existing recipes.
  Recipes are categorized, and users can search by category.

3.User Profile:
  Users can view and edit their profile (username, email, and password).
  Profile deletion with confirmation, which logs out the user upon deletion.

4.Security:
  Only logged-in users can access the application. Logged-out users cannot view or manage any recipes.
  Password validation and secure login.

5.Styling:
  The UI is styled using CSS, with a light pink theme for the background and buttons.
  Responsive forms and buttons with hover effects.
  
#Technologies Used
1.Spring Framework:
  Spring MVC for handling the web layer.
  Spring Security for managing user authentication and authorization.
  
2.H2 Database:
  In-memory database for storing recipes and user details.
  
3.Thymeleaf:
  Template engine for rendering dynamic HTML pages.
  
4.CSS:
  Custom styling for forms, buttons, and layout.
  
#How to Run
Clone the repository:
git clone https://github.com/.git
cd repo-name

Run the application:
Open the project in an IDE like IntelliJ.
Ensure you have JDK 21 installed.
Run the application using the embedded server (e.g., Tomcat).

Access the Application:
Open your browser and navigate to http://localhost:8080/.
Register a new user or log in with an existing user.

Database Access
The application uses an H2 in-memory database.
You can access the H2 console at http://localhost:8080/h2-console with the default credentials found in the application configuration.

Screenshots
Login Page
Recipe List
Add Recipe Form
Profile Management

Future Improvements
Implement pagination for recipe listings.
Add image upload functionality for recipes.
Expand on the search feature (e.g., by ingredients).
Deploy to a cloud platform like Heroku or AWS.

Contributing
Feel free to fork this repository and contribute to its development. Submit pull requests with enhancements or bug fixes.


