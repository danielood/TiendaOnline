import { Component, Input, OnInit } from '@angular/core';
import { FileService } from 'app/core/file.service';
import { IJuegoTabla, IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { read } from 'fs';

@Component({
  selector: 'jhi-vista-tabla',
  templateUrl: './vista-tabla.component.html',
  styles: [],
  styleUrls: ['./vista-tabla.component.scss']
})
export class VistaTablaComponent implements OnInit {
  @Input('juego') juego: IJuegoTabla;
  portada: File;
  url;

  constructor(private fileService: FileService) {}

  ngOnInit() {
    //TODO
    //this.portada = this.fileService.ficheroToFile(this.juego.caratula);
    // const reader = new FileReader();
    // reader.onload = event => {
    //   this.url = event.target.result;
    //   reader.readAsDataURL(this.portada);
    // };
  }
}
