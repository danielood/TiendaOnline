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
import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from 'app/entities/venta';

@Component({
  selector: 'jhi-carrito-update',
  templateUrl: './carrito-update.component.html'
})
export class CarritoUpdateComponent implements OnInit {
  carrito: ICarrito;
  isSaving: boolean;

  clientes: ICliente[];

  ventas: IVenta[];

  editForm = this.fb.group({
    id: [],
    clienteId: [],
    ventas: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected carritoService: CarritoService,
    protected clienteService: ClienteService,
    protected ventaService: VentaService,
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
    this.ventaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVenta[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVenta[]>) => response.body)
      )
      .subscribe((res: IVenta[]) => (this.ventas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(carrito: ICarrito) {
    this.editForm.patchValue({
      id: carrito.id,
      clienteId: carrito.clienteId,
      ventas: carrito.ventas
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
      ventas: this.editForm.get(['ventas']).value
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
