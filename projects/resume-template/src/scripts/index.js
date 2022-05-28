const pageBtnElements = [].slice.call(document.querySelectorAll('input[name="page"]'));
const pageElements = [].slice.call(document.querySelectorAll('.page'));

// show page depending on which button has been clicked
pageBtnElements.forEach((pageBtnElement) => {
  pageBtnElement.addEventListener('change', (e) => {
    pageElements.forEach((pageElement) => {
      pageElement.classList.toggle('hidden', pageElement.id !== e.target.value);
    });
  });
});
