import React, { useState } from "react";
import axios from "axios";

const UpdateProduct = () => {
  const [id, setId] = useState("");
  const [product, setProduct] = useState({
    name: "",
    description: "",
    brand: "",
    price: "",
    category: "",
    relesedate: "",
    avilable: true,
    quantity: ""
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setProduct({
      ...product,
      [name]: type === "checkbox" ? checked : value
    });
  };

  const handleUpdate = async () => {
    try {
      const res = await axios.put(`http://localhost:8081/api/product/${id}`, product);
      alert("Product updated successfully!");
      console.log(res.data);
    } catch (err) {
      console.error(err);
      alert("Error updating product");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>Update Product</h2>
      <input
        type="number"
        placeholder="Product ID"
        value={id}
        onChange={(e) => setId(e.target.value)}
        style={{ marginBottom: "10px" }}
      />
      <br />
      <input name="name" placeholder="Name" onChange={handleChange} />
      <br />
      <input name="description" placeholder="Description" onChange={handleChange} />
      <br />
      <input name="brand" placeholder="Brand" onChange={handleChange} />
      <br />
      <input name="price" placeholder="Price" type="number" onChange={handleChange} />
      <br />
      <input name="category" placeholder="Category" onChange={handleChange} />
      <br />
      <input name="relesedate" placeholder="Release Date" type="date" onChange={handleChange} />
      <br />
      <label>
        Available:
        <input name="avilable" type="checkbox" checked={product.avilable} onChange={handleChange} />
      </label>
      <br />
      <input name="quantity" placeholder="Quantity" type="number" onChange={handleChange} />
      <br />
      <button onClick={handleUpdate}>Update Product</button>
    </div>
  );
};

export default UpdateProduct;
