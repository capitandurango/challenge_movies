# challenge_movies
Movies Challenge

## 1. Scope
This application works with the following statements:
- Scroll the list of the ***discovered*** movies when you scroll until the bottom to the list the app retrieves 20 more films.
- When you selected a movie show the information:
 - Overview
 - Runtime ***If exist and is different from Zero***
 - Link to the movie homepage ***If exist for the movie***
- A search bar is allowing the user to enter the name of a film and display it on the screen.

## 2. Instructions.
The application works in portrait and landscape mode.

When you run the portrait mode, the application has two screens.

When you run in landscape mode, the application only has one screen.


## 3. Architecture.
I decide to work with MVVM pattern because, in this way, I take advantage of everything Android has to offer.

## 4. Third libraries.
- Architecture Components: I use these libraries for the correct implementation of the MVVM pattern (LiveData and ViewModel).   
- Retrofit: This library to create an HTTP request to the endpoint.
- Glide: These libraries help to download images.