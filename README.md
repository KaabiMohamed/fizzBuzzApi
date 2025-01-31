# FizzBuzz REST API

## Problem Statement

This project implements a REST API version of the classic **FizzBuzz** problem.  
The original FizzBuzz challenge prints numbers from **1 to 100**, replacing:

- Multiples of **3** with `fizz`
- Multiples of **5** with `buzz`
- Multiples of **both 3 and 5** with `fizzbuzz`

### **Example Output**

```
1,2,fizz,4,buzz,fizz,7,8,fizz,buzz,11,fizz,13,14,fizzbuzz,16,...
```

---

## Task

The API exposes a **REST endpoint** that:

- Accepts **five parameters**:
    - **Three integers**: `int1`, `int2`, and `limit`
    - **Two strings**: `str1` and `str2`
- Returns a list of strings from **1 to limit**, where:
    - Multiples of `int1` are replaced with `str1`
    - Multiples of `int2` are replaced with `str2`
    - Multiples of both `int1` and `int2` are replaced with `str1str2`

---

## Requirements

- The server should be **production-ready**.
- The code should be **easy to maintain** for other developers.

---

## Bonus Feature: API Metrics

The application includes a **statistics endpoint** to track the most frequent API request.

### Statistics Endpoint

- Accepts **no parameters**.
- Returns:
    - **The most used request parameters**.
    - **The number of times this request has been made**.

---

## 🔧 Running Integration Tests

This project includes **integration tests**. Run the following command to execute tests:

```sh
mvn test
```

This will validate the API functionality and ensure correctness.

---

## API Usage (cURL Examples)

### FizzBuzz Request

```sh
curl -X POST "http://localhost:8080/api/fizzbuzz" \
     -H "Content-Type: application/json" \
     -d '{
           "int1": 3,
           "int2": 5,
           "limit": 15,
           "str1": "Fizz",
           "str2": "Buzz"
         }'
```

### Get Most Frequent API Call Statistics

```sh
curl -X GET "http://localhost:8080/api/fizzbuzz/stats"
```

---

## Technologies Used

- **Spring Boot** (REST API framework)
- **Micrometer** (Metrics tracking)
- **JUnit & MockMvc** (Testing framework)
- **Maven** (Build tool)

---

## Author

**Kaabi Mohamed**  
Developed for backend coding challenge.

---



