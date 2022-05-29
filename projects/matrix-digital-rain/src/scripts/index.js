function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

class Symbol {
  constructor(options) {
    this.x = options.x;
    this.y = options.y;
    this.speed = options.speed;
    this.changeRate = options.changeRate;
    this.colour = options.colour;
    this.yThreshold = height + textSize();
  }

  draw() {
    if (frameCount % this.changeRate === 0 || !this.character) {
      this.character = char(randomInt(65381, 65440));
    }
    this.y = (this.y > this.yThreshold) ? 0 : this.y + this.speed;
    fill(this.colour);
    text(this.character, this.x, this.y);
  }
}

class Stream {
  constructor(options) {
    const {
      x,
      y,
      speed,
      length,
    } = options;

    this.symbols = [];

    for (let i = 0; i < length; i += 1) {
      const baseLightness = (1 - (i / length));
      const lightnessMultiplier = (i < 3) ? (100 - (i * 5)) : 50;

      const symbol = new Symbol({
        x,
        y: y - (textSize() * i),
        speed,
        changeRate: randomInt(100, 400),
        colour: [120, 100, baseLightness * lightnessMultiplier],
      });

      this.symbols.push(symbol);
    }
  }

  draw() {
    this.symbols.forEach((symbol) => symbol.draw());
  }
}

class Streams {
  constructor() {
    this.streams = [];

    for (let i = 0; i < window.innerWidth / textSize(); i += 1) {
      const stream = new Stream({
        x: i * textSize(),
        y: randomInt(-1000, 0),
        speed: randomInt(3, 7),
        length: randomInt(1, window.innerHeight / textSize()),
      });

      this.streams.push(stream);
    }
  }

  draw() {
    this.streams.forEach((stream) => stream.draw());
  }
}

let streams;

window.setup = () => {
  createCanvas(window.innerWidth, window.innerHeight);
  textFont('monospace', 16);
  colorMode(HSL);
  streams = new Streams();
};

window.draw = () => {
  background(0, 0, 0, 0.6);
  streams.draw();
};

window.windowResized = () => {
  resizeCanvas(window.innerWidth, window.innerHeight);
  streams = new Streams();
};
