import axios from 'axios';

const BASE_URL = 'http://localhost:8081/api';

const productService = {
  // Get all products
  getAllProducts: async () => {
    try {
      const response = await axios.get(`${BASE_URL}/product`);
      return response.data;
    } catch (error) {
      console.error('Error fetching products:', error);
      throw error;
    }
  },

  // Get product by ID
  getProductById: async (id) => {
    try {
      const response = await axios.get(`${BASE_URL}/product/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching product:', error);
      throw error;
    }
  },
  // Create new product with image
  createProduct: async (productData) => {
    try {
      const formData = new FormData();
      
      // Create product object without image
      const productObj = {
        name: productData.name,
        description: productData.description,
        brand: productData.brand,
        price: productData.price,
        category: productData.category,
        releaseDate: productData.releaseDate,
        available: productData.available,
        quantity: productData.quantity
      };
      
      // Add product data as JSON blob for @RequestPart Product
      formData.append('prod', new Blob([JSON.stringify(productObj)], {
        type: 'application/json'
      }));
      
      // Add image file for @RequestPart MultipartFile
      if (productData.image) {
        formData.append('imgfile', productData.image);
      }
      
      const response = await axios.post(`${BASE_URL}/product`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return response.data;
    } catch (error) {
      console.error('Error creating product:', error);
      throw error;
    }
  },



  // Update product with image
  updateProduct: async (id, productData) => {
    try {
      const formData = new FormData();
      
      // Create product object without image
      const productObj = {
        id: id,
        name: productData.name,
        description: productData.description,
        brand: productData.brand,
        price: productData.price,
        category: productData.category,
        releaseDate: productData.releaseDate,
        available: productData.available,
        quantity: productData.quantity
      };
      
      // Add product data as JSON blob for @RequestPart Product
      formData.append('prod', new Blob([JSON.stringify(productObj)], {
        type: 'application/json'
      }));
      
      // Add image file for @RequestPart MultipartFile (if provided)
      if (productData.image) {
        formData.append('imgfile', productData.image);
      }
      
      const response = await axios.put(`${BASE_URL}/product/${id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return response.data;
    } catch (error) {
      console.error('Error updating product:', error);
      throw error;
    }
  },

  // Get product image URL from backend endpoint
  getImageUrl: (id) => {
    if (!id) {
      console.warn('Product ID is missing for image URL generation');
      return null;
    }
    const imageUrl = `${BASE_URL}/product/${id}/image`;
    console.log('Generated image URL:', imageUrl);
    return imageUrl;
  },

  // Test image endpoint connectivity
  testImageEndpoint: async (id) => {
    try {
      const response = await axios.head(`${BASE_URL}/product/${id}/image`);
      return response.status === 200;
    } catch (error) {
      console.error('Image endpoint test failed:', error);
      return false;
    }
  },

  // Delete product
  deleteProduct: async (id) => {
    try {
      const response = await axios.delete(`${BASE_URL}/product/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error deleting product:', error);
      throw error;
    }
  },

  // Search products by name
  searchProducts: async (keyword) => {
    try {
      const response = await axios.get(`${BASE_URL}/product/search?keyword=${keyword}`);
      return response.data;
    } catch (error) {
      console.error('Error searching products:', error);
      throw error;
    }
  },

  // Get products by category
  getProductsByCategory: async (category) => {
    try {
      const response = await axios.get(`${BASE_URL}/products/category/${category}`);
      return response.data;
    } catch (error) {
      console.error('Error fetching products by category:', error);
      throw error;
    }
  }
};

export default productService;