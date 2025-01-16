export class Application {

  private dialog: Dialog;

  constructor(private os: string) {}

  initialise(): void {
    this.dialog = new Dialog(this.os);
  }

  main(): void {
    this.initialise();
    this.dialog.render();
  }
}

export class Dialog {
  constructor(private os: string) {}

  render(): void {
    const okButton = new Button(this.os);
    okButton.onClick(() => console.log("Closing dialog."));
    okButton.render();

    const inputBox = new InputBox(this.os);
    inputBox.onKeyStroke(() => console.log("Key hit."));
    inputBox.render();
  }
}

export class Button {
  constructor(private os: string) {}

  render(): void {
    if (this.os === "Windows") {
      console.log("Rendering a Windows button.");
    } else if (this.os === "Web") {
      console.log("Rendering an HTML button.");
    } else {
      throw new Error("Error! Unknown operating system.");
    }
  }

  onClick(f: () => void): void {
    if (this.os === "Windows") {
      console.log("Doing Windows button specific logic.");
    } else if (this.os === "Web") {
      console.log("Doing HTML button specific logic.");
    } else {
      throw new Error("Error! Unknown operating system.");
    }
    f();
  }
}

export class InputBox {
  constructor(private os: string) {}

  render(): void {
    if (this.os === "Windows") {
      console.log("Rendering a Windows input box.");
    } else if (this.os === "Web") {
      console.log("Rendering an HTML input box.");
    } else {
      throw new Error("Error! Unknown operating system.");
    }
  }

  onKeyStroke(f: () => void): void {
    if (this.os === "Windows") {
      console.log("Doing Windows input box specific logic.");
    } else if (this.os === "Web") {
      console.log("Doing HTML input box specific logic.");
    } else {
      throw new Error("Error! Unknown operating system.");
    }
    f();
  }
}
