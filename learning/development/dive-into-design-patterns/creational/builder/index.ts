export interface Engine {
};

export class Car {
  private _tripComputer: boolean;
  private _gps: boolean;
  private _seats?: number;
  private _engine?: Engine;

  constructor(
    tripComputer: boolean,
    gps: boolean,
    seats?: number,
    engine?: Engine,
  ) {
    this._tripComputer = tripComputer;
    this._gps = gps;
    this._seats = seats;
    this._engine = engine;
  }

  get tripComputer(): boolean {
    return this._tripComputer;
  }

  get gps(): boolean {
    return this._gps;
  }

  get seats(): number | undefined {
    return this._seats;
  }

  get engine(): Engine | undefined {
    return this._engine;
  }
}

export class Manual {
  private _tripComputer: boolean;
  private _gps: boolean;
  private _seats?: number;
  private _engine?: Engine;

  constructor(
    tripComputer: boolean,
    gps: boolean,
    seats?: number,
    engine?: Engine,
  ) {
    this._tripComputer = tripComputer;
    this._gps = gps;
    this._seats = seats;
    this._engine = engine;
  }

  get tripComputer(): boolean {
    return this._tripComputer;
  }

  get gps(): boolean {
    return this._gps;
  }

  get seats(): number | undefined {
    return this._seats;
  }

  get engine(): Engine | undefined {
    return this._engine;
  }
}

export interface Builder {
  reset(): Builder;
  setTripComputer(): Builder;
  setGPS(): Builder;
  setSeats(seat: number): Builder;
  setEngine(engine: Engine): Builder;
}

export class CarBuilder implements Builder {
  private tripComputer: boolean;
  private gps: boolean;
  private seats?: number;
  private engine?: Engine;

  constructor() {
    this.reset();
  }

  reset(): CarBuilder {
    this.tripComputer = false;
    this.gps = false;
    this.seats = undefined;
    this.engine = undefined;
    return this;
  }

  setTripComputer(): CarBuilder {
    this.tripComputer = true;
    return this;
  }

  setGPS(): CarBuilder {
    this.gps = true;
    return this;
  }

  setSeats(seats: number): CarBuilder {
    this.seats = seats;
    return this;
  }

  setEngine(engine: Engine): CarBuilder {
    this.engine = engine;
    return this;
  }

  build(): Car {
    const car = new Car(this.tripComputer, this.gps, this.seats, this.engine);
    this.reset();
    return car;
  }
}

export class CarManualBuilder implements Builder {
  private tripComputer: boolean;
  private gps: boolean;
  private seats?: number;
  private engine?: Engine;

  constructor() {
    this.reset();
  }

  reset(): CarManualBuilder {
    this.tripComputer = false;
    this.gps = false;
    this.seats = undefined;
    this.engine = undefined;
    return this;
  }

  setTripComputer(): CarManualBuilder {
    this.tripComputer = true;
    return this;
  }

  setGPS(): CarManualBuilder {
    this.gps = true;
    return this;
  }

  setSeats(seats: number): CarManualBuilder {
    this.seats = seats;
    return this;
  }

  setEngine(engine: Engine): CarManualBuilder {
    this.engine = engine;
    return this;
  }

  build(): Manual {
    const manual = new Manual(this.tripComputer, this.gps, this.seats, this.engine);
    this.reset();
    return manual;
  }
}

export class Director {
  makeSUV(builder: Builder): void {
    builder.reset()
      .setGPS()
      .setSeats(7)
      .setTripComputer();
  }

  makeSportsCar(builder: Builder): void {
    builder.reset()
      .setSeats(2)
      .setGPS();
  }
}

export class Application {
  main(): void {
    const director = new Director();

    const carBuilder = new CarBuilder();
    director.makeSportsCar(carBuilder);
    const car = carBuilder.build();

    const carManualBuilder = new CarManualBuilder();
    director.makeSUV(carManualBuilder);
    const manual = carManualBuilder.build();
  }
}
