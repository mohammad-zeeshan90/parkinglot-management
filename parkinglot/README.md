**Parking Lot Management System**

This is a Spring Boot-based Parking Lot Management application with H2 in-memory database, Google OAuth2 authentication, and REST APIs for admin and user operations.

Table of Contents
Prerequisites

Setup

Running the Application

H2 Database Console

Testing APIs via Postman

Admin Access

Postman Collection

GitHub Repository

**Prerequisites**

Java 17+
Maven 3.8+
Postman (for testing APIs)

Setup
Clone the repository: 
git clone https://github.com/mohammad-zeeshan90/parkinglot-management
cd parkinglot


Build the project:
mvn clean install


Run the application:
mvn spring-boot:run


The app will start on: http://localhost:8080

H2 Database Console
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:parkinglotdb
Username: sa
Password: (leave empty)

You can view tables like parking_slot, ticket, pricing_rule etc.

Testing APIs via Postman
1️⃣ OAuth2 Authentication

Open Postman → Authorization → Type: OAuth 2.0
Click Get New Access Token
Token Name: GoogleOAuth2Token
Grant Type: Authorization Code
Callback URL: http://localhost:8080/login/oauth2/code/google (or Postman default)

Auth URL: https://accounts.google.com/o/oauth2/auth

Access Token URL: https://oauth2.googleapis.com/token

Client ID: My EMail google CLient ID

Client Secret: MY Email google secret

Scope: openid profile email
Client Authentication: Send as Basic Auth
Click Get Access Token → Login with Google



Copy the ID Token (not access token)

2️⃣ Use ID Token in Requests
In Postman, set Authorization → Type: No Auth

Add Header:

Key: Authorization
Value: Bearer <paste-id-token-here>


Now you can call User APIs and Admin APIs.
3️⃣ Admin Access
Currently, only the email: emailZeeshanTo@gmail.com is assigned ADMIN role
Any other logged-in user will be treated as USER

4️⃣ Available REST APIs
User APIs (/api/v1/users/**)

POST /api/v1/users/entry → Park vehicle

POST /api/v1/users/exit/{ticketId} → Prepare exit & calculate fee

POST /api/v1/users/pay/{ticketId} → Pay ticket

GET /api/v1/users/tickets/{ticketId} → Get ticket info

GET /api/v1/users/slots/availability → Check available slots

Admin APIs (/api/v1/admin/**)
PUT /api/v1/admin/pricing/{id} → Update pricing rules
POST /api/v1/admin/pricing → Add new pricing rule


Admin APIs are restricted and will return 403 Forbidden for non-admin users.
Postman Collection
The collection is included: My Collection.postman_collection.json
Import it in Postman → Update headers with Bearer ID Token as described above.
GitHub Repository
Repository Link (public): https://github.com/mohammad-zeeshan90/parkinglot-management.git

Anyone with the link can clone and run the project.