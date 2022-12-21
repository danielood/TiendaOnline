import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FileService } from 'app/core/file.service';
import { IProducto } from 'app/shared/model/producto.model';
import { IProductoTabla } from 'app/shared/model/producto.model';
import { read } from 'fs';
import { CrearEditarDialogComponent } from '../crear-editar-dialog/crear-editar-dialog.component';
import { ProductoComponent } from '../producto.component';
import { ProductoService } from '../producto.service';

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

  constructor(
    private fileService: FileService,
    private productoService: ProductoService,
    private modalService: NgbModal,
    private productoComponent: ProductoComponent
  ) {}

  ngOnInit() {
    if (this.producto.imagen) {
      this.portada = this.fileService.ficheroToFile(this.producto.imagen);
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.url = event.target.result;
      };
      if (this.portada) {
        reader.readAsDataURL(this.portada);
      } else {
        this.url = '';
      }
    }
  }

  openEditModal() {
    this.productoService.find(this.producto.id).subscribe((res: HttpResponse<IProducto>) => {
      const modal = this.modalService.open(CrearEditarDialogComponent, { size: 'lg', backdrop: 'static', keyboard: false });
      modal.componentInstance.producto = res.body;
      modal.result.then(res => {
        if (res == 0) {
          this.productoComponent.loadAll();
        }
      });
    });
  }
}
