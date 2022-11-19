import React from "react";
import { Helmet } from "react-helmet-async";
import { redireactApp } from "../utils/deeplink";
import PLAY_IMG from "../assets/google_play_button.svg";
import styles from "./Home.module.css";

const Home = () => {
  return (
    <div>
      <Helmet>
        <title>{"나야(Naya) - 나를 소개하는 가장 쉬운 방법"}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      </Helmet>
      <div>
        {/* 딥링크 방식 */}
        <button
          onClick={() => redireactApp("")}
          style={{ position: "sticky", border: "none" }}
        >
          <img
            src={PLAY_IMG}
            alt="Google Play Button"
            className={styles.playBtn}
          />
        </button>

        <div style={{ padding: "10px" }}></div>
        {/* 일반 A링크 방식 */}

        <a href="https://play.google.com/store/apps/details?id=com.youme.naya">
          <img
            src={PLAY_IMG}
            alt="Google Play Button"
            className={styles.playBtn}
          />
        </a>
      </div>
    </div>
  );
};

export default Home;
