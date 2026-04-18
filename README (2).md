# MPJ_Hotel_Reservation_System

## Hotel Reservation System

- A full-stack mobile hotel booking application developed as a Java Mini
Project. The system allows users to register, log in, search available
rooms, make bookings, manage reservations, and request hotel services
through an Android application connected to a Spring Boot backend with
MySQL database support.



## Project Overview

- The Hotel Reservation System was developed to digitize and simplify the
traditional hotel booking process. The project provides customers with
an easy-to-use mobile platform for room reservations while allowing
efficient backend processing of bookings and room availability.



## Key Features

- User Registration and Login - Search Rooms by Availability - Book
Hotel Rooms - View My Bookings - Cancel Bookings - Dynamic Room
Availability Update - Do Not Disturb (DND) Requests - Cleaning / Service
Requests - Mobile Friendly Android Interface - REST API Based
Architecture



## Technologies Used

### Frontend (Android)

- Kotlin 
- Android Studio 
- XML Layout 
- RecyclerView 
- Material Design 
- Retrofit 
- Gson

### Backend

- Java 
- Spring Boot 
- Spring Data JPA 
- Hibernate 
- Maven

### Database

- MySQL

### Deployment

- Docker 
- Docker Compose 
- GitHub



## System Architecture

 text Android Application <br>
↓  <br>
 REST API Requests <br>
↓ <br>
  Spring Boot Backend<br>
↓<br>
 Service Layer<br>
 ↓ <br>
 Repository Layer <br>
 ↓ <br>
 MySQL Database <br>
 
 
 ## Modules
 - Customer
- Register user 
- Login user 
- Session handling 
- Room Module View
    - available rooms 
    - Search rooms 
- Booking Module 
    - Create booking 
    - View booking history 
    - Cancel reservation 
- Service Request Module 
    - DND mode Cleaning
    - request Admin / Data Layer 
- Room inventory management 
- Booking storage
- Availability tracking 
  
Instructions Clone Repository git clone
https://github.com/Milind-Muravane/MPJ_Hotel_Reservation_System.git

Backend Setup Run using Docker docker-compose up \--build Backend runs
on:

http://localhost:8080 Android Setup Open Android Studio Open android-app
folder Update BASE_URL in RetrofitClient.kt For emulator:
http://10.0.2.2:8080/ For physical device:
http://YOUR_IP:8080/ Run application Public Access Demo (Optional) Use
ngrok:
ngrok http 8080 Use generated HTTPS URL inside Android BASE_URL. Sample
Workflow User registers account User logs in Searches room availability
Selects room Makes booking Booking stored in database User views booking
in My Bookings User may cancel booking or request services Learning


Outcomes
- Android API Integration 
- REST API Development
-  Database Design
- Docker Deployment 
- Full Stack Application Development 


## Team Collaboration
 Team Members 
- Tanmay Dhumal 
- Saaransh Johri
- Milind Muravane 
- Aditya Kiran 
  
## Future Enhancements
- Payment Gateway
- Integration Admin Dashboard
-  AI Room Recommendation Notification System
- OTP Authentication 
- Cloud Deployment 
- Analytics Dashboard 

## Conclusion:
 The Hotel Reservation System successfully demonstrates a complete full-stack
software solution integrating Android frontend, Java backend, and MySQL
database. The project improves booking efficiency, user convenience, and
showcases practical implementation of modern software development
concepts.

 Author
 - Hotel Reservation App Project Team
