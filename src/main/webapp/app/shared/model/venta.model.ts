import { Moment } from 'moment';
import { IProducto } from 'app/shared/model/producto.model';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { ICarrito } from 'app/shared/model/carrito.model';

export interface IVenta {
  id?: number;
  fechaVenta?: Moment;
  precioVenta?: number;
  clienteId?: number;
  productos?: IProducto[];
  videoJuegos?: IVideoJuegos[];
  carritos?: ICarrito[];
}

export class Venta implements IVenta {
  constructor(
    public id?: number,
    public fechaVenta?: Moment,
    public precioVenta?: number,
    public clienteId?: number,
    public productos?: IProducto[],
    public videoJuegos?: IVideoJuegos[],
    public carritos?: ICarrito[]
  ) {}
}
