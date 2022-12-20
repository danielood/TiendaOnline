import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICliente } from 'app/shared/model/cliente.model';
import jsPDF from 'jspdf';

import * as _html2canvas from 'html2canvas';
const html2canvas: any = _html2canvas;

import { IVenta } from 'app/shared/model/venta.model';
import { ClienteService } from '../cliente';

@Component({
  selector: 'jhi-venta-detail',
  templateUrl: './venta-detail.component.html'
})
export class VentaDetailComponent implements OnInit {
  venta: IVenta;
  @ViewChild('html-data') htmlData!: ElementRef;

  subTotal: number = 0;
  total: number = 0;
  cliente: ICliente = {};

  constructor(protected activatedRoute: ActivatedRoute, protected clienteService: ClienteService) {}

  downloadPdf() {
    let DATA: any = document.getElementById('html-data');
    html2canvas(DATA).then(canvas => {
      let fileWidth = 208;
      let fileHeight = (canvas.height * fileWidth) / canvas.width;
      const FILEURI = canvas.toDataURL('image/png');
      let PDF = new jsPDF('p', 'mm', 'a4');
      let position = 10;
      let posIzq = 10;
      PDF.addImage(FILEURI, 'PNG', posIzq, position, fileWidth, fileHeight);
      PDF.save('Factura.pdf');
    });
  }

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
