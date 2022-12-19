import { Injectable } from '@angular/core';
import { Fichero } from './fichero.model';

@Injectable({ providedIn: 'root' })
export class FileService {
  ficheroToFile(fichero: Fichero): File {
    const dataUrl = 'data:' + fichero.fileType + ';base64,' + fichero.fileBase64;
    return this.dataURLtoFile(dataUrl, fichero.fileName);
  }

  fileToFichero(file: File) {
    let fichero: Fichero = new Fichero();
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      fichero.fileName = file.name;
      fichero.fileType = file.type;
      fichero.fileBase64 = reader.result as string;
      return fichero;
    };
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

  private dataURLtoFile(dataurl, filename): File {
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
}
