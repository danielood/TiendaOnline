import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IVentaTabla, IVenta } from 'app/shared/model/venta.model';
import { CrearEditarDialogComponent } from '../crear-editar-dialog/crear-editar-dialog.component';
import { VentaComponent } from '../venta.component';
import { VentaService } from '../venta.service';

@Component({
  selector: 'jhi-vista-venta',
  templateUrl: './vista-venta.component.html',
  styles: [],
  styleUrls: ['./vista-venta.component.scss']
})
export class VistaVentaComponent implements OnInit {
  @Input('ventas') venta: IVentaTabla;

  constructor(private modalService: NgbModal, private ventaComponent: VentaComponent, private ventaService: VentaService) {}

  ngOnInit() {}

  open() {
    this.ventaService.find(this.venta.id).subscribe((res: HttpResponse<IVenta>) => {
      const modal = this.modalService.open(CrearEditarDialogComponent, { size: 'lg', backdrop: 'static', keyboard: false });
      modal.componentInstance.venta = res.body;
      modal.result.then(res => {
        if (res == 0) {
          this.ventaComponent.loadAll();
        }
      });
    });
  }
}
