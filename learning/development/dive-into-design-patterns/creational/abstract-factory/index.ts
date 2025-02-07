export function loadConfigFromFile () {
  return "Mac";
}

export class ApplicationConfigurator {
  main(): void {
    const os: string = loadConfigFromFile();

    let factory: GUIFactory;;

    if (os === "Windows") {
      factory = new WinFactory();
    } else if (os === "Mac") {
      factory = new MacFactory();
    } else {
      throw new Error("Error! Unknown operating system.");
    }

    const app = new Application(factory);
    app.createUI();
    app.paint();
  }
}

export class Application {
  private button: Button;

  constructor(private factory: GUIFactory) {}

  createUI(): void {
    this.button = this.factory.createButton();
  }

  paint(): void {
    this.button.paint();
  }
}

export interface GUIFactory {
  createButton(): Button;
  createCheckbox(): Checkbox;
}

export class WinFactory implements GUIFactory {
  createButton(): Button {
    return new WinButton();
  }

  createCheckbox(): Checkbox {
    return new WinCheckbox();
  }
}

export class MacFactory implements GUIFactory {
  createButton(): Button {
    return new MacButton();
  }

  createCheckbox(): Checkbox {
    return new MacCheckbox();
  }
}

export interface Button {
  paint(): void;
}

export class WinButton implements Button {
  paint(): void {
    console.log("Rendering a Windows button");
  }
}

export class MacButton implements Button {
  paint(): void {
    console.log("Rendering a Mac button");
  }
}

export interface Checkbox {
  paint(): void;
}

export class WinCheckbox implements Checkbox {
  paint(): void {
    console.log("Rendering a Windows checkbox");
  }
}

export class MacCheckbox implements Checkbox {
  paint(): void {
    console.log("Rendering a Mac checkbox");
  }
}
