import styled from "styled-components";
import pixelToRem from "../utils/pxToRem";
import { device } from "./responsive";
import { motion } from "framer-motion";
import { ButtonProps } from "../components/Button";

interface GalleryFlexContainerProps {
  flex?: "row" | "column";
  width?: string;
  margin?: string;
  padding?: string;
  alignItems?: "flex-start" | "flex-end" | "center" | "stretch";
  justifyContent?:
    | "flex-start"
    | "flex-end"
    | "center"
    | "space-between"
    | "space-around"
    | "space-evenly";
}

interface ImageGalleryProps {
  width?: number;
  height?: number;
  src: string;
  borderRadius?: number;
  objectFit?: "cover" | "contain" | "fill" | "none" | "scale-down";
}

export const Container = styled.div<GalleryFlexContainerProps>`
  display: flex;
  height: 100vh;
  flex-direction: ${(props) => props.flex};
  width: ${(props) => props.width};
  margin: ${(props) => props.margin};
  padding: ${(props) => props.padding};
  align-items: ${(props) => props.alignItems};
  justify-content: ${(props) => props.justifyContent};
  background: var(--neutral-white);

  @media ${device.mobile} {
    margin: ${pixelToRem(17)};
    align-items: center;
    justify-content: center;
  }
`;

export const Header = styled.div`
  display: flex;
  @media ${device.mobile} {
    padding-bottom: ${pixelToRem(28)};
  }
`;


export const LogoSymbol = styled.image<GalleryFlexContainerProps>`
  width: ${pixelToRem(72)};
  height: ${pixelToRem(32)};
  background: url("/images/app-logo-symbol.svg") no-repeat;
  @media ${device.mobile} {
    width: ${pixelToRem(60)};
    height: ${pixelToRem(28)};
  }
`;

export const Logo = styled.image<GalleryFlexContainerProps>`
  width: ${pixelToRem(180)};
  height: ${pixelToRem(32)};
  background: url("/images/logo-naya.svg") no-repeat;
  @media ${device.mobile} {
    width: ${pixelToRem(150)};
    height: ${pixelToRem(28)};
  }
`;

export const Main = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: Center;
  height: 100vh;
  width: ${pixelToRem(815)};
  @media ${device.mobile} {
    max-width: 100vw;
    align-items: center;
    justify-content: center;
  }
`;

export const Experience = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: Center;
  align-items: Center;
  height: ${pixelToRem(200)};
  @media ${device.mobile} {
    max-width: 100vw;
    align-items: center;
    justify-content: center;
  }
`;


export const FirstTitle = styled.div`
  color: var(--primary-dark);
  font: var(--font-heading-2);
  text-transform: uppercase;
  letter-spacing: ${pixelToRem(0)};
  @media ${device.mobile} {
    font: var(--font-mobile-text-4);
    letter-spacing: ${pixelToRem(0)};
    padding-bottom: ${pixelToRem(4)};
  }
`;

export const SecondTitleLayout = styled.div`
  display: flex;
  align-items: Center;
`;

export const HomeNa = styled.image<GalleryFlexContainerProps>`
  width: ${pixelToRem(110)};
  height: ${pixelToRem(70)};
  background: url("/images/home-na.png") no-repeat;
  background-size: cover; 
  @media ${device.mobile} {
    width: ${pixelToRem(45)};
    height: ${pixelToRem(32)};
  }
`;

export const SecondTitle = styled.p`
  color: var(--primary-dark);
  font: var(--font-display);
  padding-left: ${pixelToRem(12)};
  @media ${device.mobile} {
    padding-left: ${pixelToRem(4)};
    font: var(--font-mobile-heading-1);
    text-align: center;
  }
  span {
    color: var(--neutral-white);
  }
`;


export const Subtitle = styled.p`
  color: var(--neutral-gray);
  font: var(--text-4);
  max-width: ${pixelToRem(728)};
  @media ${device.mobile} {
    padding: ${pixelToRem(16, 0, 32, 0)};
    text-align: center;
    max-width: ${pixelToRem(307)};
  }
`;

export const GoogleDownLoadTitle = styled.p`
  padding-top: ${pixelToRem(200)};
  margin-bottom: ${pixelToRem(20)};
  color: var(--neutral-light);
  font: var(--text-3);
  max-width: ${pixelToRem(728)};
  @media ${device.mobile} {
    padding: ${pixelToRem(12, 0, 0, 0)};
    text-align: center;
    font: var(--text-2);
    max-width: ${pixelToRem(307)};
  }
`;

