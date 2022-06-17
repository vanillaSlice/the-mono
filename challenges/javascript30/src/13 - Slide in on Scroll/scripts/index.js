/*
 * Elements
 */

const sliderImgElements = document.querySelectorAll('.js-slide-in');

/*
 * Functions
 */

function debounce(func) {
  let timeout;
  return () => {
    const later = () => {
      timeout = null;
    };
    const callNow = !timeout;
    clearTimeout(timeout);
    timeout = setTimeout(later, 20);
    if (callNow) {
      func.apply(this);
    }
  };
}

function checkSlide() {
  sliderImgElements.forEach((sliderImage) => {
    const slideInAt = (window.scrollY + window.innerHeight) - (sliderImage.height / 2);
    const imageBottom = sliderImage.offsetTop + sliderImage.height;
    const isHalfShown = slideInAt > sliderImage.offsetTop;
    const isNotScrolledPast = window.scrollY < imageBottom;
    if (isHalfShown && isNotScrolledPast) {
      sliderImage.classList.add('active');
    } else {
      sliderImage.classList.remove('active');
    }
  });
}

/*
 * Initialise
 */

window.addEventListener('scroll', debounce(checkSlide));
