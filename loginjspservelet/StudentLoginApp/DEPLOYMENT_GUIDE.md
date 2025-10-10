# StudentLoginApp - Deployment Guide

## 🚨 Common Issues and Solutions

### Issue 1: "Project not recognized by IDE"
**Solution**: Import as existing project in Eclipse or create new Dynamic Web Project

### Issue 2: "Servlet API not found"
**Solution**: Add Tomcat runtime to project build path

### Issue 3: "404 Error when accessing application"
**Solution**: Check context path and servlet mapping

---

## 🔧 Setup Methods

### Method 1: Eclipse IDE (Recommended)

1. **Import Project**:
   - File → Import → Existing Projects into Workspace
   - Select the `StudentLoginApp` folder
   - Check "Copy projects into workspace" if needed

2. **Configure Server**:
   - Right-click project → Properties → Project Facets
   - Ensure "Dynamic Web Module" is checked (version 4.0)
   - Ensure "Java" is checked (version 1.8 or higher)

3. **Add Tomcat Runtime**:
   - Right-click project → Properties → Java Build Path
   - Libraries tab → Add Library → Server Runtime
   - Select Apache Tomcat v9.0 (or your version)

4. **Deploy and Run**:
   - Right-click project → Run As → Run on Server
   - Select Tomcat server
   - Access: `http://localhost:8080/StudentLoginApp/`

### Method 2: Maven Build

1. **Prerequisites**:
   - Install Maven 3.6+
   - Install Java 8+

2. **Build Project**:
   ```bash
   cd StudentLoginApp
   mvn clean compile
   mvn package
   ```

3. **Deploy WAR**:
   - Copy `target/StudentLoginApp.war` to Tomcat `webapps` folder
   - Start Tomcat
   - Access: `http://localhost:8080/StudentLoginApp/`

4. **Run with Maven Tomcat Plugin**:
   ```bash
   mvn tomcat7:run
   ```
   - Access: `http://localhost:8080/StudentLoginApp/`

### Method 3: IntelliJ IDEA

1. **Import Project**:
   - File → Open → Select `StudentLoginApp` folder
   - Choose "Import project from external model" → Maven

2. **Configure Tomcat**:
   - Run → Edit Configurations → Add → Tomcat Server → Local
   - Configure Tomcat installation path
   - In Deployment tab, add artifact: `StudentLoginApp:war exploded`

3. **Run Application**:
   - Click Run button
   - Access: `http://localhost:8080/StudentLoginApp/`

### Method 4: Manual Deployment

1. **Compile Java Files**:
   ```bash
   javac -cp "path/to/servlet-api.jar" -d build/classes src/com/example/*.java
   ```

2. **Create WAR Structure**:
   ```
   StudentLoginApp.war
   ├── WEB-INF/
   │   ├── classes/
   │   │   └── com/example/LoginServlet.class
   │   └── web.xml
   ├── index.jsp
   ├── welcome.jsp
   ├── logout.jsp
   └── error.jsp
   ```

3. **Deploy to Tomcat**:
   - Copy WAR to `webapps` folder
   - Start Tomcat

---

## 🐛 Troubleshooting

### Problem: 404 Error
**Causes & Solutions**:
- ❌ Wrong URL → ✅ Use `http://localhost:8080/StudentLoginApp/`
- ❌ Server not started → ✅ Start Tomcat server
- ❌ Project not deployed → ✅ Check server deployment

### Problem: 500 Internal Server Error
**Causes & Solutions**:
- ❌ Missing servlet-api.jar → ✅ Add Tomcat runtime to build path
- ❌ Compilation errors → ✅ Check Problems view in Eclipse
- ❌ Missing web.xml → ✅ Ensure web.xml exists in WEB-INF

### Problem: Login not working
**Causes & Solutions**:
- ❌ Wrong credentials → ✅ Use: username=`student`, password=`password123`
- ❌ Form action incorrect → ✅ Check form action="LoginServlet"
- ❌ Servlet mapping issue → ✅ Verify @WebServlet("/LoginServlet") annotation

### Problem: Session issues
**Causes & Solutions**:
- ❌ Cookies disabled → ✅ Enable cookies in browser
- ❌ Session timeout → ✅ Check web.xml session-config
- ❌ Multiple tabs → ✅ Use single browser tab for testing

---

## 📋 Verification Checklist

- [ ] Project imports without errors
- [ ] No compilation errors in Problems view
- [ ] Tomcat server configured and running
- [ ] Can access `http://localhost:8080/StudentLoginApp/`
- [ ] Login page displays correctly
- [ ] Can login with demo credentials
- [ ] Welcome page shows after successful login
- [ ] Logout functionality works
- [ ] Session protection works (try accessing welcome.jsp directly)

---

## 🔐 Demo Credentials

- **Username**: `student`
- **Password**: `password123`

---

## 📞 Support

If you're still experiencing issues:

1. Check Tomcat logs in `logs/catalina.out`
2. Verify Java version compatibility
3. Ensure all required dependencies are in classpath
4. Try creating a new Dynamic Web Project and copying files manually
5. Check firewall/antivirus blocking port 8080