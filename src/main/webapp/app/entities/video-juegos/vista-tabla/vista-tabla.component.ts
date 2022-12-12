import { Component, Input, OnInit } from '@angular/core';
import { FileService } from 'app/core/file.service';
import { IJuegoTabla, IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { read } from 'fs';

@Component({
  selector: 'jhi-vista-tabla',
  templateUrl: './vista-tabla.component.html',
  styles: []
})
export class VistaTablaComponent implements OnInit {
  @Input() juego: IJuegoTabla;
  portada: File;
  url;

  constructor(private fileService: FileService) {
    this.portada = this.fileService.ficheroToFile(this.juego.caratula);
  }

  ngOnInit() {
    var reader = new FileReader();
    reader.onload = event => {
      this.url = event.target.result;
      reader.readAsDataURL(this.portada);
    };
  }
}
