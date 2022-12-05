import { IVideoJuegos } from 'app/shared/model/video-juegos.model';

export interface ICategoria {
  id?: number;
  nombre?: string;
  videoJuegos?: IVideoJuegos[];
}

export class Categoria implements ICategoria {
  constructor(public id?: number, public nombre?: string, public videoJuegos?: IVideoJuegos[]) {}
}
