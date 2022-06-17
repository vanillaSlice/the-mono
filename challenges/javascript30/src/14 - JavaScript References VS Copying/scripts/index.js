/*
  eslint-disable
    no-console,
    no-unused-vars,
*/

// start with strings, numbers and booleans
let age = 100;
const age2 = age;
console.log(age, age2);
age = 200;
console.log(age, age2);

let name = 'Mike';
const name2 = name;
console.log(name, name2);
name = 'Michael';
console.log(name, name2);

// Let's say we have an array
const players = ['Wes', 'Sarah', 'Ryan', 'Poppy'];

// and we want to make a copy of it.

// You might think we can just do something like this:
const team = players;

console.log(players, team);

// however what happens when we update that array?
team[3] = 'Lux';

console.log(players, team);

// now here is the problem!

// oh no - we have edited the original array too!

// Why? It's because that is an array reference, not an array copy.
// They both point to the same array!

// So, how do we fix this? We take a copy instead!

// one way
const team2 = players.slice();

team2[3] = 'Mike';

console.log(players, team2);

// or create a new array and concat the old one in
const team3 = [].concat(players);

// or use the new ES6 Spread
const team4 = [...players];
team4[3] = 'Mike';

console.log(team3, team4);

const team5 = Array.from(players);

// now when we update it, the original one isn't changed

// The same thing goes for objects, let's say we have a person object

// with Objects
const person = {
  name: 'Mike',
  age: 24,
};

// and think we make a copy:
const captain = person;
captain.number = 99;

console.log(person, captain);

// how do we take a copy instead?
const captain2 = Object.assign({}, person, {
  number2: 999,
});

console.log(person, captain2);

// We will hopefully soon see the object ...spread
// const captain3 = {...person};

// Things to note - this is only 1 level deep - both for Arrays and Objects.
// lodash has a cloneDeep method, but you should think twice before using it.
const mike = {
  name: 'Mike',
  age: 24,
  social: {
    twitter: '@vanillaSlice',
    instagram: 'vanillaSlice',
  },
};

console.log(mike);

const dev = Object.assign({}, mike);

dev.name = 'Michael';

console.log(mike, dev);

dev.social.twitter = '@coolman';

console.log(mike, dev);

const dev2 = JSON.parse(JSON.stringify(dev));

dev2.social.twitter = '@vanillaSlice';

console.log(dev, dev2);
