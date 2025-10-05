# Rick And Morty Test Application #
### Android application that allows viewing a paginated list of characters from the Rick and Morty API. Each character has a dedicated screen displaying detailed information. ###

## ***Main Features*** ##
1. Observing characters list with pagination
2. Caching items for Offline mode 
3. Firltering items by name
4. Sorting in descending or ascending order
5. Navigation to details by clicking on item in the list
6. Displaying full information about the selected character
7. Switching between character information tabs
8. Enabling adding character to favorites or removing from favorites

## ***Architecture:*** ##
Clean architecture with separation into 3 layers: data, domain, presentation

*Presentation Layer:*
MVI architecture for presentation layer
Jetpack Compose for building interface components
Separating logic for main and details screens
Ui Contract for implemention proper UI handing with Compose (State, Event, Effect)
Custom theme

*Domain Layer:*
Use cases for business logic
Repository interfaces
Domain models with neccessary mappers
Di modules for providing domain layer classes 

*Data Layer:*
Repository implementations
Remote data source for fetching paginated list from Api
Local data source for caching received items
Data models and mappers
Di modules for providing data layer classes 

Supports Unit tests for use cases and view models

## ***Technologies*** ##

- Kotlin - Primary programming language
- Paging3 - Supporting pagination
- Room - Caching paginated items
- Jetpack Compose - Building UI
- Coroutines & Flow - Asynchronous programming
- Hilt - Dependency injection
- Retrofit - Network requests
- Material 3 - Design system
- Coil - Retriving images from API
- Moshi - Unit tesing

## ***Screens*** ##
### Main Screen - Character list ###
1. List of Rick And Morty heroes
2. Toggle for sorting items
3. Search bar for filtering items

### Details Screen - Character details ###
1. Character details with information about name, origin, location, episodes, gender, date of creation
2. Add to favourite toggle
