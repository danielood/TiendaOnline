import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IVenta } from 'app/shared/model/venta.model';

@Component({
  selector: 'jhi-venta-detail',
  templateUrl: './venta-detail.component.html'
})
export class VentaDetailComponent implements OnInit {
  venta: IVenta;

  subTotal: number = 0;
  total: number = 0;

  constructor(protected activatedRoute: ActivatedRoute) {}

  downloadPdf() {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ venta }) => {
      this.venta = venta;
    });

    this.venta.productos.forEach(a => {
      this.subTotal = this.subTotal + a.precio;
    });

    this.venta.videoJuegos.forEach(a => {
      this.subTotal = this.subTotal + a.precio;
    });

    var to = this.subTotal * 0.21;
    this.total = this.subTotal + to;
  }

  previousState() {
    window.history.back();
  }
}
