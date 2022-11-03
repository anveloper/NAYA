import React from "react";
import { useParams } from "react-router-dom";
const Card = () => {
  const { id, cardId } = useParams();
  return (
    <div>
      Card
      <div>{id}</div>
      <div>{cardId}</div>
    </div>
  );
};

export default Card;
