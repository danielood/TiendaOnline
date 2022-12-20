import { ICliente } from './cliente.model';

export interface IDireccion {
  id?: number;
  pais?: string;
  provincia?: string;
  ciudad?: string;
  calle?: string;
  portal?: string;
  escalera?: string;
  piso?: string;
  letra?: string;
  cliente?: ICliente;
}

export class Direccion implements IDireccion {
  constructor(
    public id?: number,
    public pais?: string,
    public provincia?: string,
    public ciudad?: string,
    public calle?: string,
    public portal?: string,
    public escalera?: string,
    public piso?: string,
    public letra?: string,
    public cliente?: ICliente
  ) {}
}
