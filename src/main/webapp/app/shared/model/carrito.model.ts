import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface ICarrito {
  id?: number;
  clienteId?: number;
  videoJuegos?: IVideoJuegos[];
  productos?: IProducto[];
}

export class Carrito implements ICarrito {
  constructor(public id?: number, public clienteId?: number, public videoJuegos?: IVideoJuegos[], public productos?: IProducto[]) {}
}
