import React from "react";
import { createRoot } from "react-dom/client";
import { Provider } from "react-redux";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { HelmetProvider } from "react-helmet-async";
import { store } from "./app/store";
import Home from "./features/Home";
import Card from "./features/Card";
import "./index.css";

const container = document.getElementById("root")!;
const root = createRoot(container);

const router = createBrowserRouter([
  { path: "/", element: <Home /> },
  { path: "/:userId/:sendCardId", element: <Card /> },
]);

root.render(
  <HelmetProvider>
    <Provider store={store}>
      <RouterProvider router={router} />
    </Provider>
  </HelmetProvider>
);
