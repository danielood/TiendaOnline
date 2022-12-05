export interface ICliente {
  id?: number;
  email?: string;
}

export class Cliente implements ICliente {
  constructor(public id?: number, public email?: string) {}
}
