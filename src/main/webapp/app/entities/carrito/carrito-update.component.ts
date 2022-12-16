import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICarrito, Carrito } from 'app/shared/model/carrito.model';
import { CarritoService } from './carrito.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosService } from 'app/entities/video-juegos';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';

@Component({
  selector: 'jhi-carrito-update',
  templateUrl: './carrito-update.component.html'
})
export class CarritoUpdateComponent implements OnInit {
  carrito: ICarrito;
  isSaving: boolean;

  clientes: ICliente[];

  videojuegos: IVideoJuegos[];

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    clienteId: [],
    videoJuegos: [],
    productos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carritoService: CarritoService,
    protected clienteService: ClienteService,
    protected videoJuegosService: VideoJuegosService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ carrito }) => {
      this.updateForm(carrito);
      this.carrito = carrito;
    });
    this.clienteService
      .query({ filter: 'carrito-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe(
        (res: ICliente[]) => {
          if (!this.carrito.clienteId) {
            this.clientes = res;
          } else {
            this.clienteService
              .find(this.carrito.clienteId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICliente>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICliente>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICliente) => (this.clientes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.videoJuegosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVideoJuegos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVideoJuegos[]>) => response.body)
      )
      .subscribe((res: IVideoJuegos[]) => (this.videojuegos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(carrito: ICarrito) {
    this.editForm.patchValue({
      id: carrito.id,
      clienteId: carrito.clienteId,
      videoJuegos: carrito.videoJuegos,
      productos: carrito.productos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const carrito = this.createFromForm();
    if (carrito.id !== undefined) {
      this.subscribeToSaveResponse(this.carritoService.update(carrito));
    } else {
      this.subscribeToSaveResponse(this.carritoService.create(carrito));
    }
  }

  private createFromForm(): ICarrito {
    const entity = {
      ...new Carrito(),
      id: this.editForm.get(['id']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      videoJuegos: this.editForm.get(['videoJuegos']).value,
      productos: this.editForm.get(['productos']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarrito>>) {
    result.subscribe((res: HttpResponse<ICarrito>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackClienteById(index: number, item: ICliente) {
    return item.id;
  }

  trackVideoJuegosById(index: number, item: IVideoJuegos) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
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
