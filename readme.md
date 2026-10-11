# Microservices Project

A sample microservices-based e-commerce application built with Java, Spring Boot, Kafka, MySQL, and Docker Compose.

## Overview

This project demonstrates how multiple independent services can work together in a distributed system:

- Product Service handles product catalog operations
- Order Service manages customer orders
- Payment Service processes payment-related workflows
- Notification Service consumes events and handles notifications
- API Gateway exposes the system through a single entry point

The application is orchestrated with Docker Compose and uses MySQL for persistence and Kafka for asynchronous communication between services.

## Architecture

```text
Client
  |
  v
API Gateway (8080)
  |-----------------------> Product Service (8082)
  |-----------------------> Order Service (8081)
  |-----------------------> Payment Service (8083)
  |-----------------------> Notification Service (8084)

Order Service <-----> Kafka
Notification Service <-----> Kafka

Product DB  -> Product Service
Order DB    -> Order Service
Payment DB  -> Payment Service
```

## Running the project
```
cd /to-this-folder
docker compose up --build
```
This will start 3 MySql databases + 1 kafka + 5 services

## Test Flow
```
Step 1: GET http://localhost:8080/api/products
Step 2: POST http://localhost:8080/api/orders with body 
{
    "productId": 1,
    "quantity": 5
}
Step 3: POST http://localhost:8080/api/payments with body
{
    "orderId": 1
}
Step 4: GET http://localhost:8080/api/notifications