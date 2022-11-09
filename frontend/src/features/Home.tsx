import React from "react";
import { Helmet } from "react-helmet-async";

const Home = () => {
  return (
    <div>
      <Helmet>
        <title>{"나야(Naya) - 나를 소개하는 가장 쉬운 방법"}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      </Helmet>
      Home
    </div>
  );
};

export default Home;
