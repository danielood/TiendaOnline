import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IVenta, Venta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosService } from 'app/entities/video-juegos';

@Component({
  selector: 'jhi-venta-update',
  templateUrl: './venta-update.component.html'
})
export class VentaUpdateComponent implements OnInit {
  venta: IVenta;
  isSaving: boolean;
  clientes: ICliente[];
  productos: IProducto[];
  videojuegos: IVideoJuegos[];
  fechaVentaDp: any;

  editForm = this.fb.group({
    id: [],
    fechaVenta: [],
    precioVenta: [],
    clienteId: [],
    productos: [],
    videoJuegos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ventaService: VentaService,
    protected clienteService: ClienteService,
    protected productoService: ProductoService,
    protected videoJuegosService: VideoJuegosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ venta }) => {
      this.updateForm(venta);
      this.venta = venta;
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProducto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProducto[]>) => response.body)
      )
      .subscribe((res: IProducto[]) => (this.productos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.videoJuegosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVideoJuegos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVideoJuegos[]>) => response.body)
      )
      .subscribe((res: IVideoJuegos[]) => (this.videojuegos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(venta: IVenta) {
    this.editForm.patchValue({
      id: venta.id,
      fechaVenta: venta.fechaVenta,
      precioVenta: venta.precioVenta,
      clienteId: venta.clienteId,
      productos: venta.productos,
      videoJuegos: venta.videoJuegos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const venta = this.createFromForm();
    if (venta.id !== undefined) {
      this.subscribeToSaveResponse(this.ventaService.update(venta));
    } else {
      this.subscribeToSaveResponse(this.ventaService.create(venta));
    }
  }

  private createFromForm(): IVenta {
    const entity = {
      ...new Venta(),
      id: this.editForm.get(['id']).value,
      fechaVenta: this.editForm.get(['fechaVenta']).value,
      precioVenta: this.editForm.get(['precioVenta']).value,
      clienteId: this.editForm.get(['clienteId']).value,
      productos: this.editForm.get(['productos']).value,
      videoJuegos: this.editForm.get(['videoJuegos']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>) {
    result.subscribe((res: HttpResponse<IVenta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackVideoJuegosById(index: number, item: IVideoJuegos) {
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
