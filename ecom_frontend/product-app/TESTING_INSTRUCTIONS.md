# Testing Instructions

## Quick Fix Summary

### 1. Fix Your Spring Boot Backend Controller

In your Spring Boot controller, change this:
```java
@GetMapping("/product/{id}/image")
public ResponseEntity<byte[]>getprodimgbyid(@PathVariable int prodid){
```

To this:
```java
@GetMapping("/product/{id}/image")
public ResponseEntity<byte[]> getprodimgbyid(@PathVariable int id){
    try {
        Product product = service.getproductbyid(id);
        
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        
        byte[] imageData = product.getImgdata();
        
        if (imageData == null || imageData.length == 0) {
            return ResponseEntity.notFound().build();
        }
        
        String contentType = product.getImgtype();
        if (contentType == null || contentType.isEmpty()) {
            contentType = "image/jpeg";
        }
        
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(contentType))
                .body(imageData);
                
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
```

### 2. Test Backend Endpoint

**Manual testing**:
1. Open browser and go to: `http://localhost:8081/api/product`
2. Note a product ID from the response
3. Try: `http://localhost:8081/api/product/{ID}/image` (replace {ID} with actual ID)

### 3. Start Frontend
```bash
npm start
```

### 4. Check Browser Console
Open browser developer tools and look for:
- Image URL generation logs
- Image loading success/failure messages
- Debug information from ImageDebugger component

## Common Issues and Solutions

### Issue: "ECONNREFUSED" error
**Solution**: Make sure your Spring Boot application is running on port 8081

### Issue: CORS errors
**Solution**: Add CORS configuration to your Spring Boot application:
```java
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ProductController {
    // your controller methods
}
```

### Issue: 404 errors for images
**Possible causes**:
1. Product doesn't exist
2. Product has no image data
3. Backend method parameter mismatch (fixed above)

### Issue: Images not displaying
**Check**:
1. Browser Network tab for failed requests
2. Console for error messages
3. ImageDebugger component output

## Debugging Steps

1. **Verify backend is running**: Open `http://localhost:8081/api/product` in browser
2. **Check specific product**: Open `http://localhost:8081/api/product/1` in browser
3. **Test image endpoint**: Open `http://localhost:8081/api/product/1/image` in browser
4. **Check frontend logs**: Open browser console
5. **Use debug component**: Look for ImageDebugger output in development

## Production Cleanup

Once everything is working, remove the debug components:

1. Remove ImageDebugger import and usage from ProductCard.js
2. Remove the debug files:
   - `src/components/ImageDebugger.js`
   - `IMAGE_LOADING_FIXES.md`
   - `TESTING_INSTRUCTIONS.md`

## Expected Results

After applying the fixes:
- Products with images should display correctly
- Products without images should show placeholder
- Console should show clear logging of image loading status
- No 404 or parameter mismatch errors