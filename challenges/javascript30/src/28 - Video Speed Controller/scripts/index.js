const speedElement = document.querySelector('.js-speed');
const speedBarElement = speedElement.querySelector('.js-speed-bar');
const videoElement = document.querySelector('.js-video');

speedElement.addEventListener('mousemove', (e) => {
  const y = e.pageY - speedElement.offsetTop;
  const percent = y / speedElement.offsetHeight;
  const min = 0.4;
  const max = 4;
  const height = `${Math.round(percent * 100)}%`;
  const playbackRate = percent * (max - min) + min;
  speedBarElement.style.height = height;
  speedBarElement.textContent = `${playbackRate.toFixed(2)}x`;
  videoElement.playbackRate = playbackRate;
});
