import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IVentaTabla, IVenta } from 'app/shared/model/venta.model';
import { CrearEditarDialogComponent } from '../crear-editar-dialog/crear-editar-dialog.component';
import { VentaComponent } from '../venta.component';

@Component({
  selector: 'jhi-vista-venta',
  templateUrl: './vista-venta.component.html',
  styles: [],
  styleUrls: ['./vista-venta.component.scss']
})
export class VistaVentaComponent implements OnInit {
  @Input('ventas') venta: IVentaTabla;

  constructor(private modalService: NgbModal, private ventaComponent: VentaComponent) {}

  ngOnInit() {}

  open() {
    const modalRef = this.modalService.open(CrearEditarDialogComponent, { size: 'lg' });
    modalRef.componentInstance.venta = this.venta;
    modalRef.result.then(res => {
      if (res == 0) {
        this.ventaComponent.loadAll();
      }
    });
  }
}
