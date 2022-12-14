import { Moment } from 'moment';
import { IValoraciones } from 'app/shared/model/valoraciones.model';
import { IPlataforma } from 'app/shared/model/plataforma.model';
import { ICategoria } from 'app/shared/model/categoria.model';
import { IVenta } from 'app/shared/model/venta.model';
import { Fichero } from 'app/core/fichero.model';
import { ICompannia } from './compannia.model';

export const enum Pegi {
  PEGI3 = 'PEGI3',
  PEGI7 = 'PEGI7',
  PEGI12 = 'PEGI12',
  PEGI16 = 'PEGI16',
  PEGI18 = 'PEGI18'
}

export interface IJuegoTabla {
  id?: number;
  titulo?: string;
  pegi?: Pegi;
  precio?: number;
  stock?: number;
  caratula?: Fichero;
  compannia?: string;
  plataformas?: string[];
}

export class JuegoTabla implements IJuegoTabla {
  constructor(
    public id?: number,
    public titulo?: string,
    public pegi?: Pegi,
    public precio?: number,
    public stock?: number,
    public caratula?: Fichero,
    public compannia?: string,
    public plataformas?: string[]
  ) {}
}

export interface IVideoJuegos {
  id?: number;
  titulo?: string;
  sinopsis?: string;
  pegi?: Pegi;
  fechaLanzamiento?: Moment;
  precio?: number;
  stock?: number;
  destacado?: boolean;
  caratula?: Fichero;
  compannia?: ICompannia;
  plataformas?: IPlataforma[];
  categorias?: ICategoria[];
  valoracion?: number;
}

export class VideoJuegos implements IVideoJuegos {
  constructor(
    public id?: number,
    public titulo?: string,
    public sinopsis?: string,
    public pegi?: Pegi,
    public fechaLanzamiento?: Moment,
    public precio?: number,
    public stock?: number,
    public destacado?: boolean,
    public caratula?: Fichero,
    public compannia?: ICompannia,
    public plataformas?: IPlataforma[],
    public categorias?: ICategoria[],
    public valoracion?: number
  ) {
    this.destacado = this.destacado || false;
    this.valoracion = this.valoracion || 0;
  }
}
