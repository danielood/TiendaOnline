export interface IFichero {
  fileName?: string;
  fileType?: string;
  fileBase64?: string;
}

export class Fichero implements IFichero {
  constructor(public fileName?: string, public fileType?: string, public fileBase64?: string) {}
}
