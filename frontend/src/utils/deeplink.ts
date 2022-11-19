export const redireactApp = (imageUrl: String) => {
  exeDeepLink(imageUrl);
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
        : "https://play.google.com/store/apps/details?id=com.youme.naya";
  } // 아이폰 아직
};

function exeDeepLink(imageUrl: String) {
  const url = `naya://com.youme.naya/imageUrl=${imageUrl}`;
  // eslint-disable-next-line no-restricted-globals
  location.href = url;
}
