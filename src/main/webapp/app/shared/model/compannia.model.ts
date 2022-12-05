export interface ICompannia {
  id?: number;
  nombre?: string;
}

export class Compannia implements ICompannia {
  constructor(public id?: number, public nombre?: string) {}
}
