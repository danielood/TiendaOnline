import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IPlataforma {
  id?: number;
  nombre?: string;
  videoJuegos?: IVideoJuegos[];
  productos?: IProducto[];
}

export class Plataforma implements IPlataforma {
  constructor(public id?: number, public nombre?: string, public videoJuegos?: IVideoJuegos[], public productos?: IProducto[]) {}
}
