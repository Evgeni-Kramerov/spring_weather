🌦️ Spring Weather App
A Java-based web application that provides real-time weather information using the OpenWeatherMap API. Built with Spring MVC and deployed on Apache Tomcat, this application allows users to search for current weather conditions by city name.

🚀 Features
User Authentication: Secure login and registration system.

Weather Search: Retrieve current weather data by entering a city name.

Responsive UI: Clean and responsive interface styled with CSS.

API Integration: Fetches data from the OpenWeatherMap API.

Dockerized Deployment: Easily deployable using Docker and Docker Compose.

🛠️ Technologies Used
Java 17

Spring MVC

Apache Tomcat 9

Maven

Docker & Docker Compose

Jackson for JSON processing

OpenWeatherMap API

📦 Getting Started
Prerequisites
Java 17+

Maven 3.6+

Docker & Docker Compose

Clone the Repository
bash
Copy
Edit
git clone https://github.com/Evgeni-Kramerov/spring_weather.git
cd spring_weather
Configuration
API Key: Obtain an API key from OpenWeatherMap.

Environment Variables: Create a .env file in the project root:

env
Copy
Edit
WEATHER_API_KEY=your_openweathermap_api_key
Docker Compose: Ensure your docker-compose.yml references the .env file:

yaml
Copy
Edit
environment:
  - WEATHER_API_KEY=${WEATHER_API_KEY}
Build and Deploy
Build the WAR File:

bash
Copy
Edit
mvn clean package
The WAR file will be generated in the target/ directory.

Run with Docker Compose:

bash
Copy
Edit
docker-compose up --build
The application will be accessible at http://localhost:8080/weather.

🖥️ Usage
Navigate to http://localhost:8080/weather.

Register a new account or log in with existing credentials.

Enter a city name in the search bar to retrieve current weather information.

🧪 Running Tests
To execute unit tests:

bash
Copy
Edit
mvn test
📁 Project Structure
css
Copy
Edit
spring_weather/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/ek/weather/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── filter/
│   │   │       ├── http_api/
│   │   │       └── utils/
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── images/
│   │       │   └── style/
│   │       └── templates/
├── Dockerfile
├── docker-compose.yml
├── .env
└── pom.xml
🛡️ Security Considerations
API Key Management: Ensure that the .env file is included in .gitignore to prevent accidental commits of sensitive information.

Git History: If an API key was previously committed, consider using tools like BFG Repo-Cleaner to remove it from the repository history.

🤝 Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.

Feel free to customize this README.md further to match any additional features or configurations specific to your project. Let me know if you need assistance with any other aspects!
