import React from "react";
import { Routes, Route } from "react-router-dom";

import Home from "./features/Home";
import Card from "./features/Card";

import "./App.css";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/:id/:cardId" element={<Card />} />
      </Routes>
    </div>
  );
}
export default App;
