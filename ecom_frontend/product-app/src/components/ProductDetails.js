import React, { useState } from 'react';
import productService from '../services/productService';
import ImagePlaceholder from './ImagePlaceholder';
import './ProductDetails.css';

const ProductDetails = ({ product, onEdit, onClose }) => {
  const [imageError, setImageError] = useState(false);
  const [imageLoaded, setImageLoaded] = useState(false);

  if (!product) return null;

  const formatPrice = (price) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: 'USD'
    }).format(price);
  };

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  return (
    <div className="product-details-overlay">
      <div className="product-details-modal">
        <div className="product-details-header">
          <h2>Product Details</h2>
          <button className="close-btn" onClick={onClose}>
            ×
          </button>
        </div>

        <div className="product-details-content">
          <div className="product-layout">
            <div className="product-image-section">
              {product.id && !imageError ? (
                <img 
                  src={productService.getImageUrl(product.id)} 
                  alt={product.name}
                  className="product-detail-image"
                  style={{ display: imageLoaded ? 'block' : 'none' }}
                  onError={() => {
                    console.error('Image failed to load for product:', product.name);
                    setImageError(true);
                  }}
                  onLoad={() => {
                    console.log('Image loaded successfully for product:', product.name);
                    setImageLoaded(true);
                    setImageError(false);
                  }}
                />
              ) : null}
              <ImagePlaceholder 
                className={`detail-placeholder ${imageLoaded && !imageError ? 'hidden' : ''}`}
                alt="No product image available"
              />
            </div>
            
            <div className="product-info-section">
              <div className="product-main-info">
                <div className="product-title-section">
                  <h3 className="product-title">{product.name}</h3>
                  <span className={`status-badge ${product.available ? 'available' : 'unavailable'}`}>
                    {product.available ? 'Available' : 'Out of Stock'}
                  </span>
                </div>
            
                <div className="product-price-section">
                  <span className="price-label">Price:</span>
                  <span className="price-value">{formatPrice(product.price)}</span>
                </div>
              </div>

              <div className="product-description-section">
                <h4>Description</h4>
                <p className="product-description">{product.description}</p>
              </div>

              <div className="product-info-grid">
                <div className="info-item">
                  <span className="info-label">Brand:</span>
                  <span className="info-value">{product.brand}</span>
                </div>
                
                <div className="info-item">
                  <span className="info-label">Category:</span>
                  <span className="info-value">{product.category}</span>
                </div>
                
                <div className="info-item">
                  <span className="info-label">Quantity in Stock:</span>
                  <span className="info-value">{product.quantity}</span>
                </div>
                
                <div className="info-item">
                  <span className="info-label">Release Date:</span>
                  <span className="info-value">{formatDate(product.releaseDate || product.relesedate)}</span>
                </div>
                
                <div className="info-item">
                  <span className="info-label">Product ID:</span>
                  <span className="info-value">#{product.id}</span>
                </div>
                
                <div className="info-item">
                  <span className="info-label">Availability:</span>
                  <span className={`info-value ${product.available ? 'available-text' : 'unavailable-text'}`}>
                    {product.available ? 'In Stock' : 'Out of Stock'}
                  </span>
                </div>
              </div>
            </div>
          </div>

          <div className="product-actions">
            <button className="btn btn-primary" onClick={() => onEdit(product)}>
              Edit Product
            </button>
            <button className="btn btn-secondary" onClick={onClose}>
              Close
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;