import React from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { store } from "./app/store";
import "./index.css";
import Home from "./features/Home";
import Card from "./features/Card";

const container = document.getElementById("root")!;
const root = createRoot(container);

const router = createBrowserRouter([
  { path: "/", element: <Home /> },
  { path: "/:userId/:sendCardId", element: <Card /> },
]);

root.render(
  <Provider store={store}>
    <RouterProvider router={router} />
  </Provider>
);
