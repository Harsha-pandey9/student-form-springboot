@echo off
echo ========================================
echo Testing API Authentication Endpoints
echo ========================================
echo.

echo Testing Health Check...
curl -X GET http://localhost:8080/api/auth/health
echo.
echo.

echo Testing Login API...
echo Username: admin, Password: admin123
curl -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"username\":\"admin\",\"password\":\"admin123\"}"
echo.
echo.

echo Testing Student API (requires token from login)...
echo Use the token from login response in Authorization header:
echo curl -X GET http://localhost:8080/api/students -H "Authorization: Bearer YOUR_TOKEN_HERE"
echo.

pause