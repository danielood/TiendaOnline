import { IPlataforma } from 'app/shared/model/plataforma.model';
import { IVenta } from 'app/shared/model/venta.model';
export interface IProductoTabla {
  id?: number;
  nombre?: string;
  precio?: number;
  stock?: number;
  imagenId?: number;
  plataformas?: IPlataforma[];
}

export class ProductoTabla implements IProductoTabla {
  constructor(
    public id?: number,
    public nombre?: string,
    public precio?: number,
    public stock?: number,
    public imagenId?: number,
    public plataformas?: IPlataforma[]
  ) {}
}
export interface IProducto {
  id?: number;
  nombre?: string;
  descripcion?: string;
  precio?: number;
  stock?: number;
  imagenId?: number;
  plataformas?: IPlataforma[];
  ventas?: IVenta[];
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
    public ventas?: IVenta[]
  ) {}
}
