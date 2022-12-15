import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { ProductoService } from './producto.service';
import { IImagen } from 'app/shared/model/imagen.model';
import { IPlataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from 'app/entities/plataforma';
import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from 'app/entities/venta';

@Component({
  selector: 'jhi-producto-update',
  templateUrl: './producto-update.component.html'
})
export class ProductoUpdateComponent implements OnInit {
  producto: IProducto;
  isSaving: boolean;

  imagens: IImagen[];

  plataformas: IPlataforma[];

  ventas: IVenta[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    precio: [],
    stock: [],
    imagenId: [],
    plataformas: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected plataformaService: PlataformaService,
    protected ventaService: VentaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ producto }) => {
      this.updateForm(producto);
      this.producto = producto;
    });
    this.plataformaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPlataforma[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPlataforma[]>) => response.body)
      )
      .subscribe((res: IPlataforma[]) => (this.plataformas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ventaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVenta[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVenta[]>) => response.body)
      )
      .subscribe((res: IVenta[]) => (this.ventas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      stock: producto.stock,
      imagenId: producto.imagenId,
      plataformas: producto.plataformas
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id !== undefined) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    const entity = {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      precio: this.editForm.get(['precio']).value,
      stock: this.editForm.get(['stock']).value,
      imagenId: this.editForm.get(['imagenId']).value,
      plataformas: this.editForm.get(['plataformas']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackImagenById(index: number, item: IImagen) {
    return item.id;
  }

  trackPlataformaById(index: number, item: IPlataforma) {
    return item.id;
  }

  trackVentaById(index: number, item: IVenta) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
