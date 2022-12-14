import { Component, Input, OnInit } from '@angular/core';
import { IVentaTabla, IVenta } from 'app/shared/model/venta.model';

@Component({
  selector: 'jhi-vista-venta',
  templateUrl: './vista-venta.component.html',
  styles: [],
  styleUrls: ['./vista-venta.component.scss']
})
export class VistaVentaComponent implements OnInit {
  // tslint:disable-next-line:no-input-rename
  @Input('ventas') venta: IVentaTabla;
  ngOnInit() {
    console.log('loaded');
  }
}