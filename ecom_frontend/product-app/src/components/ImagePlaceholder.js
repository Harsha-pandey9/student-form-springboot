import React from 'react';
import './ImagePlaceholder.css';

const ImagePlaceholder = ({ className = '', alt = 'No image available' }) => {
  return (
    <div className={`image-placeholder ${className}`}>
      <div className="placeholder-content">
        <svg 
          width="48" 
          height="48" 
          viewBox="0 0 24 24" 
          fill="none" 
          stroke="currentColor" 
          strokeWidth="2" 
          strokeLinecap="round" 
          strokeLinejoin="round"
        >
          <rect x="3" y="3" width="18" height="18" rx="2" ry="2"/>
          <circle cx="8.5" cy="8.5" r="1.5"/>
          <polyline points="21,15 16,10 5,21"/>
        </svg>
        <span className="placeholder-text">{alt}</span>
      </div>
    </div>
  );
};

export default ImagePlaceholder;