export const FooterText = styled.p`
padding-top: ${pixelToRem(44)};
  color: var(--neutral-light);
  font: var(--text-2);
  max-width: ${pixelToRem(728)};
  @media ${device.mobile} {
    padding: ${pixelToRem(16, 0, 32, 0)};
    text-align: center;
    max-width: ${pixelToRem(307)};
  }
`;

export const FooterTextCopy = styled.p`
  padding-top: ${pixelToRem(16)};
  color: var(--neutral-light);
  font: var(--text-1);
  max-width: ${pixelToRem(728)};
  @media ${device.mobile} {
    padding: ${pixelToRem(16, 0, 32, 0)};
    text-align: center;
    max-width: ${pixelToRem(307)};
  }
`;


export const ButtonForGoogle = styled.button<ButtonProps>`
  width: ${({ fullWidth }) => (fullWidth ? "100%" : pixelToRem(240))};
  height: ${pixelToRem(40)};
  border: none;
  border-radius: ${pixelToRem(6)};
  &:hover {
    cursor: pointer;
  }
`;

export const CardsIllustration = styled.image`
  width: ${pixelToRem(600)};
  height: ${pixelToRem(500)};
  position: absolute;
  background-image: url("/images/app-cards.svg");
  background-repeat: no-repeat;
  background-size: cover; 
  right: 0;
  top: ${pixelToRem(400)};
  @media (max-width: ${pixelToRem(2000)}) {
    width: ${pixelToRem(540)};
    height: ${pixelToRem(400)};
    top: ${pixelToRem(450)};
  }
  @media (max-width: ${pixelToRem(1000)}) {
    position: relative;
    order: 4;
    width: ${pixelToRem(472)};
    height: ${pixelToRem(800)};
    top: ${pixelToRem(80)};
    left: 20;
  }
  @media ${device.mobile} {
    position: relative;
    order: 4;
    top: ${pixelToRem(30)};
    width: ${pixelToRem(400)};
    height: ${pixelToRem(300)};
  }
`;

export const PhoneIllustration = styled.image`
  width: ${pixelToRem(1400)};
  height: ${pixelToRem(850)};
  position: absolute;
  background-image: url("/images/home-phone-image.png");
  background-repeat: no-repeat;
  background-size: cover; 
  right: ${pixelToRem(80)};
  @media (max-width: ${pixelToRem(2000)}) {
    width: ${pixelToRem(1000)};
    height: ${pixelToRem(850)};
    top: ${pixelToRem(80)};
  }
  @media (max-width: ${pixelToRem(1400)}) {
    display: none;
    position: relative;
    order: 4;
    width: ${pixelToRem(500)};
    height: ${pixelToRem(300)};
    top: ${pixelToRem(80)};
    left: 20;
  }
`;

export const TopBackground = styled.image`
  width: ${pixelToRem(1000)};
  height: ${pixelToRem(1000)};
  position: absolute;
  background-image: url("/images/home_background.png");
  background-repeat: no-repeat;
  background-size: cover; 
  right: ${pixelToRem(20)};
  top: ${pixelToRem(0)};
  @media (max-width: ${pixelToRem(2000)}) {
    width: ${pixelToRem(1000)};
    height: ${pixelToRem(850)};
    top: ${pixelToRem(80)};
  }
  @media (max-width: ${pixelToRem(1400)}) {
    display: none;
    position: relative;
    order: 4;
    width: ${pixelToRem(500)};
    height: ${pixelToRem(300)};
    top: ${pixelToRem(80)};
    left: 20;
  }
`;

export const DivButton = styled.div`
  width: ${pixelToRem(264)};
  padding-top: ${pixelToRem(16)};
  @media ${device.mobile} {
    padding-bottom: ${pixelToRem(0)};
  }
`;

export const DivIcons = styled(motion.div)`
  display: flex;
  justify-content: space-between;
  max-width: ${pixelToRem(1440)};
  align-items: center;
  width: 100%;
  @media ${device.mobile} {
    flex-direction: column;
    text-align: center;
    gap: ${pixelToRem(64)};
    padding-bottom: ${pixelToRem(72)};
    max-width: ${pixelToRem(222)};
    & image {
      margin: 0 auto;
    }
  }
`;

export const SectionAbout = styled.div<GalleryFlexContainerProps>`
  display: flex;
  justify-content: center;
  background: url("/images/stars.jpg") no-repeat;
  background-size: cover;
  flex-direction: ${(props) => props.flex};
  @media ${device.mobile} {
    display: none;
  }
`;

