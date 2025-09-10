#!/bin/bash

# Student Management API Test Script
# Make sure the Spring Boot application is running on localhost:8080

echo "=== Student Management API Test ==="
echo ""

BASE_URL="http://localhost:8080/api/students"

echo "1. Creating a new student..."
echo "POST $BASE_URL"
curl -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson",
    "rollNo": 1001,
    "branch": "Computer Science",
    "course": "B.Tech"
  }' | jq '.'

echo ""
echo ""

echo "2. Creating another student..."
curl -X POST $BASE_URL \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Bob Smith",
    "rollNo": 1002,
    "branch": "Information Technology",
    "course": "B.Tech"
  }' | jq '.'

echo ""
echo ""

echo "3. Getting all students..."
echo "GET $BASE_URL"
curl -X GET $BASE_URL | jq '.'

echo ""
echo ""

echo "4. Getting student by ID (ID: 1)..."
echo "GET $BASE_URL/1"
curl -X GET $BASE_URL/1 | jq '.'

echo ""
echo ""

echo "5. Getting student by roll number (Roll No: 1001)..."
echo "GET $BASE_URL/rollno/1001"
curl -X GET $BASE_URL/rollno/1001 | jq '.'

echo ""
echo ""

echo "6. Updating student (ID: 1)..."
echo "PUT $BASE_URL/1"
curl -X PUT $BASE_URL/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Johnson Updated",
    "rollNo": 1001,
    "branch": "Computer Science Engineering",
    "course": "B.Tech"
  }' | jq '.'

echo ""
echo ""

echo "7. Getting students by branch..."
echo "GET $BASE_URL/branch/Computer%20Science%20Engineering"
curl -X GET "$BASE_URL/branch/Computer%20Science%20Engineering" | jq '.'

echo ""
echo ""

echo "8. Searching students by name..."
echo "GET $BASE_URL/search?name=Alice"
curl -X GET "$BASE_URL/search?name=Alice" | jq '.'

echo ""
echo ""

echo "9. Getting all students after updates..."
echo "GET $BASE_URL"
curl -X GET $BASE_URL | jq '.'

echo ""
echo ""

echo "10. Deleting a student (ID: 2)..."
echo "DELETE $BASE_URL/2"
curl -X DELETE $BASE_URL/2 | jq '.'

echo ""
echo ""

echo "11. Final check - Getting all students..."
echo "GET $BASE_URL"
curl -X GET $BASE_URL | jq '.'

echo ""
echo ""
echo "=== API Test Complete ==="
echo ""
echo "Note: If you see 'jq: command not found', install jq or remove '| jq .' from the commands"
echo "You can also test these endpoints using Postman or any other API testing tool"