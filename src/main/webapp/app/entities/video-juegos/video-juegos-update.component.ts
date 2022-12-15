import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IVideoJuegos, VideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosService } from './video-juegos.service';
import { IImagen } from 'app/shared/model/imagen.model';
import { ImagenService } from 'app/entities/imagen';
import { ICompannia } from 'app/shared/model/compannia.model';
import { CompanniaService } from 'app/entities/compannia';
import { IValoraciones } from 'app/shared/model/valoraciones.model';
import { ValoracionesService } from 'app/entities/valoraciones';
import { IPlataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from 'app/entities/plataforma';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from 'app/entities/venta';
import { ICarrito } from 'app/shared/model/carrito.model';
import { CarritoService } from 'app/entities/carrito';

@Component({
  selector: 'jhi-video-juegos-update',
  templateUrl: './video-juegos-update.component.html'
})
export class VideoJuegosUpdateComponent implements OnInit {
  videoJuegos: IVideoJuegos;
  isSaving: boolean;

  caratulas: IImagen[];

  compannias: ICompannia[];

  valoraciones: IValoraciones[];

  plataformas: IPlataforma[];

  categorias: ICategoria[];

  ventas: IVenta[];

  carritos: ICarrito[];
  fechaLanzamientoDp: any;

  editForm = this.fb.group({
    id: [],
    titulo: [],
    sinopsis: [],
    pegi: [],
    fechaLanzamiento: [],
    precio: [],
    stock: [],
    destacado: [],
    caratulaId: [],
    companniaId: [],
    valoraciones: [],
    plataformas: [],
    categorias: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected videoJuegosService: VideoJuegosService,
    protected imagenService: ImagenService,
    protected companniaService: CompanniaService,
    protected valoracionesService: ValoracionesService,
    protected plataformaService: PlataformaService,
    protected categoriaService: CategoriaService,
    protected ventaService: VentaService,
    protected carritoService: CarritoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ videoJuegos }) => {
      this.updateForm(videoJuegos);
      this.videoJuegos = videoJuegos;
    });
    this.imagenService
      .query({ filter: 'videojuegos-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IImagen[]>) => mayBeOk.ok),
        map((response: HttpResponse<IImagen[]>) => response.body)
      )
      .subscribe(
        (res: IImagen[]) => {
          if (!this.videoJuegos.caratulaId) {
            this.caratulas = res;
          } else {
            this.imagenService
              .find(this.videoJuegos.caratulaId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IImagen>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IImagen>) => subResponse.body)
              )
              .subscribe(
                (subRes: IImagen) => (this.caratulas = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.companniaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompannia[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompannia[]>) => response.body)
      )
      .subscribe((res: ICompannia[]) => (this.compannias = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.valoracionesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IValoraciones[]>) => mayBeOk.ok),
        map((response: HttpResponse<IValoraciones[]>) => response.body)
      )
      .subscribe((res: IValoraciones[]) => (this.valoraciones = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.plataformaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPlataforma[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPlataforma[]>) => response.body)
      )
      .subscribe((res: IPlataforma[]) => (this.plataformas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.categoriaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICategoria[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICategoria[]>) => response.body)
      )
      .subscribe((res: ICategoria[]) => (this.categorias = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ventaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVenta[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVenta[]>) => response.body)
      )
      .subscribe((res: IVenta[]) => (this.ventas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.carritoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICarrito[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICarrito[]>) => response.body)
      )
      .subscribe((res: ICarrito[]) => (this.carritos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(videoJuegos: IVideoJuegos) {
    this.editForm.patchValue({
      id: videoJuegos.id,
      titulo: videoJuegos.titulo,
      sinopsis: videoJuegos.sinopsis,
      pegi: videoJuegos.pegi,
      fechaLanzamiento: videoJuegos.fechaLanzamiento,
      precio: videoJuegos.precio,
      stock: videoJuegos.stock,
      destacado: videoJuegos.destacado,
      caratulaId: videoJuegos.caratulaId,
      companniaId: videoJuegos.companniaId,
      valoraciones: videoJuegos.valoraciones,
      plataformas: videoJuegos.plataformas,
      categorias: videoJuegos.categorias
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const videoJuegos = this.createFromForm();
    if (videoJuegos.id !== undefined) {
      this.subscribeToSaveResponse(this.videoJuegosService.update(videoJuegos));
    } else {
      this.subscribeToSaveResponse(this.videoJuegosService.create(videoJuegos));
    }
  }

  private createFromForm(): IVideoJuegos {
    const entity = {
      ...new VideoJuegos(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      sinopsis: this.editForm.get(['sinopsis']).value,
      pegi: this.editForm.get(['pegi']).value,
      fechaLanzamiento: this.editForm.get(['fechaLanzamiento']).value,
      precio: this.editForm.get(['precio']).value,
      stock: this.editForm.get(['stock']).value,
      destacado: this.editForm.get(['destacado']).value,
      caratulaId: this.editForm.get(['caratulaId']).value,
      companniaId: this.editForm.get(['companniaId']).value,
      valoraciones: this.editForm.get(['valoraciones']).value,
      plataformas: this.editForm.get(['plataformas']).value,
      categorias: this.editForm.get(['categorias']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoJuegos>>) {
    result.subscribe((res: HttpResponse<IVideoJuegos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackImagenById(index: number, item: IImagen) {
    return item.id;
  }

  trackCompanniaById(index: number, item: ICompannia) {
    return item.id;
  }

  trackValoracionesById(index: number, item: IValoraciones) {
    return item.id;
  }

  trackPlataformaById(index: number, item: IPlataforma) {
    return item.id;
  }

  trackCategoriaById(index: number, item: ICategoria) {
    return item.id;
  }

  trackVentaById(index: number, item: IVenta) {
    return item.id;
  }

  trackCarritoById(index: number, item: ICarrito) {
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
}
