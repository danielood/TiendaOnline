export interface IFichero {
  fileName?: String;
  fileType?: String;
  fileBase64?: String;
}

export class Fichero implements IFichero {
  constructor(public fileName?: String, public fileContentType?: String, public fileBase64?: String) {}
}
