export interface IImagen {
  id?: number;
  path?: string;
}

export class Imagen implements IImagen {
  constructor(public id?: number, public path?: string) {}
}
