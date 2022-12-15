import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ImagenService } from 'app/entities/imagen';
import { PlataformaService } from 'app/entities/plataforma';
import { VentaService } from 'app/entities/venta';
import { IImagen } from 'app/shared/model/imagen.model';
import { IPlataforma } from 'app/shared/model/plataforma.model';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { IVenta } from 'app/shared/model/venta.model';
import { JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoService } from '../producto.service';

@Component({
  selector: 'jhi-crear-editar-dialog',
  templateUrl: './crear-editar-dialog.component.html',
  styles: []
})
export class CrearEditarDialogComponent implements OnInit {
  @Input() producto: IProducto;
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
    private fb: FormBuilder,
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected imagenService: ImagenService,
    protected plataformaService: PlataformaService,
    protected ventaService: VentaService,
    protected activatedRoute: ActivatedRoute,
    public activeModal: NgbActiveModal
  ) {}
  ngOnInit(): void {
    this.isSaving = false;
    this.updateForm(this.producto);

    console.log(this.producto);
    // this.activatedRoute.data.subscribe(({ producto }) => {
    //   this.updateForm(producto);
    //   this.producto = producto;

    // });
    this.imagenService
      .query({ filter: 'producto-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IImagen[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImagen[]>) => response.body)
      )
      .subscribe(
        (res: IImagen[]) => {
          if (!this.producto.imagenId) {
            this.imagens = res;
          } else {
            this.imagenService
              .find(this.producto.imagenId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IImagen>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IImagen>) => subResponse.body)
              )
              .subscribe(
                (subRes: IImagen) => (this.imagens = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
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

  clear() {
    this.activeModal.dismiss('cancel');
    window.location.reload();
  }
}
