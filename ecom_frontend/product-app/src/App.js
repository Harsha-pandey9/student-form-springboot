import React, { useState } from 'react';
import './App.css';
import Navigation from './components/Navigation';
import ProductList from './components/ProductList';
import ProductForm from './components/ProductForm';
import ProductDetails from './components/ProductDetails';
import UpdateProduct from './UpdateProduct';

function App() {
  const [currentView, setCurrentView] = useState('list');
  const [selectedProduct, setSelectedProduct] = useState(null);
  const [showProductDetails, setShowProductDetails] = useState(false);
  const [refreshList, setRefreshList] = useState(0);

  const handleViewChange = (view) => {
    setCurrentView(view);
    setSelectedProduct(null);
    setShowProductDetails(false);
  };

  const handleEditProduct = (product) => {
    setSelectedProduct(product);
    setCurrentView('edit');
    setShowProductDetails(false);
  };

  const handleViewProduct = (product) => {
    setSelectedProduct(product);
    setShowProductDetails(true);
  };

  const handleProductSaved = () => {
    setCurrentView('list');
    setSelectedProduct(null);
    setRefreshList(prev => prev + 1); // Trigger refresh
  };

  const handleCloseDetails = () => {
    setShowProductDetails(false);
    setSelectedProduct(null);
  };

  const renderCurrentView = () => {
    switch (currentView) {
      case 'list':
        return (
          <ProductList
            key={refreshList} // Force re-render when refreshList changes
            onEditProduct={handleEditProduct}
            onViewProduct={handleViewProduct}
          />
        );
      case 'add':
        return (
          <ProductForm
            onSave={handleProductSaved}
            onCancel={() => setCurrentView('list')}
            isEdit={false}
          />
        );
      case 'edit':
        return (
          <ProductForm
            product={selectedProduct}
            onSave={handleProductSaved}
            onCancel={() => setCurrentView('list')}
            isEdit={true}
          />
        );
      case 'update':
        return <UpdateProduct />;
      default:
        return (
          <ProductList
            onEditProduct={handleEditProduct}
            onViewProduct={handleViewProduct}
          />
        );
    }
  };

  return (
    <div className="app">
      <Navigation currentView={currentView} onViewChange={handleViewChange} />
      
      <main className="main-content">
        {renderCurrentView()}
      </main>

      {showProductDetails && (
        <ProductDetails
          product={selectedProduct}
          onEdit={handleEditProduct}
          onClose={handleCloseDetails}
        />
      )}
    </div>
  );
}

export default App;

