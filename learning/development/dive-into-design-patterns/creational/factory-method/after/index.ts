export class Application {

  private dialog: Dialog;

  constructor(private os: string) {}

  initialise(): void {
    if (this.os === "Windows") {
      this.dialog = new WindowsDialog();
    } else if (this.os === "Web") {
      this.dialog = new WebDialog();
    } else if (this.os === "Mac") {
      this.dialog = new MacDialog();
    } else {
      throw new Error("Error! Unknown operating system.");
    }
  }

  main(): void {
    this.initialise();
    this.dialog.render();
  }
}

export abstract class Dialog {
  render(): void {
    const okButton = this.createButton();
    okButton.onClick(() => console.log("Closing dialog."));
    okButton.render();

    const inputBox = this.createInputBox();
    inputBox.onKeyStroke(() => console.log("Key hit."));
    inputBox.render();
  }

  abstract createButton(): Button;
  abstract createInputBox(): InputBox;
}

export class WindowsDialog extends Dialog {
  createButton(): Button {
    return new WindowsButton();
  }

  createInputBox(): InputBox {
    return new WindowsInputBox();
  }
}

export class WebDialog extends Dialog {
  createButton(): Button {
    return new HTMLButton();
  }

  createInputBox(): InputBox {
    return new HTMLInputBox();
  }
}

export class MacDialog extends Dialog {
  createButton(): Button {
    return new MacButton();
  }

  createInputBox(): InputBox {
    return new MacInputBox();
  }
}

export interface Button {
  render(): void;
  onClick(f: () => void): void;
}

export class WindowsButton implements Button {
  render(): void {
    console.log("Rendering a Windows button.");
  }

  onClick(f: () => void): void {
    console.log("Doing Windows button specific logic.");
    f();
  }
}

export class HTMLButton implements Button {
  render(): void {
    console.log("Rendering an HTML button.");
  }

  onClick(f: () => void): void {
    console.log("Doing HTML button specific logic.");
    f();
  }
}

export class MacButton implements Button {
  render(): void {
    console.log("Rendering a Mac button.");
  }

  onClick(f: () => void): void {
    console.log("Doing Mac button specific logic.");
    f();
  }
}

export interface InputBox {
  render(): void;
  onKeyStroke(f: () => void): void;
}

export class WindowsInputBox implements InputBox {
  render(): void {
    console.log("Rendering a Windows input box.");
  }

  onKeyStroke(f: () => void): void {
    console.log("Doing Windows input box specific logic.");
    f();
  }
}

export class HTMLInputBox implements InputBox {
  render(): void {
    console.log("Rendering an HTML input box.");
  }

  onKeyStroke(f: () => void): void {
    console.log("Doing HTML input box specific logic.");
    f();
  }
}

export class MacInputBox implements InputBox {
  render(): void {
    console.log("Rendering a Mac input box.");
  }

  onKeyStroke(f: () => void): void {
    console.log("Doing Mac input box specific logic.");
    f();
  }
}
