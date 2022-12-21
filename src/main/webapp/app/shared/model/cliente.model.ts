import { IDireccion } from './direccion.model';

export interface ICliente {
  id?: number;
  email?: string;
  dni?: string;
  nombre?: string;
  apellido?: string;
  telefono?: string;
  direcciones?: IDireccion[];
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public email?: string,
    public dni?: string,
    public nombre?: string,
    public apellido?: string,
    public telefono?: string
  ) {}
}
