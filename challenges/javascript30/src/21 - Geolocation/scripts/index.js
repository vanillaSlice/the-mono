/*
  eslint-disable
    no-console,
    no-alert,
*/

const arrowElement = document.querySelector('.js-arrow');
const speedElement = document.querySelector('.js-speed-value');
const latitudeElement = document.querySelector('.js-latitude-value');
const longitudeElement = document.querySelector('.js-longitude-value');

navigator.geolocation.watchPosition((data) => {
  speedElement.textContent = data.coords.speed || 0;
  latitudeElement.textContent = data.coords.latitude.toFixed(5);
  longitudeElement.textContent = data.coords.longitude.toFixed(5);
  arrowElement.style.transform = `rotate(${data.coords.heading}deg)`;
}, (err) => {
  console.error(err);
  window.alert('Could not access geolocation data');
});
