import { IVenta } from 'app/shared/model/venta.model';

export interface ICarrito {
  id?: number;
  clienteId?: number;
  ventas?: IVenta[];
}

export class Carrito implements ICarrito {
  constructor(public id?: number, public clienteId?: number, public ventas?: IVenta[]) {}
}
