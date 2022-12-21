import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { toInteger } from '@ng-bootstrap/ng-bootstrap/util/util';
import { ClienteService } from 'app/entities/cliente';
import { ProductoService } from 'app/entities/producto';
import { VideoJuegosService } from 'app/entities/video-juegos';
import { ICliente } from 'app/shared/model/cliente.model';
import { IDireccion } from 'app/shared/model/direccion.model';
import { IProducto } from 'app/shared/model/producto.model';
import { IVenta, Venta } from 'app/shared/model/venta.model';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { JhiAlertService } from 'ng-jhipster';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VentaService } from '../venta.service';

@Component({
  selector: 'jhi-crear-editar-dialog',
  templateUrl: './crear-editar-dialog.component.html',
  styles: []
})
export class CrearEditarDialogComponent implements OnInit {
  @Input() venta: IVenta;

  //venta: IVenta;
  isSaving: boolean;
  clientes: ICliente[];
  productos: IProducto[];
  videojuegos: IVideoJuegos[];
  fechaVentaDp: any;

  total: number = 0;

  selectedProd: string;
  selectedProdObj: IProducto;
  listProd: Array<IProducto>;

  selectedVid: string;
  selectedVidObj: IVideoJuegos;
  listVid: Array<IVideoJuegos>;

  selectedClient: ICliente = {};
  dire: IDireccion[] = [];
  selectedDire: IDireccion = {};
  selectVisible: boolean = false;

  editForm = this.fb.group({
    id: [],
    fechaVenta: [],
    precioVenta: [],
    clienteId: [],
    productos: [],
    videoJuegos: [],
    direccion: []
  });
  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ventaService: VentaService,
    protected clienteService: ClienteService,
    protected productoService: ProductoService,
    protected videoJuegosService: VideoJuegosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal
  ) {}

  deleteProd(index: number) {
    this.total = this.total - this.listProd[index].precio;
    this.listProd.splice(index, 1);
  }

  deleteVid(index: number) {
    this.total = this.total - this.listVid[index].precio;
    this.listVid.splice(index, 1);
  }

  onSelectProd() {
    this.selectedProdObj = this.productos.find(p => p.nombre == this.selectedProd);

    if (this.selectedProdObj) {
      this.listProd.push(this.selectedProdObj);
      this.total = this.total + this.selectedProdObj.precio;
      this.selectedProd = '';
    } else {
      alert('El producto no existe');
    }
  }

  ons(event) {
    console.log(event);

    this.ventaService.findDireccionesByClientId(parseInt(event)).subscribe(res => (this.dire = res.body));
    this.selectVisible = true;
  }

  onChange(event) {
    console.log(event);
  }

  onSelectVid() {
    this.selectedVidObj = this.videojuegos.find(p => p.titulo == this.selectedVid);
    if (this.selectedVidObj) {
      this.listVid.push(this.selectedVidObj);
      this.total = this.total + this.selectedVidObj.precio;
    } else {
      alert('El videojuego no existe');
    }
  }

  ngOnInit() {
    this.isSaving = false;
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.productoService.findAll().subscribe(res => (this.productos = res.body));
    this.videoJuegosService.findAll().subscribe(res => (this.videojuegos = res.body));

    this.selectedProd = '';
    this.selectedProdObj = {};

    this.selectedVid = '';
    this.selectedVidObj = {};

    if (!this.venta) {
      this.venta = new Venta();
    } else {
      this.updateForm(this.venta);
    }
    if (this.venta.productos) {
      this.listProd = this.venta.productos;
    } else {
      this.listProd = new Array<IProducto>();
    }
    if (this.venta.videoJuegos) {
      this.listVid = this.venta.videoJuegos;
    } else {
      this.listVid = new Array<IVideoJuegos>();
    }
  }

  updateForm(venta: IVenta) {
    this.editForm.patchValue({
      id: venta.id,
      fechaVenta: venta.fechaVenta,
      precioVenta: venta.precioVenta,
      clienteId: venta.cliente,
      productos: venta.productos,
      videoJuegos: venta.videoJuegos,
      direccion: venta.direccion
    });
  }

  previousState() {
    this.activeModal.close(0);
  }

  save() {
    this.isSaving = true;
    const venta = this.createFromForm();
    if (venta.id != undefined || venta.id != null) {
      this.subscribeToSaveResponse(this.ventaService.update(venta));
    } else {
      this.subscribeToSaveResponse(this.ventaService.create(venta));
    }
  }

  private createFromForm(): IVenta {
    const entity = {
      ...new Venta(),
      id: this.editForm.get(['id']).value,
      fechaVenta: this.editForm.get(['fechaVenta']).value,
      precioVenta: this.total,
      cliente: this.editForm.get(['clienteId']).value,
      productos: this.listProd,
      videoJuegos: this.listVid,
      direccion: this.editForm.get(['direccion']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>) {
    result.subscribe((res: HttpResponse<IVenta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProductoById(index: number, item: IProducto) {
    return item.id;
  }

  trackVideoJuegosById(index: number, item: IVideoJuegos) {
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

  clear() {
    this.activeModal.dismiss();
  }
}
