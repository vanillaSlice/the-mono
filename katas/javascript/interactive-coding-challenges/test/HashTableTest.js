const assert = require('assert');
const HashTable = require('../src/HashTable');

describe('HashTable', () => {
  it('get with no matching key returns undefined', () => {
    const hashTable = new HashTable();
    assert.equal(hashTable.get(5), undefined);
  });

  it('get with matching key returns value', () => {
    const hashTable = new HashTable();
    hashTable.set(5, 'djent');
    assert.equal(hashTable.get(5), 'djent');
  });

  it('set no matching key adds value', () => {
    const hashTable = new HashTable();
    assert.equal(hashTable.get(2), undefined);
    hashTable.set(2, 'derp');
    assert.equal(hashTable.get(2), 'derp');
  });

  it('set matching key updates value', () => {
    const hashTable = new HashTable();
    hashTable.set(10, 'herp');
    assert.equal(hashTable.get(10), 'herp');
    hashTable.set(10, 'derp');
    assert.equal(hashTable.get(10), 'derp');
  });

  it('remove removes value', () => {
    const hashTable = new HashTable();
    hashTable.set(1, 'poop');
    assert.equal(hashTable.get(1), 'poop');
    hashTable.remove(1);
    assert.equal(hashTable.get(1), undefined);
  });
});
