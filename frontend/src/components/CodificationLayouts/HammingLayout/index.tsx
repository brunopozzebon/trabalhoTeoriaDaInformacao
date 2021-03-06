import React from "react";
import { UnaryCodewordRow } from "./styles";
import { useCodewords, useIndex } from "../../../context";
import { Codeword } from "../../../models/codeword";

export const HammingLayout = () => {
  const [index, ] = useIndex();
  const [codewords,] = useCodewords();

  function renderCodeword(codeword: Codeword, index:number) {
  
      return (
        <UnaryCodewordRow key={index}>
          
        </UnaryCodewordRow>
      );
   
  };

  function renderCodewords() {
    const layoutArray = [];
    for (let i = 0; i < index; i++) {
      let codeword = codewords[i];
      if (codeword) {
        layoutArray.push(renderCodeword(codeword, i));
      }
    }
    return layoutArray;
  };

  return <>
  <h1>Hammiing</h1>
    {
      renderCodewords()
    }

  </>;
};
