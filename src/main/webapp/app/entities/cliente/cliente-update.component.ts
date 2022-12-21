import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { VentaService } from '../venta';
import { Direccion, IDireccion } from 'app/shared/model/direccion.model';
import { AddDireccionModalComponent } from './add-direccion-modal/add-direccion-modal.component';

@Component({
  selector: 'jhi-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  @Input() cliente: ICliente;
  isSaving: boolean;
  direcciones: IDireccion[] = [];
  direccion: IDireccion = {};

  editForm = this.fb.group({
    id: [],
    email: [],
    dni: [],
    nombre: [],
    apellido: [],
    telefono: []
  });

  constructor(
    private modalService: NgbModal,
    protected ventasService: VentaService,
    public activeModal: NgbActiveModal,
    protected clienteService: ClienteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  addDire(i: number) {
    const modalRef = this.modalService.open(AddDireccionModalComponent, { size: 'lg' });

    modalRef.result.then(res => {
      if (res) {
        this.direcciones.push(res);
      }
    });
  }

  editDire(i: number) {
    const modalRef = this.modalService.open(AddDireccionModalComponent, { size: 'lg' });
    modalRef.componentInstance.direccion = this.direcciones[i];
    modalRef.result.then(res => {
      if (res) {
        this.direcciones.splice(i, 1);
        this.direcciones.push(res);
      }
    });
  }

  deleteDire(i: number) {
    //this.ventasService.deleteDireccionById(this.direcciones[i].id).subscribe(res => console.log(res))
    this.direcciones.splice(i, 1);
  }

  ngOnInit() {
    this.isSaving = false;
    // this.activatedRoute.data.subscribe(({ cliente }) => {
    //   this.updateForm(cliente);
    //   this.cliente = cliente;
    // });
    this.ventasService.findDireccionesByClientId(this.cliente.id).subscribe(res => (this.direcciones = res.body));

    this.updateForm(this.cliente);
  }

  updateForm(cliente: ICliente) {
    this.editForm.patchValue({
      id: cliente.id,
      email: cliente.email,
      dni: cliente.dni,
      nombre: cliente.nombre,
      apellido: cliente.apellido,
      telefono: cliente.telefono
    });
  }

  previousState() {
    this.activeModal.close(0);
  }

  save() {
    this.isSaving = true;
    const cliente = this.createFromForm();
    cliente.direcciones = this.direcciones;
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    const entity = {
      ...new Cliente(),
      id: this.editForm.get(['id']).value,
      email: this.editForm.get(['email']).value,
      dni: this.editForm.get(['dni']).value,
      nombre: this.editForm.get(['nombre']).value,
      apellido: this.editForm.get(['apellido']).value,
      telefono: this.editForm.get(['telefono']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
    result.subscribe((res: HttpResponse<ICliente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.activeModal.close(0);
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
