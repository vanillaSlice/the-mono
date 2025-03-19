export class Database {

  private static instance: Database;

  private constructor() {
  }

  public static getInstance(): Database {
    if (Database.instance === null) {
      // in a multi-threaded language, you would acquire a lock here
      Database.instance = new Database();
    }
    return Database.instance;
  }

  public query(sql: string): any {
    console.log(`Executing query: ${sql}`);
    return {};
  }
}

export class Application {
  public main(): void {
    const databaseOne = Database.getInstance();
    databaseOne.query("SELECT ...");

    const databaseTwo = Database.getInstance();
    databaseTwo.query("SELECT ...");
  }
}
