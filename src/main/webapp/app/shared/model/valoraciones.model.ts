import { IVideoJuegos } from 'app/shared/model/video-juegos.model';

export interface IValoraciones {
  id?: number;
  puntuacion?: number;
  comentario?: string;
  clienteId?: number;
  videoJuegos?: IVideoJuegos[];
}

export class Valoraciones implements IValoraciones {
  constructor(
    public id?: number,
    public puntuacion?: number,
    public comentario?: string,
    public clienteId?: number,
    public videoJuegos?: IVideoJuegos[]
  ) {}
}
