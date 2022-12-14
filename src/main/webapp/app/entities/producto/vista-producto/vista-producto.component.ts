import { Component, Input, OnInit } from '@angular/core';
import { FileService } from 'app/core/file.service';
import { IProducto } from 'app/shared/model/producto.model';
import { IProductoTabla } from 'app/shared/model/producto.model';
import { read } from 'fs';

@Component({
  selector: 'jhi-vista-producto',
  templateUrl: './vista-producto.component.html',
  styles: [],
  styleUrls: ['./vista-producto.component.scss']
})
export class VistaProductoComponent implements OnInit {
  @Input('producto') producto: IProductoTabla;
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
