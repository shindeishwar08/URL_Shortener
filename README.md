# URL Shortener with Analytics

A robust backend service built with Java and Spring Boot to shorten URLs and track click analytics.

## ✨ Features

-   **URL Shortening:** Generate a unique, short alias for any valid URL.
-   **Redirection:** Redirect users from the short URL to the original long URL.
-   **Click Analytics:** Silently tracks every click, capturing IP address and User-Agent.
-   **Custom Aliases:** Allows users to choose their own short codes.
-   **URL Expiry:** Allows setting an optional expiration date for shortened links.
-   **Input Validation:** Rejects invalid or malformed URLs with clear error messages.

## 💻 Tech Stack

-   **Backend:** Java 17, Spring Boot 3
-   **Database:** PostgreSQL
-   **Build Tool:** Maven
-   **Core Libraries:** Spring Web, Spring Data JPA, Hibernate, Lombok

---

## 🚀 How to Run Locally

### Prerequisites

-   Java 17 or later
-   Maven
-   PostgreSQL

### Steps

1.  **Clone the repository:**
    ```bash
    git clone <your-repo-url>
    cd url-shortener
    ```

2.  **Setup the database:**
    -   Start your PostgreSQL server.
    -   Create a new database named `url_shortener`.

3.  **Configure the application:**
    -   Open `src/main/resources/application.properties`.
    -   Update `spring.datasource.username` to match your PostgreSQL username.

4.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will be accessible at `http://localhost:8080`.

---

## 📖 API Documentation

### 1. Create a Short URL

Creates a new short URL. A custom alias and an expiry date are optional.

-   **Method:** `POST`
-   **Endpoint:** `/api/v1/url`
-   **Success Response:** `201 Created`

#### Request Body (JSON)

| Field       | Type          | Description                                         | Required |
| :---------- | :------------ | :-------------------------------------------------- | :------- |
| `longUrl`   | `String`      | The original, valid HTTPS URL to be shortened.      | Yes      |
| `alias`     | `String`      | A custom short code for the URL.                    | No       |
| `expiresAt` | `LocalDateTime` | The optional expiry date in `YYYY-MM-DDTHH:MM:SS` format. | No       |

**Example Request:**

```json
{
  "longUrl": "[https://www.google.com/search?q=backend+development](https://www.google.com/search?q=backend+development)",
  "alias": "my-search",
  "expiresAt": "2026-12-31T23:59:59"
}
````

#### Success Response (JSON)

**Example Response:**

```json
{
  "shortUrl": "http://localhost:8080/api/v1/my-search"
}
```

-----

### 2\. Redirect to Original URL

Redirects a short URL to its original long URL and records the click for analytics.

  - **Method:** `GET`
  - **Endpoint:** `/api/v1/{shortCode}`
  - **Success Response:** `302 Found` (Redirect)

#### Path Parameters

| Parameter   | Description                        |
| :---------- | :--------------------------------- |
| `shortCode` | The short code or custom alias.    |

#### Response Details

If the `shortCode` is valid and not expired, the server returns an empty response with a `302 Found` status code and a `Location` header pointing to the original long URL. If the `shortCode` does not exist, it returns a `404 Not Found` error. If the link has expired, it returns a `410 Gone` error.

-----

## 🔮 Future Enhancements

  - Implement a global exception handler for consistent JSON error responses.
  - Add robust IP address resolution using the `X-Forwarded-For` header for production environments.
  - Introduce a `do-while` loop for random code generation to guarantee uniqueness in high-traffic scenarios.


```
```