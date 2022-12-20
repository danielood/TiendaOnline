import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICliente } from 'app/shared/model/cliente.model';

import { IVenta } from 'app/shared/model/venta.model';
import { ClienteService } from '../cliente';

@Component({
  selector: 'jhi-venta-detail',
  templateUrl: './venta-detail.component.html'
})
export class VentaDetailComponent implements OnInit {
  venta: IVenta;

  subTotal: number = 0;
  total: number = 0;
  cliente: ICliente = {};

  constructor(protected activatedRoute: ActivatedRoute, protected clienteService: ClienteService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ venta }) => {
      this.venta = venta;

      this.clienteService.find(this.venta.cliente.id).subscribe(res => {
        this.cliente = res.body;
      });
    });

    this.venta.productos.forEach(a => {
      this.subTotal = this.subTotal + a.precio;
    });

    this.venta.videoJuegos.forEach(a => {
      this.subTotal = this.subTotal + a.precio;
    });

    var to = this.subTotal * 0.21;
    this.total = this.subTotal + to;

    console.log(this.venta);
  }

  previousState() {
    window.history.back();
  }
}
