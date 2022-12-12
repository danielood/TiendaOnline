import { Injectable } from '@angular/core';
import { Fichero } from './fichero.model';

@Injectable({ providedIn: 'root' })
export class FileService {
  ficheroToFile(fichero: Fichero): File {
    const dataUrl = 'data:' + fichero.fileContentType + ';base64,' + fichero.fileBase64;
    return this.dataURLtoFile(dataUrl, 'Portada.png');
  }

  fileToFichero(file: File) {}

  dataURLtoFile(dataurl, filename): File {
    let arr = dataurl.split(','),
      mime = arr[0].match(/:(.*?);/)[1],
      bstr = atob(arr[1]),
      n = bstr.length,
      u8arr = new Uint8Array(n);

    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
  }

  downloadFile(file: File, fileName: string): void {
    let url = window.URL.createObjectURL(file);
    let a = document.createElement('a');
    document.body.appendChild(a);
    a.setAttribute('style', 'display: none');
    a.href = url;
    a.download = fileName;
    a.click();
    window.URL.revokeObjectURL(url);
    a.remove();
  }
}