export const SecondSubTitle = styled.p`
  font: var(--font-heading-1);
  color: var(--text);
  padding-top: ${pixelToRem(14)};
`;

export const GalleryContent = styled.div<GalleryFlexContainerProps>`
  display: flex;
  flex-direction: ${(props) => props.flex};
  margin: ${pixelToRem(150, 100, 157, 300)};
`;

export const DivLogo = styled(motion.div)<GalleryFlexContainerProps>`
  max-width: ${pixelToRem(350)};
  margin-right: ${pixelToRem(30)};
`;

export const DivLogoSpaceY = styled.div`
  width: ${pixelToRem(30)};
  height: ${pixelToRem(60)};
`;

export const ImageGallery = styled.img<ImageGalleryProps>`
  width: ${({ width }) => width && pixelToRem(width)};
  height: ${({ height }) => height && pixelToRem(height)};
  border-radius: ${({ borderRadius }) =>
    borderRadius && pixelToRem(borderRadius)};
  object-fit: ${({ objectFit }) => objectFit};
`;

export const ContainerAbout = styled.div<GalleryFlexContainerProps>`
  display: flex;
  flex-direction: ${(props) => props.flex};
  align-items: ${(props) => props.alignItems};
  justify-content: ${(props) => props.justifyContent};
  width: ${(props) => props.width};
  margin-top: ${pixelToRem(180)};
  gap: ${pixelToRem(200)};
`;

export const TextLogo = styled.p`
  font: var(--font-heading-1);
  color: var(--text);
`;

export const TextSubscribe = styled(motion.p)`
  font: var(--text-3);
  color: var(--mars-light);
  cursor: pointer;
  padding-top: ${pixelToRem(20)};
`;

export const ContainerForm = styled.div`
  display: flex;
  width: 100%;
  background: url("/images/background-stars-form.jpg") no-repeat;
  background-size: cover;
  justify-content: space-around;
`;

export const SectionForm = styled.div`
  flex-direction: column;
  width: 100%;
  justify-content: space-around;
  @media ${device.mobile} {
    display: none;
  }
`;

export const DivForm = styled(motion.div)`
  background: var(--background-form);
  border-radius: ${pixelToRem(20)};
  align-items: center;
  justify-content: center;
  padding: ${pixelToRem(39, 51, 61, 52)};
  margin-left: ${pixelToRem(120)};
  max-height: ${pixelToRem(792)};
`;
export const IconForm = styled.div`
  background: url("/images/icon-ticket.svg") no-repeat;
  width: ${pixelToRem(56)};
  height: ${pixelToRem(56)};
`;
export const TitleForm = styled.div`
  font: var(--font-heading-2);
  color: var(--text);
  max-width: ${pixelToRem(264)};
  padding-top: ${pixelToRem(16)};
`;
export const SubtitleForm = styled.div`
  font: var(--heading-3);
  color: var(--gray-05);
  max-width: ${pixelToRem(308)};
`;


export const DivInputCheckbox = styled.div`
  display: flex;
  padding: ${pixelToRem(24, 0, 32, 0)};
`;


export const SectionFooter = styled.div`
  justify-content: Center;  
  @media ${device.mobile} {
    display: none;
  }
`;

export const DivImageSmoke = styled(motion.div)`
  & img {
    width: 120%;
  }
`;

export const LogoFooter = styled.image<GalleryFlexContainerProps>`
  width: ${pixelToRem(180)};
  height: ${pixelToRem(40)};

  justify-content: Center;
  background: url("/images/logo-naya.svg") no-repeat;
  @media ${device.mobile} {
    width: ${pixelToRem(150)};
    height: ${pixelToRem(28)};
  }
`;

export const DivFooterBottom = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: Center;
`;

export const DivFooterBottom2 = styled.div`
  display: flex;
  flex-direction: column;
  align-items: Center;
  justify-content: Center;
`;

export const DivSocial = styled.div`
  display: flex;
  gap: ${pixelToRem(20)};
  & img {
    width: ${pixelToRem(25)};
    height: ${pixelToRem(25)};
  }
`;

export const DivFooterMenu = styled.div`
  gap: ${pixelToRem(53)};
  & ul {
    display: flex;
    list-style: none;
    gap: ${pixelToRem(53)};
  }
  & a {
    color: var(--text);
    font: var(--text-3);
    text-decoration: none;
  }
`;