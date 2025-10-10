import React, { useState, useEffect } from 'react';
import ProductCard from './ProductCard';
import productService from '../services/productService';
import './ProductList.css';

const ProductList = ({ onEditProduct, onViewProduct }) => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      const data = await productService.getAllProducts();
      setProducts(data);
      
      // Extract unique categories
      const uniqueCategories = [...new Set(data.map(product => product.category))];
      setCategories(uniqueCategories);
      
      setError(null);
    } catch (err) {
      setError('Failed to fetch products. Please check if your backend is running.');
      console.error('Error fetching products:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (productId) => {
    if (window.confirm('Are you sure you want to delete this product?')) {
      try {
        await productService.deleteProduct(productId);
        setProducts(products.filter(product => product.id !== productId));
        alert('Product deleted successfully!');
      } catch (err) {
        alert('Error deleting product');
        console.error('Error deleting product:', err);
      }
    }
  };

  const handleSearch = async () => {
    if (searchTerm.trim()) {
      try {
        setLoading(true);
        const data = await productService.searchProducts(searchTerm);
        setProducts(data);
      } catch (err) {
        setError('Error searching products');
      } finally {
        setLoading(false);
      }
    } else {
      fetchProducts();
    }
  };

  const handleCategoryFilter = async (category) => {
    setSelectedCategory(category);
    if (category) {
      try {
        setLoading(true);
        const data = await productService.getProductsByCategory(category);
        setProducts(data);
      } catch (err) {
        setError('Error filtering products by category');
      } finally {
        setLoading(false);
      }
    } else {
      fetchProducts();
    }
  };

  const filteredProducts = products.filter(product =>
    product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
    product.description.toLowerCase().includes(searchTerm.toLowerCase()) ||
    product.brand.toLowerCase().includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner"></div>
        <p>Loading products...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="error-container">
        <p className="error-message">{error}</p>
        <button className="btn btn-primary" onClick={fetchProducts}>
          Retry
        </button>
      </div>
    );
  }

  return (
    <div className="product-list-container">
      <div className="product-list-header">
        <h2>Product Catalog</h2>
        <button className="btn btn-primary" onClick={fetchProducts}>
          Refresh
        </button>
      </div>

      <div className="filters-container">
        <div className="search-container">
          <input
            type="text"
            placeholder="Search products..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
          <button className="btn btn-primary" onClick={handleSearch}>
            Search
          </button>
        </div>

        <div className="category-filter">
          <select
            value={selectedCategory}
            onChange={(e) => handleCategoryFilter(e.target.value)}
            className="category-select"
          >
            <option value="">All Categories</option>
            {categories.map(category => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div className="products-count">
        <p>Showing {filteredProducts.length} product(s)</p>
      </div>

      {filteredProducts.length === 0 ? (
        <div className="no-products">
          <p>No products found.</p>
        </div>
      ) : (
        <div className="products-grid">
          {filteredProducts.map(product => (
            <ProductCard
              key={product.id}
              product={product}
              onEdit={onEditProduct}
              onDelete={handleDelete}
              onView={onViewProduct}
            />
          ))}
        </div>
      )}
    </div>
  );
};

export default ProductList;