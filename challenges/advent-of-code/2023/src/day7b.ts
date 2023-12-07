import { readInput } from './util.ts';

const charCardStrengths = {
  A: 14,
  K: 13,
  Q: 12,
  T: 10,
  J: 1,
} as Record<string, number>;

export const totalWinnings = (input: string): number => {
  return input.split(/\r?\n/)
    .filter(line => line)
    .map(line => line.split(' '))
    .map(pair => ({
      cards: pair[0],
      bid: Number(pair[1]),
    }))
    .map(hand => ({
      ...hand,
      cardCounts: hand.cards.split('').reduce((acc, char) => {
        acc[char] = acc[char] ? acc[char] + 1 : 1;
        return acc;
      }, {} as Record<string, number>),
    }))
    .map(hand => {
      let cardWithMaxCount = '';
      let maxCount = 0;
      let numberOfJs = 0;
      const updatedCardCounts = Object.entries(hand.cardCounts).reduce((acc, [card, count]) => {
        if (card === 'J') {
          numberOfJs = count;
          return acc;
        } else if (count > maxCount) {
          maxCount = count;
          cardWithMaxCount = card;
        }
        acc[card] = count;
        return acc;
      }, {} as Record<string, number>);

      updatedCardCounts[cardWithMaxCount] += numberOfJs;

      return {
        ...hand,
        cardCounts: Object.values(updatedCardCounts),
      }
    })
    .map(hand => {
      let strength: number;
      const { cardCounts } = hand;
      if (cardCounts.length === 5) {
        strength = 1;
      } else if (cardCounts.length === 4) {
        strength = 2;
      } else if (cardCounts.length === 3) {
        strength = cardCounts.includes(2) ? 3 : 4;
      } else if (cardCounts.length === 2) {
        strength = cardCounts.includes(3) ? 5 : 6;
      } else {
        strength = 7;
      }
      return {
        ...hand,
        strength,
      }
    })
    .sort((leftHand, rightHand) => {
      if (leftHand.strength === rightHand.strength) {
        for (let i = 0; i < leftHand.cards.length; i++) {
          const leftHandChar = leftHand.cards[i];
          const rightHandChar = rightHand.cards[i];
          if (leftHandChar !== rightHandChar) {
            const leftHandCharStrength = charCardStrengths[leftHandChar] ?? Number(leftHandChar);
            const rightHandCharStrength = charCardStrengths[rightHandChar] ?? Number(rightHandChar);
            return leftHandCharStrength - rightHandCharStrength;
          }
        }
      }
      return leftHand.strength - rightHand.strength;
    })
    .reduce((acc, hand, index) => acc + (hand.bid * (index + 1)), 0);
};

if (Deno.args && Deno.args[0] == 'solve') {
  const text = await readInput('day7b.txt');
  console.log(totalWinnings(text));
}
