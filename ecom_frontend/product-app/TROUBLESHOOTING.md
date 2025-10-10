# React Server Startup Troubleshooting

## Current Issue: ERR_CONNECTION_REFUSED on localhost:3000

This error means your React development server is not running. Here's how to fix it:

## Step 1: Start the Development Server

### Open Terminal/Command Prompt:
1. **Windows**: Press `Win + R`, type `cmd`, press Enter
2. **Or use VS Code**: Press `Ctrl + `` (backtick) to open integrated terminal

### Navigate to your project:
```bash
cd "C:\Users\harsh\OneDrive\Desktop\SpringBoot\ecom_frontend\product-app"
```

### Start the server:
```bash
npm start
```

## Step 2: What Should Happen

You should see output like this:
```
Compiled successfully!

You can now view product-app in the browser.

  Local:            http://localhost:3000
  On Your Network:  http://192.168.x.x:3000

Note that the development build is not optimized.
To create a production build, use npm run build.
```

## Step 3: Common Issues and Solutions

### Issue 1: "npm is not recognized"
**Solution**: Install Node.js
- Download from: https://nodejs.org/
- Install the LTS version
- Restart your terminal after installation

### Issue 2: "Port 3000 is already in use"
**Solution**: 
- The terminal will ask if you want to use a different port (like 3001)
- Type `y` and press Enter
- Or kill the existing process:
  ```bash
  # Windows
  netstat -ano | findstr :3000
  taskkill /PID <PID_NUMBER> /F
  
  # Mac/Linux
  lsof -ti:3000 | xargs kill -9
  ```

### Issue 3: Dependency errors
**Solution**: Reinstall dependencies
```bash
# Delete node_modules and package-lock.json
rm -rf node_modules package-lock.json

# Reinstall
npm install

# Start server
npm start
```

### Issue 4: Permission errors
**Solution**: 
- **Windows**: Run Command Prompt as Administrator
- **Mac/Linux**: Use `sudo npm start`

### Issue 5: Compilation errors
**Solution**: Check the terminal output for specific errors
- Look for syntax errors in your React components
- Fix any import/export issues
- Make sure all required files exist

## Step 4: Verify Everything is Working

Once the server starts successfully:

1. **React App**: http://localhost:3000 should show your product app
2. **Spring Boot Backend**: Make sure it's running on http://localhost:8081
3. **Test API**: Open http://localhost:8081/api/product in browser

## Step 5: If Still Having Issues

### Check Node.js Installation:
```bash
node --version
npm --version
```

### Check Project Structure:
Make sure these files exist:
- `package.json`
- `src/index.js`
- `src/App.js`
- `public/index.html`

### Clear Cache:
```bash
npm start -- --reset-cache
```

### Alternative Start Methods:
```bash
# Try with different options
npx react-scripts start

# Or with specific port
PORT=3001 npm start
```

## Step 6: Success Indicators

When everything is working:
- ✅ Terminal shows "Compiled successfully!"
- ✅ Browser opens automatically to localhost:3000
- ✅ You see your React app interface
- ✅ No error messages in browser console
- ✅ Network tab shows successful API calls

## Need More Help?

If you're still having issues:
1. Share the exact error message from the terminal
2. Check if Node.js is properly installed
3. Make sure you're in the correct directory
4. Try restarting your computer and trying again

## Quick Commands Summary:
```bash
# Navigate to project
cd "C:\Users\harsh\OneDrive\Desktop\SpringBoot\ecom_frontend\product-app"

# Install dependencies (if needed)
npm install

# Start development server
npm start

# If port 3000 is busy, use different port
PORT=3001 npm start
```