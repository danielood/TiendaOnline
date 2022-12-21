import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FileService } from 'app/core/file.service';

import { IProducto } from 'app/shared/model/producto.model';
import { CrearEditarDialogComponent } from './crear-editar-dialog/crear-editar-dialog.component';
import { ProductoService } from './producto.service';

@Component({
  selector: 'jhi-producto-detail',
  templateUrl: './producto-detail.component.html',
  styleUrls: ['./producto-detail.scss']
})
export class ProductoDetailComponent implements OnInit {
  producto: IProducto;
  imagen;
  url;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private fileService: FileService,
    private productoService: ProductoService,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.producto = producto;
    });
    if (this.producto.imagen) {
      this.imagen = this.fileService.ficheroToFile(this.producto.imagen);
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.url = event.target.result;
      };
      if (this.imagen) {
        reader.readAsDataURL(this.imagen);
      } else {
        this.url = '';
      }
    }
  }

  previousState() {
    window.history.back();
  }

  openEditModal() {
    this.productoService.find(this.producto.id).subscribe((res: HttpResponse<IProducto>) => {
      const modal = this.modalService.open(CrearEditarDialogComponent, { size: 'lg', backdrop: 'static', keyboard: false });
      modal.componentInstance.producto = res.body;
      modal.result.then(res => {
        if (res == 0) {
          window.location.reload();
        }
      });
    });
  }
}
