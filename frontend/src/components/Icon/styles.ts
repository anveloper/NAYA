import styled from "styled-components";
import pixelToRem from "../../utils/pxToRem";

import { IconProps } from ".";

export const DivIcon = styled.div`
  display: flex;
  flex-direction: column;
  align-items: Center;
  max-width: ${pixelToRem(216)};
`;

type ImageProps = Omit<IconProps, "txt">;

export const ImageIcon = styled.image<ImageProps>`
  width: ${pixelToRem(100)};
  height: ${pixelToRem(80)};

  background-image: ${(props) => `url(${props.src})`};
  background-repeat: no-repeat;
`;

export const TextIcon = styled.p`
  color: var(--primary-dark);
  font: var(--font-heading-3);
  padding-top: ${pixelToRem(8.5)};
`;