# 🌦️ Spring Weather App

A Java-based web application that provides real-time weather information using the OpenWeatherMap API. Built with Spring MVC and deployed on Apache Tomcat, this application allows users to search for current weather conditions by city name.

## 🚀 Features

- **User Authentication** – Secure login and registration system.
- **Weather Search** – Retrieve current weather data by entering a city name.
- **Responsive UI** – Clean and responsive interface styled with CSS.
- **API Integration** – Fetches data from the OpenWeatherMap API.
- **Dockerized Deployment** – Easily deployable using Docker and Docker Compose.

## 🛠️ Technologies Used

- Java 17  
- Spring MVC  
- Apache Tomcat 9  
- Maven  
- Docker & Docker Compose  
- Jackson for JSON processing  
- OpenWeatherMap API  

## 📦 Getting Started

### Prerequisites

- Java 17+  
- Maven 3.6+  
- Docker & Docker Compose  

### Clone the Repository

```bash
git clone https://github.com/Evgeni-Kramerov/spring_weather.git
cd spring_weather
```

### Configuration

1. **API Key** – Get an API key from [OpenWeatherMap](https://openweathermap.org/api).
2. **Environment Variables** – Create a `.env` file in the root:

```
WEATHER_API_KEY=your_openweathermap_api_key
```

3. **Docker Compose** – Ensure `docker-compose.yml` loads the `.env`:

```yaml
environment:
  - WEATHER_API_KEY=${WEATHER_API_KEY}
```

### Build and Deploy

1. **Build the WAR file**:

```bash
mvn clean package
```

2. **Run with Docker Compose**:

```bash
docker-compose up --build
```

Visit: [http://localhost:8080/weather](http://localhost:8080/weather)

## 🖥️ Usage

1. Go to `http://localhost:8080/weather`  
2. Register or log in  
3. Enter a city name to search for current weather  

## 🧪 Running Tests

Run tests with:

```bash
mvn test
```

## 📁 Project Structure

```
spring_weather/
├── src/
│   ├── main/
│   │   ├── java/org/ek/weather/
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── images/
│   │       │   └── style/
│   │       └── templates/
├── Dockerfile
├── docker-compose.yml
├── .env
└── pom.xml
```

## 🛡️ Security Considerations

- **API Key** – Add `.env` to `.gitignore`

## 🤝 Contributing

Pull requests are welcome! Fork the repo and make your changes.

## 📄 License

Licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
