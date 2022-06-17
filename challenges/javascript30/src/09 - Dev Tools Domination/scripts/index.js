/* eslint-disable no-console */

const dogs = [{
  name: 'Snickers',
  age: 2,
}, {
  name: 'Hugo',
  age: 8,
}];

// Regular
console.log('Hello');

// Interpolated
console.log('Hello I am an %s string', 'interpolated');

// Styled
console.log('%c I am some great text', 'font-size: 50px; background: red;');

// warning!
console.warn('OH NOOOOOO!');

// Error :|
console.error('Shit!');

// Info
console.info('Crocodiles eat 3-4 people per year');

// Testing
console.assert(1 === 2, 'That is wrong');

// clearing
console.clear();

// Viewing DOM Elements
const p = document.querySelector('p');
console.log(p);
console.dir(p);

console.clear();

// Grouping together
dogs.forEach((dog) => {
  console.groupCollapsed(`${dog.name}`);
  console.log(`This is ${dog.name}`);
  console.log(`${dog.name} is ${dog.age} years old`);
  console.log(`${dog.name} is ${dog.age * 7} dog years old`);
  console.groupEnd(`${dog.name}`);
});

// counting
console.count('Mike');
console.count('Mike');
console.count('Mike');
console.count('Mike');
console.count('Mike');

// timing
console.time('fetching data');
fetch('https://api.github.com/users/wesbos')
  .then(data => data.json())
  .then((data) => {
    console.timeEnd('fetching data');
    console.log(data);
  });

console.table(dogs);
