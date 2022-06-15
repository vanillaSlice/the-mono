window.addEventListener('load', () => {
  
  const secondHand = document.querySelector('.second.hand');
  const minuteHand = document.querySelector('.minute.hand');
  const hourHand = document.querySelector('.hour.hand');
  
  function setDate(disableTransition = false) {
    const now = new Date();
    
    const seconds = now.getSeconds();
    const secondsDegrees = ((seconds / 60) * 360) + 90;
    secondHand.style.transform = `rotate(${secondsDegrees}deg)`;
    secondHand.style.transitionDuration = (disableTransition || seconds === 0) ? '0s' : '0.5s';
    
    const minutes = now.getMinutes();
    const minutesDegrees = ((minutes / 60) * 360) + ((seconds / 60) * 6) + 90;
    minuteHand.style.transform = `rotate(${minutesDegrees}deg)`;
    minuteHand.style.transitionDuration = (disableTransition || minutes === 0) ? '0s' : '0.5s';
    
    const hours = now.getHours();
    const hoursDegrees = ((hours / 12) * 360) + ((minutes / 60) * 30) + 90;
    hourHand.style.transform = `rotate(${hoursDegrees}deg)`;
    hourHand.style.transitionDuration = (disableTransition || hours === 0 || hours === 12) ? '0s' : '0.5s';
  }
  
  setInterval(setDate, 1000);
  setDate(true);
  
});
  