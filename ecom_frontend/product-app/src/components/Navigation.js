import React from 'react';
import './Navigation.css';

const Navigation = ({ currentView, onViewChange }) => {
  const navItems = [
    { id: 'list', label: 'Product Catalog', icon: '📦' },
    { id: 'add', label: 'Add Product', icon: '➕' },
    { id: 'update', label: 'Update Product', icon: '✏️' }
  ];

  return (
    <nav className="navigation">
      <div className="nav-brand">
        <h1>🛒 E-Commerce Admin</h1>
      </div>
      
      <div className="nav-links">
        {navItems.map(item => (
          <button
            key={item.id}
            className={`nav-link ${currentView === item.id ? 'active' : ''}`}
            onClick={() => onViewChange(item.id)}
          >
            <span className="nav-icon">{item.icon}</span>
            <span className="nav-label">{item.label}</span>
          </button>
        ))}
      </div>
    </nav>
  );
};

export default Navigation;