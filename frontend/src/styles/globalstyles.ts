import { createGlobalStyle } from "styled-components";

import pixelToRem from "../utils/pxToRem";
import { device } from "./responsive";


export const GlobalStyles = createGlobalStyle`
@font-face {
  font-family: 'Pretendard-Black';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Black.woff') format('woff');
  font-weight: 900;
  font-style: Black;
}
@font-face {
  font-family: 'Pretendard-ExtraBold';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-ExtraBold.woff') format('woff');
  font-weight: 800;
  font-style: ExtraBold;
}
@font-face {
  font-family: 'Pretendard-Bold';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Bold.woff') format('woff');
  font-weight: 700;
  font-style: Bold;
}
@font-face {
  font-family: 'Pretendard-SemiBold';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-SemiBold.woff') format('woff');
  font-weight: 600;
  font-style: SemiBold;
}
@font-face {
  font-family: 'Pretendard-Medium';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Medium.woff') format('woff');
  font-weight: 500;
  font-style: Medium;
}
@font-face {
  font-family: 'Pretendard-Regular';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
  font-weight: 400;
  font-style: Normal;
}
@font-face {
  font-family: 'Pretendard-Light';
  src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Light.woff') format('woff');
  font-weight: 300;
  font-style: Light;
}

*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
 }
body{
  font-family: "Pretendard-Medium", sans-serif;
  -webkit-font-smoothing: antialiased;
  background-color: var(--neutral-white);
 }
:root {
  /* Colors */
  --primary-blue: #2C67FF;
  --primary-dark: #122045;
  --primary-light: # F4F7F9;
  --secondary-lightBlue: #DFE6FB;
  --secondary-mediumBlue: #5C8AFF;
  --secondary-systemBlue: #0891F2;
  --secondary-basicBlue: #055EEA;
  --secondary-darkBlue: #044BBB;
  --neutral-white: #FEFEFE;
  --neutral-lightness: #F2F5F9;
  --neutral-light: #CED3D6;
  --neutral-medium: #BDC5CA;
  --neutral-gray: #A1ACB3;
  --background: #04032C;
  --background-section: linear-gradient(180deg, #0891F2 0%, #055EEA 24.49%);
  
  /* Fonts */
    --font-display: 800 ${pixelToRem(62)}/${pixelToRem(96)} "Pretendard-ExtraBold", sans-serif;
    --font-heading-1: 700 ${pixelToRem(32)}/${pixelToRem(52)} "Pretendard-Bold", sans-serif;
    --font-heading-2: 600 ${pixelToRem(32)}/${pixelToRem(
  52
)} "Pretendard-Medium", sans-serif;
    --font-heading-3: 500 ${pixelToRem(28)}/${pixelToRem(
  24
)} "Pretendard-SemiBold", sans-serif;
--font-mobile-heading-1: 800 ${pixelToRem(32)}/${pixelToRem(
  50
)} "Pretendard-ExtraBold", sans-serif;
--font-mobile-text-1: 500 ${pixelToRem(14)}/${pixelToRem(
  24
)} "Pretendard-Medium", sans-serif;
--text-4: 500 ${pixelToRem(20)}/${pixelToRem(28)} "Pretendard-Medium", sans-serif;
--text-3: 500 ${pixelToRem(18)}/${pixelToRem(24)} "Pretendard-Medium", sans-serif;
--text-2: 500 ${pixelToRem(14)}/${pixelToRem(12)} "Pretendard-Medium", sans-serif;
--text-1: 400 ${pixelToRem(16)}/${pixelToRem(24)} "Pretendard-Regular", sans-serif;
--text-0: 400 ${pixelToRem(14)}/${pixelToRem(24)} "Pretendard-Regular", sans-serif;
}`;