import React from "react";
import { Helmet } from "react-helmet-async";
import { redireactApp } from "../utils/deeplink";
import PLAY_IMG from "../assets/google_play_button.svg";
import styles from "./Home.module.css";
import { GlobalStyles } from '../styles/globalstyles'
import pixelToRem from '../utils/pxToRem'
import {
  Container, Header, Logo, Main, FirstTitle, SecondTitle, Subtitle,  DivButton,
CardsIllustration, DivImageSmoke, DivFooterBottom, LogoSymbol, HomeNa, SecondTitleLayout, GoogleDownLoadTitle, ButtonForGoogle, PhoneIllustration, 
TopBackground, LogoFooter, DivFooterBottom2, FooterTextCopy, FooterText
} from '../styles/styles'


const Home = () => {
  return (
    <>
    <GlobalStyles />
      <Helmet>
        <title>{"나야(Naya) - 나를 소개하는 가장 쉬운 방법"}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      </Helmet>
      <Container flex='column' margin={pixelToRem(24, 120)}>
    
        <Header>
          <LogoSymbol />
          <Logo />
        </Header>

        <Main>
          <FirstTitle>첫 만남이 어려운 당신을 위한</FirstTitle>
          <SecondTitleLayout>
            <HomeNa />
          <SecondTitle>를 소개하는 가장 쉬운 방법</SecondTitle></SecondTitleLayout>
          

          <Subtitle>Naya와 함께 명함과 일정을 관리하고, 나만의 방식으로 나를 공유해보세요!
          </Subtitle>

          
          <TopBackground />
          <PhoneIllustration />
          <CardsIllustration />

          <GoogleDownLoadTitle>구글 플레이 스토어에서 다운로드 하기</GoogleDownLoadTitle>
          <DivButton>
            <ButtonForGoogle
            onClick={() => redireactApp("")}
            >
                <img
                  src={PLAY_IMG}
                  alt="Google Play Button"
                  className={styles.playBtn}
                />
            </ButtonForGoogle>
          </DivButton>
        </Main>
      </Container>


      {/* Footer */}
      {/* <DivFooterBottom>
          
          <DivFooterBottom2>
            <LogoFooter />
            <FooterText>Naya by Team.B104 | 김성찬 · 김정윤 · 안성진 · 연창모 · 주혜령 · 진윤아</FooterText>
            <FooterTextCopy>© 2022 Naya All Rights Reserved</FooterTextCopy>
          </DivFooterBottom2>
      
    
          <DivImageSmoke

            whileInView="visible"
            initial="initial"
            viewport={{ once: true }}
            variants={{
              initial: { opacity: 0, y: 0 },
              visible: {
                opacity: 1,
                y: 0,
                transition: { duration: 0.7, delay: 0.9 },
              },
            }}
          >  
            <img src="/images/smoke.svg" alt="" />
          </DivImageSmoke>
        </DivFooterBottom> */}


        {/* 딥링크 방식 */}
        {/* <button
          onClick={() => redireactApp("")}
          style={{ position: "sticky", border: "none" }}
        >
          <img
            src={PLAY_IMG}
            alt="Google Play Button"
            className={styles.playBtn}
          />
        </button> */}

        {/* <div style={{ padding: "10px" }}></div> */}
        {/* 일반 A링크 방식 */}

        {/* <a href="https://play.google.com/store/apps/details?id=com.youme.naya">
          <img
            src={PLAY_IMG}
            alt="Google Play Button"
            className={styles.playBtn}
          />
        </a> */}

    </>

  );
};

export default Home;
