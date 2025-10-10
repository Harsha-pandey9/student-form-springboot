# How to Start Your React Development Server

## The Issue
You're getting "ERR_CONNECTION_REFUSED" because the React development server isn't running on localhost:3000.

## Solution - Start the Development Server

### Option 1: Using Command Prompt/Terminal
1. Open Command Prompt or Terminal
2. Navigate to your project directory:
   ```
   cd "C:\Users\harsh\OneDrive\Desktop\SpringBoot\ecom_frontend\product-app"
   ```
3. Start the development server:
   ```
   npm start
   ```

### Option 2: Using VS Code Terminal
1. Open VS Code
2. Open the integrated terminal (Ctrl + `)
3. Make sure you're in the project directory
4. Run:
   ```
   npm start
   ```

## What Should Happen
- The command will start the React development server
- It should automatically open your browser to http://localhost:3000
- You should see your React app running
- The terminal will show something like:
  ```
  Compiled successfully!

  You can now view product-app in the browser.

    Local:            http://localhost:3000
    On Your Network:  http://192.168.x.x:3000

  Note that the development build is not optimized.
  To create a production build, use npm run build.
  ```

## Troubleshooting

### If you get "npm not found" error:
- Make sure Node.js is installed on your system
- Download from: https://nodejs.org/

### If you get port 3000 already in use:
- Kill any existing processes on port 3000
- Or the server will automatically use a different port (like 3001)

### If you get dependency errors:
- Run: `npm install` first, then `npm start`

### If you get permission errors:
- Try running as administrator (Windows)
- Or use: `sudo npm start` (Mac/Linux)

## After Starting Successfully
1. Your React app should load at http://localhost:3000
2. Make sure your Spring Boot backend is also running on http://localhost:8081
3. Test the image loading functionality we fixed earlier

## Quick Check Commands
Before starting, you can verify:
```bash
# Check if Node.js is installed
node --version

# Check if npm is installed  
npm --version

# Check if dependencies are installed
npm list --depth=0
```