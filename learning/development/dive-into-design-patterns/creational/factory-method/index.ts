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
  }

  abstract createButton(): Button;
}

export class WindowsDialog extends Dialog {
  createButton(): Button {
    return new WindowsButton();
  }
}

export class WebDialog extends Dialog {
  createButton(): Button {
    return new HTMLButton();
  }
}

export class MacDialog extends Dialog {
  createButton(): Button {
    return new MacButton();
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

