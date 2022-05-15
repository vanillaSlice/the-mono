import { version } from '../../package.json';

/*
 * Variables
 */

let inputs = ['0'];

/*
 * DOM Elements
 */

const screenTextElement = document.querySelector('.js-screen-text');
const acBtnElement = document.querySelector('.js-ac-btn');
const ceBtnElement = document.querySelector('.js-ce-btn');
const numberBtnElements = [].slice.call(document.querySelectorAll('.js-number-btn'));
const operationBtnElements = [].slice.call(document.querySelectorAll('.js-operation-btn'));
const decimalPointBtnElement = document.querySelector('.js-decimal-point-btn');
const equalsBtnElement = document.querySelector('.js-equals-btn');
const versionElement = document.querySelector('.js-version');

/*
 * Functions
 */

function resetInputs() {
  inputs = ['0'];
}

function displayInputs() {
  screenTextElement.innerText = inputs.join('');
}

function clearAll() {
  resetInputs();
  displayInputs();
}

function hasMoreThanOneInput() {
  return inputs.length > 1;
}

function removeLastInput() {
  inputs.pop();
}

function clearEntry() {
  if (hasMoreThanOneInput()) {
    removeLastInput();
    displayInputs();
  } else {
    clearAll();
  }
}

function getLastInput() {
  return inputs[inputs.length - 1];
}

function isAnOperation(value) {
  return value === '÷' || value === '×' || value === '+' || value === '−';
}

function setLastInput(value) {
  inputs[inputs.length - 1] = value;
}

function appendToLastInput(value) {
  inputs[inputs.length - 1] += value;
}

function appendNumber(e) {
  const number = e.target.innerText;
  const last = getLastInput();
  if (isAnOperation(last)) {
    inputs.push(number);
  } else if (last === '0') {
    setLastInput(number);
  } else {
    appendToLastInput(number);
  }
  displayInputs();
}

function appendMinus() {
  const last = getLastInput();
  if (last === '0' && !hasMoreThanOneInput()) {
    inputs[0] = '−';
  } else if (last !== '−') {
    inputs.push('−');
  }
}

function appendOperation(e) {
  const operation = e.target.innerText;
  if (operation === '−') {
    appendMinus();
  } else if (!isAnOperation(getLastInput())) {
    inputs.push(operation);
  }
  displayInputs();
}

function containsDecimalPoint(value) {
  return value.includes('.');
}

function appendDecimalPoint() {
  const last = getLastInput();
  if (!containsDecimalPoint(last)) {
    appendToLastInput('.');
    displayInputs();
  }
}

function normaliseInputs() {
  return inputs.join('').replace(/÷/g, '/').replace(/×/g, '*').replace(/−/g, '-');
}

function evaluate(expression) {
  try {
    return eval(expression);
  } catch (error) {
    return error;
  }
}

function isInvalidResult(result) {
  return !Number.isFinite(result);
}

function handleCalculationError() {
  screenTextElement.innerText = 'Error';
  resetInputs();
}

function normaliseResult(result) {
  return String(result).replace(/-/g, '−');
}

function calculateResult() {
  const expression = normaliseInputs();
  const result = evaluate(expression);
  if (isInvalidResult(result)) {
    handleCalculationError();
  } else {
    inputs = [normaliseResult(result)];
    displayInputs();
  }
}

/*
 * Initialise
 */

acBtnElement.addEventListener('click', clearAll);
ceBtnElement.addEventListener('click', clearEntry);
numberBtnElements.forEach(btn => btn.addEventListener('click', appendNumber));
operationBtnElements.forEach(btn => btn.addEventListener('click', appendOperation));
decimalPointBtnElement.addEventListener('click', appendDecimalPoint);
equalsBtnElement.addEventListener('click', calculateResult);
versionElement.innerText = version;
