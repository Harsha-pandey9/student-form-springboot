import React, { useState } from 'react';
import productService from '../services/productService';
import ImagePlaceholder from './ImagePlaceholder';

import './ProductCard.css';

const ProductCard = ({ product, onEdit, onDelete, onView }) => {
  const [imageError, setImageError] = useState(false);
  const [imageLoaded, setImageLoaded] = useState(false);

  const formatPrice = (price) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(price);
  };

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString();
  };

  // Build image URL from backend
  const imageUrl = productService.getImageUrl(product.id);

  const handleImageError = () => {
    console.error('Image failed to load for product:', product.name, 'URL:', imageUrl);
    setImageError(true);
  };

  const handleImageLoad = () => {
    console.log('Image loaded successfully for product:', product.name);
    setImageLoaded(true);
    setImageError(false);
  };

  return (
    <div className="product-card">
      <div className="product-image-container">
        {product.id && !imageError ? (
          <img
            src={imageUrl}
            alt={product.name}
            className="product-image"
            style={{ display: imageLoaded ? 'block' : 'none' }}
            onError={handleImageError}
            onLoad={handleImageLoad}
          />
        ) : null}
        <ImagePlaceholder
  className={`card-placeholder ${imageLoaded && !imageError ? 'hidden' : ''}`}
  alt="No product image"
/>

      </div>

      <div className="product-header">
        <h3 className="product-name">{product.name}</h3>
        <span className={`availability-badge ${product.available ? 'available' : 'unavailable'}`}>
          {product.available ? 'Available' : 'Out of Stock'}
        </span>
      </div>

      <div className="product-details">
        <p className="product-description">{product.description}</p>
        <div className="product-info">
          <span className="product-brand"><strong>Brand:</strong> {product.brand}</span>
          <span className="product-category"><strong>Category:</strong> {product.category}</span>
          <span className="product-price"><strong>Price:</strong> {formatPrice(product.price)}</span>
          <span className="product-quantity"><strong>Quantity:</strong> {product.quantity}</span>
          <span className="product-release-date">
            <strong>Release Date:</strong> {formatDate(product.releaseDate || product.relesedate)}
          </span>
        </div>
      </div>

      <div className="product-actions">
        <button className="btn btn-primary" onClick={() => onView(product)}>
          View Details
        </button>
        <button className="btn btn-secondary" onClick={() => onEdit(product)}>
          Edit
        </button>
        <button className="btn btn-danger" onClick={() => onDelete(product.id)}>
          Delete
        </button>
      </div>
    
    </div>
  );
};

export default ProductCard;
