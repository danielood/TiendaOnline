import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { VentaService } from 'app/entities/venta';
import { Direccion, IDireccion } from 'app/shared/model/direccion.model';

@Component({
  selector: 'jhi-add-direccion-modal',
  templateUrl: './add-direccion-modal.component.html',
  styles: []
})
export class AddDireccionModalComponent implements OnInit {
  @Input() direccion: IDireccion = {};
  editado: number = 0;

  nuevaDire = this.fb.group({
    id: [],
    pais: [],
    provincia: [],
    ciudad: [],
    calle: [],
    portal: [],
    escalera: [],
    piso: [],
    letra: [],
    cliente: []
  });

  constructor(private fb: FormBuilder, protected ventaService: VentaService, public activeModal: NgbActiveModal) {}

  ngOnInit() {
    this.updateForm(this.direccion);
  }

  save() {
    this.activeModal.close(this.createFromForm());
  }

  previousState() {
    this.activeModal.dismiss();
  }
  updateForm(direccion: IDireccion) {
    this.nuevaDire.patchValue({
      id: direccion.id,
      pais: direccion.pais,
      provincia: direccion.provincia,
      ciudad: direccion.ciudad,
      calle: direccion.calle,
      portal: direccion.portal,
      escalera: direccion.escalera,
      piso: direccion.piso,
      letra: direccion.letra,
      cliente: direccion.cliente
    });
  }

  private createFromForm(): IDireccion {
    const entity = {
      ...new Direccion(),
      id: this.nuevaDire.get(['id']).value,
      pais: this.nuevaDire.get(['pais']).value,
      provincia: this.nuevaDire.get(['provincia']).value,
      ciudad: this.nuevaDire.get(['ciudad']).value,
      calle: this.nuevaDire.get(['calle']).value,
      portal: this.nuevaDire.get(['portal']).value,
      escalera: this.nuevaDire.get(['escalera']).value,
      piso: this.nuevaDire.get(['piso']).value,
      letra: this.nuevaDire.get(['letra']).value
    };
    return entity;
  }
}
