import Dexie from 'dexie';

export interface Token {
  key: string;   
  value: string; 
}

export class TaskManagerDB extends Dexie {
  tokens!: Dexie.Table<Token, string>;

  constructor() {
    super('TaskManagerDB');
    this.version(1).stores({
      tokens: '&key, value',   
    });
  }
}

export const db = new TaskManagerDB();
export default db;