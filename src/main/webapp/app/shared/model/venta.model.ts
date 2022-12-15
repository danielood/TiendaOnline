import { Moment } from 'moment';
import { IProducto } from 'app/shared/model/producto.model';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';

export interface IVenta {
  id?: number;
  fechaVenta?: Moment;
  precioVenta?: number;
  clienteId?: number;
  productos?: IProducto[];
  videoJuegos?: IVideoJuegos[];
}

export class Venta implements IVenta {
  constructor(
    public id?: number,
    public fechaVenta?: Moment,
    public precioVenta?: number,
    public clienteId?: number,
    public productos?: IProducto[],
    public videoJuegos?: IVideoJuegos[]
  ) {}
}
