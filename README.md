# Hotel Reservation App

A simple hotel reservation system built using Spring Boot. This application allows users to:

- View available rooms for a given period.
- Reserve a room.
- Cancel an existing reservation.

## Features

1. **Check Available Rooms**  
   Endpoint: `GET /api/available-rooms`  
   Description: Returns a list of available rooms for the specified period.

2. **Reserve a Room**  
   Endpoint: `POST /api/reserve`  
   Description: Allows users to make a reservation by providing details such as room ID and the reservation period.

3. **Cancel a Reservation**  
   Endpoint: `DELETE /api/cancel/{id}`  
   Description: Cancels an existing reservation by its ID.

---

## API Documentation

### 1. Get Available Rooms

**Endpoint:**  
`GET /api/available-rooms`

**Request Body:**

```json
{
  "startDate": "yyyy-MM-dd",
  "endDate": "yyyy-MM-dd"
}
```

Response:
Returns a list of available rooms.

Response Example:

```json
[
  {
    "id": 1,
    "description": "Room A"
  },
  {
    "id": 2,
    "description": "Room B"
  },
  {
    "id": 3,
    "description": "Room C"
  }
]
```

---

### 2. Reserve a Room

**Endpoint:**  
`GET /api/available-rooms`

Request Body:

```json
{
  "roomId": "1",
  "period": {
    "checkIn": "2024-12-06",
    "checkOut": "2024-12-10"
  }
}
```

Response:
Returns the reservation details.

Response Example:

```json
{
  "id": 1,
  "roomId": 1,
  "checkIn": "2024-12-06",
  "checkOut": "2024-12-10"
}
```

---

### 3. Cancel a Reservation

**Endpoint:**  
`DELETE /api/cancel/{id}`

Path Variable:

- id - The ID of the reservation to be canceled.

Response:
A confirmation message indicating that the reservation has been canceled.

How to Run:

0. Have the following installed on your system:
- Java JDK
- Maven

1. Clone the repository:

```
git clone https://github.com/nllko/spring-hotel-reservation.git
cd spring-hotel-reservation
```

2.Build the application:

```
mvn clean install
```

3. Run the application:

```
mvn spring-boot:run
```
4. Access the application:

- API Base URL: http://localhost:8080


Technologies Used

    Spring Boot - Backend framework.
    Hibernate/JPA - For database operations.
    H2 Database - In-memory database for testing.
    Maven - Build tool.
