import React, { useEffect, useRef, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate, useParams, Link, redirect } from "react-router-dom";
import { useAppDispatch } from "../app/hooks";
import styles from "./Card.module.css";
import { getCardInfo, selectImageUrl } from "./cardSlice";
import IMG from "../assets/sample_card.svg";
import PLAY_IMG from "../assets/google_play_button.svg";
import { Helmet } from "react-helmet-async";
import { isMobile } from "react-device-detect";

const Card = () => {
  const { userId, sendCardId }: any = useParams();
  const contentRef = useRef<HTMLDivElement | null>(null);
  const imageUrl = useSelector(selectImageUrl);
  const dispatch = useAppDispatch();
  // render
  useEffect(() => {
    dispatch(getCardInfo({ userId, sendCardId }));
    console.log(userId, sendCardId);
  }, [dispatch, sendCardId, userId]);
  useEffect(() => {
    console.log(imageUrl);
  }, [imageUrl]);

  // action
  const [rotateX, setRotateX] = useState(0.0);
  const [rotateY, setRotateY] = useState(0.0);
  const [transition, setTransition] = useState("all .3s ease");
  const [translateZ, setTranslateZ] = useState("0px");
  // mouse
  const mousemoveOn = (e: MouseEvent) => {
    // console.log(e.pageX, e.pageY);
    let xAxis = (window.innerWidth / 2 - e.pageX) / 12;
    let yAxis = (window.innerHeight / 2 - e.pageY) / 12;
    setRotateX(xAxis);
    setRotateY(yAxis);
  };
  const mouseenterOn = (e: MouseEvent) => {
    setTransition("none");
    setTranslateZ("100px");
  };

  const mouseleaveOn = (e: MouseEvent) => {
    setRotateX(0.0);
    setRotateY(0.0);
    setTransition("all .3s ease");
    setTranslateZ("0px");
  };

  //touch
  const touchmoveOn = (ev: TouchEvent) => {
    // console.log(e.pageX, e.pageY);
    let xAxis = (window.innerWidth / 2 - ev.touches[0].pageX) / 12;
    let yAxis = (window.innerHeight / 2 - ev.touches[0].pageY) / 12;
    setRotateX(xAxis);
    setRotateY(yAxis);
  };
  const touchstartOn = (ev: TouchEvent) => {
    setTransition("none");
    setTranslateZ("100px");
  };

  const touchendOn = (ev: TouchEvent) => {
    setRotateX(0.0);
    setRotateY(0.0);
    setTransition("all .3s ease");
    setTranslateZ("0px");
  };

  useEffect(() => {
    if (!contentRef.current) return;
    const content: HTMLDivElement = contentRef.current;
    // mouse
    content.addEventListener("mousemove", mousemoveOn);
    content.addEventListener("mouseenter", mouseenterOn);
    content.addEventListener("mouseleave", mouseleaveOn);
    // touch
    content.addEventListener("touchmove", touchmoveOn);
    content.addEventListener("touchstart", touchstartOn);
    content.addEventListener("touchend", touchendOn);
    return () => {
      // mouse
      content.removeEventListener("mousemove", mousemoveOn);
      content.removeEventListener("mouseenter", mouseenterOn);
      content.addEventListener("mouseleave", mouseleaveOn);
      // touch
      content.removeEventListener("touchmove", touchmoveOn);
      content.removeEventListener("touchstart", touchstartOn);
      content.removeEventListener("touchend", touchendOn);
    };
  });

  // 어플이 있는 지 체크
  // const [isOpenModal, setIsModalOpen] = useState(true);

  const redireactApp = () => {
    exeDeepLink();
    checkInstallApp();
  };

  function checkInstallApp() {
    function clearTimers() {
      clearInterval(check);
      clearTimeout(timer);
    }

    function isHideWeb() {
      if (document.hidden) {
        clearTimers();
      }
    }
    const check = setInterval(isHideWeb, 200);

    const timer = setTimeout(function () {
      redirectStore();
    }, 500);
  }

  const redirectStore = () => {
    const ua = navigator.userAgent.toLowerCase();

    if (window.confirm("스토어로 이동하시겠습니까?")) {
      // eslint-disable-next-line no-restricted-globals
      location.href =
        ua.indexOf("android") > -1
          ? "https://play.google.com/store/apps/details?id=com.youme.naya"
          : "https://apps.apple.com/kr/app/com.youme.naya";
    }
  };

  function exeDeepLink() {
    const url = `naya://com.youme.naya/${imageUrl}`;
    // eslint-disable-next-line no-restricted-globals
    location.href = url;
  }

  return (
    <div className={styles.container}>
      <Helmet>
        <title>{"나야(Naya) - 카드공유"}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      </Helmet>

      <div
        className={styles.content}
        ref={contentRef}
        style={{
          transform: `rotateY(${rotateY}deg) rotateX(${rotateX}deg)`,
          transition: `${transition}`,
        }}
      >
        <img
          className={styles.cardImage}
          src={imageUrl ? imageUrl : IMG}
          alt=""
          style={{
            transform: `translateZ(${translateZ})`,
            transition: "all .3s ease",
          }}
        />
      </div>
      {/* <a href="https://play.google.com/store/apps/details?id=com.youme.naya">
        <img
          src={PLAY_IMG}
          alt="Google Play Button"
          className={styles.playBtn}
        />
      </a> */}
      <button
        onClick={redireactApp}
        style={{ position: "sticky", border: "none" }}
      >
        <img
          src={PLAY_IMG}
          alt="Google Play Button"
          className={styles.playBtn}
        />
      </button>
    </div>
  );
};

export default Card;
