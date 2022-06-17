/* eslint-disable no-param-reassign */

/*
 * Elements
 */

const clearAllBtn = document.querySelector('.js-clear-all');
const checkAllBtn = document.querySelector('.js-check-all');
const uncheckAllBtn = document.querySelector('.js-uncheck-all');
const itemsListElement = document.querySelector('.js-items');
const addItemsElement = document.querySelector('.js-add-items');

/*
 * Variables
 */

const items = JSON.parse(localStorage.getItem('items')) || [];

/*
 * Functions
 */

function populateList() {
  itemsListElement.innerHTML = items.map((item, index) => (
    `<li class="items__item">
      <input class="item__input js-checkbox" type="checkbox" data-index="${index}" id="item${index}" ${item.done ? 'checked' : ''}>
      <label class="item__label" for="item${index}">${item.text}</label>
    </li>`)).join('');
}

function clearItems() {
  items.length = 0;
  localStorage.removeItem('items');
  populateList(items, itemsListElement);
}

function checkItems() {
  items.forEach((item) => {
    item.done = true;
  });
  localStorage.setItem('items', JSON.stringify(items));
  populateList(items, itemsListElement);
}

function uncheckItems() {
  items.forEach((item) => {
    item.done = false;
  });
  localStorage.setItem('items', JSON.stringify(items));
  populateList(items, itemsListElement);
}

function addItem(e) {
  e.preventDefault();
  const text = this.querySelector('.js-item-text').value;
  const item = {
    text,
    done: false,
  };
  items.push(item);
  populateList(items, itemsListElement);
  localStorage.setItem('items', JSON.stringify(items));
  this.reset();
}

function toggleDone(e) {
  if (!e.target.matches('.js-checkbox')) {
    return;
  }
  const el = e.target;
  const { index } = el.dataset;
  items[index].done = !items[index].done;
  localStorage.setItem('items', JSON.stringify(items));
  populateList(items, itemsListElement);
}

/*
 * Initialise
 */

clearAllBtn.addEventListener('click', clearItems);
checkAllBtn.addEventListener('click', checkItems);
uncheckAllBtn.addEventListener('click', uncheckItems);
addItemsElement.addEventListener('submit', addItem);
itemsListElement.addEventListener('click', toggleDone);
populateList(items, itemsListElement);
