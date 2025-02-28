export interface Prototype {
  clone(): Prototype;
}

export abstract class Shape implements Prototype {
  constructor(public colour: string) {}

  abstract clone(): Shape;
  abstract display(): void;
}

export class Rectangle extends Shape {
  constructor(public width: number, public height: number, public colour: string) {
    super(colour);
  }

  clone(): Rectangle {
    return new Rectangle(this.width, this.height, this.colour);
  }

  display(): void {
    console.log(`Rectangle: Width = ${this.width}, Height = ${this.height}, Colour = ${this.colour}`);
  }
}

export class Circle extends Shape {
  constructor(public radius: number, public colour: string) {
    super(colour);
  }

  clone(): Circle {
    return new Circle(this.radius, this.colour);
  }

  display(): void {
    console.log(`Circle: Radius = ${this.radius}, Colour = ${this.colour}`);
  }
}

export class Application {
  main(): void {
    const originalRectangle = new Rectangle(5, 10, "Red");
    const clonedRectangle = originalRectangle.clone();
    clonedRectangle.colour = "Blue";
    clonedRectangle.height = 25;

    const originalCircle = new Circle(20, "Yellow");
    const clonedCircle = originalCircle.clone();
    clonedCircle.radius = 10;

    originalRectangle.display();
    originalCircle.display();

    clonedRectangle.display();
    clonedCircle.display();
  }
}
