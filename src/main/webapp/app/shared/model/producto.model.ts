import { IPlataforma } from 'app/shared/model/plataforma.model';
import { IVenta } from 'app/shared/model/venta.model';
import { ICarrito } from 'app/shared/model/carrito.model';

export interface IProducto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  precio?: number;
  stock?: number;
  imagenId?: number;
  plataformas?: IPlataforma[];
  ventas?: IVenta[];
  carritos?: ICarrito[];
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string,
    public descripcion?: string,
    public precio?: number,
    public stock?: number,
    public imagenId?: number,
    public plataformas?: IPlataforma[],
    public ventas?: IVenta[],
    public carritos?: ICarrito[]
  ) {}
}
