## URL Shortening Service

Create a simple **RESTful API** using **Java (Spring Boot)** or **Python (FastAPI)** to shorten long URLs and track basic metrics. Authentication and authorization are not required.

### Entities:
* **ShortUrl:**
    * **ID** (auto-generated)
    * **Original URL** (string)
    * **Short code** (string, unique)
    * **Creation date**
    * **Click count**
    * **Last accessed date**

### API Requirements:
* **POST /urls:** Create a shortened URL. Response must include the short code and original URL.
* **GET /{shortCode}:** Redirect to the original URL and increment click count.
* **GET /urls:** Retrieve a list of all URLs with metrics (**click count, last accessed date**).
* **DELETE /urls/{id}:** Delete a shortened URL

### Technical Requirements:
* **Java (Spring Boot)** or **Python (FastAPI)**
* **Database: PostgreSQL**
* Proper handling of **HTTP redirects (302 status code)**
* Provide a **docker-compose.yml** file to start and manage both the application service and database, allowing startup with a single command.

---

## Additional Feature: Top-N Reporting

Implement an endpoint to retrieve the **top N most-clicked** shortened URLs. The endpoint should support an optional time window filter (e.g., last 7 days, last 30 days).

* **GET /analytics/top?window=7d&limit=10**
    * Returns the top N (default 10) shortened URLs ordered by click count within the specified time window.
    * Each entry must include: **short code, original URL, total clicks (within window),** and **last accessed date.**