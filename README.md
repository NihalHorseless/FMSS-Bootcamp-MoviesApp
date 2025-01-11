# Movie App

Movie App is a movie search and cart application built using Kotlin, Jetpack Compose, and Android Architecture Components. The project demonstrates the use of modern Android development practices, including MVVM architecture, dependency injection with Dagger Hilt, and state management with Kotlin Flows.

## Features

- **Movie Search**: Search for movies by name.
- **Movie Details**: View detailed information about a selected movie.
- **Cart Management**: Add movies to the cart, view cart items, and manage the cart.
- **Order Placement**: Place an order for the movies in the cart.
- 
## Architecture

The project follows the MVVM (Model-View-ViewModel) architecture pattern, which helps in separating the concerns of the application and makes the codebase more maintainable and testable.

- **Model**: Represents the data layer of the application. It includes data classes and repository classes that handle data operations.
- **View**: Represents the UI layer of the application. It includes Composables that define the UI components.
- **ViewModel**: Acts as a bridge between the Model and the View. It holds the UI state and handles the business logic.

## Dependencies

- **Jetpack Compose**: For building the UI.
- **Dagger Hilt**: For dependency injection.
- **Kotlin Coroutines**: For asynchronous programming.
- **Coil**: For image loading.
- **Retrofit**: For network operations.

## Getting Started

### Prerequisites

- Android Studio Ladybug | 2024.2.1 Patch 3
- Kotlin 1.5+
- Gradle 7.0+

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/NihalHorseless/bitirmeprojesi.git
    ```
2. Open the project in Android Studio.
3. Build the project to download the dependencies.

### Running the App

1. Connect an Android device or start an emulator.
2. Click on the "Run" button in Android Studio.

## Usage

### Movie Search

1. Navigate to the Movie Search screen.
2. Enter a movie name in the search bar.
3. View the search results and select a movie to see its details.

### Cart Management

1. Add movies to the cart from the Movie Details screen.
2. Navigate to the Cart screen to view the cart items.
3. Manage the cart by increasing or decreasing the quantity of items or removing items from the cart.

### Order Placement

1. On the Cart screen, click the "Order" button to place an order for the movies in the cart.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

## Acknowledgements

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [Coil](https://coil-kt.github.io/coil/)
- [Retrofit](https://square.github.io/retrofit/)

---

Feel free to customize this README file according to your project's specific details and requirements.
