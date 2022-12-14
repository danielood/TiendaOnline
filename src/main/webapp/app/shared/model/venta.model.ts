import { Moment } from 'moment';
import { IProducto } from 'app/shared/model/producto.model';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { IDireccion } from './direccion.model';
import { ICliente } from './cliente.model';

export interface IVentaTabla {
  id?: number;
  fechaVenta?: Moment;
  precioVenta?: number;
  clienteId?: number;
}

export class VentaTabla implements IVentaTabla {
  constructor(public id?: number, public fechaVenta?: Moment, public precioVenta?: number, public clienteId?: number) {}
}

export interface IVenta {
  id?: number;
  fechaVenta?: Moment;
  precioVenta?: number;
  cliente?: ICliente;
  productos?: IProducto[];
  videoJuegos?: IVideoJuegos[];
  direccion?: IDireccion;
}

export class Venta implements IVenta {
  constructor(
    public id?: number,
    public fechaVenta?: Moment,
    public precioVenta?: number,
    public cliente?: ICliente,
    public productos?: IProducto[],
    public videoJuegos?: IVideoJuegos[],
    public direccion?: IDireccion
  ) {}
}
