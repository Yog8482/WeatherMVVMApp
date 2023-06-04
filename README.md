# WeatherMVVMApp

### Weather App with combination of Java and Kotlin Use. MVVM, Retrofit, Clean Architecture with Solid Principles, HILT, Kotlin flow, Coroutines

Below are the details used to construct a weather-based app where users can look up weather for a city.

**Public API**
Create a free account at 
[openweathermap.org](https://openweathermap.org/) 

Just takes a few minutes. Full documentation for the service below is on their site, be sure to take a few minutes to understand it.
https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid={API key}

Built-in geocoding

Used Geocoder API to convert city names and zip-codes to geo coordinates and the other way around. 

Please note that API requests by city name, zip-codes and city id have been deprecated. Although they are still available for use, bug fixing and updates are no longer available for this functionality.

**API call**

https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}

https://api.openweathermap.org/data/2.5/weather?q={city name},{country code}&appid={API key}

https://api.openweathermap.org/data/2.5/weather?q={city name},{state code},{country code}&appid={API key}

### Icons from here:

http://openweathermap.org/weather-conditions

### What you will see in this app?

**native-app-based application to serve as a basic weather app.**
- Weather update screen & Search Screen
- Search screen to allow users to enter a city
- Displayed the information from openweathermap.org API which I thought a user would be interested in seeing.
- Asked the User for location access, If the user gives permission to access the location, then retrieves weather data (if city not selected already )
- Auto-load the weather for last city searched upon app launch.
- If Location permission denied then fallback to search city screen

### Prioritized the following:
* Proper function – requirements met.
* Well-constructed, easy-to-follow, commented code.
* Proper separation of concerns and best-practice coding patterns.
* Defensive code that graciously handles unexpected edge cases.

### How I Coded this?

- [x] API request by city name or cordinates (latitude, longitude)
- [ ] Not Added unit test
- [x] Tried Good simple design
- [x] Industry-standard design pattern adoption (MVVM)
- [x] Adoption of SOLID principles.
- [x] Adopted Retrofit, kotlin flow, state based ui, databinding, HILT (dependency injection), Shared preferences, Jetpack navigation compomemt with single activty usecase
