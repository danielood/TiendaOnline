import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDireccion, Direccion } from 'app/shared/model/direccion.model';
import { DireccionService } from './direccion.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';

@Component({
  selector: 'jhi-direccion-update',
  templateUrl: './direccion-update.component.html'
})
export class DireccionUpdateComponent implements OnInit {
  direccion: IDireccion;
  isSaving: boolean;

  clientes: ICliente[];

  editForm = this.fb.group({
    id: [],
    pais: [],
    provincia: [],
    ciudad: [],
    calle: [],
    portal: [],
    escalera: [],
    piso: [],
    letra: [],
    clienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected direccionService: DireccionService,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ direccion }) => {
      this.updateForm(direccion);
      this.direccion = direccion;
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(direccion: IDireccion) {
    this.editForm.patchValue({
      id: direccion.id,
      pais: direccion.pais,
      provincia: direccion.provincia,
      ciudad: direccion.ciudad,
      calle: direccion.calle,
      portal: direccion.portal,
      escalera: direccion.escalera,
      piso: direccion.piso,
      letra: direccion.letra,
      clienteId: direccion.clienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const direccion = this.createFromForm();
    if (direccion.id !== undefined) {
      this.subscribeToSaveResponse(this.direccionService.update(direccion));
    } else {
      this.subscribeToSaveResponse(this.direccionService.create(direccion));
    }
  }

  private createFromForm(): IDireccion {
    const entity = {
      ...new Direccion(),
      id: this.editForm.get(['id']).value,
      pais: this.editForm.get(['pais']).value,
      provincia: this.editForm.get(['provincia']).value,
      ciudad: this.editForm.get(['ciudad']).value,
      calle: this.editForm.get(['calle']).value,
      portal: this.editForm.get(['portal']).value,
      escalera: this.editForm.get(['escalera']).value,
      piso: this.editForm.get(['piso']).value,
      letra: this.editForm.get(['letra']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDireccion>>) {
    result.subscribe((res: HttpResponse<IDireccion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
