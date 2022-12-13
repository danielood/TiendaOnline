import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
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

@Component({
  selector: 'jhi-video-juegos-update',
  templateUrl: './video-juegos-update.component.html',
  styleUrls: ['./video-juegos-update.component.scss']
})
export class VideoJuegosUpdateComponent implements OnInit {
  videoJuegos: IVideoJuegos;
  isSaving: boolean;

  compannias: ICompannia[];

  plataformas: IPlataforma[];

  categorias: ICategoria[];

  ventas: IVenta[];
  fechaLanzamientoDp: any;
  caratula;

  isPegi3: boolean;
  isPegi7: boolean;
  isPegi12: boolean;
  isPegi16: boolean;
  isPegi18: boolean;

  editForm = this.fb.group({
    id: [],
    titulo: [],
    sinopsis: [],
    pegi: [],
    fechaLanzamiento: [],
    precio: [],
    stock: [],
    companniaId: [],
    plataformas: [],
    categorias: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected videoJuegosService: VideoJuegosService,
    protected imagenService: ImagenService,
    protected companniaService: CompanniaService,
    protected plataformaService: PlataformaService,
    protected categoriaService: CategoriaService,
    protected ventaService: VentaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ videoJuegos }) => {
      this.updateForm(videoJuegos);
      this.videoJuegos = videoJuegos;
    });
    this.companniaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICompannia[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICompannia[]>) => response.body)
      )
      .subscribe((res: ICompannia[]) => (this.compannias = res), (res: HttpErrorResponse) => this.onError(res.message));
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
    this.isPegi3 = false;
    this.isPegi7 = false;
    this.isPegi12 = false;
    this.isPegi16 = false;
    this.isPegi18 = false;
    this.caratula = '';
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
      companniaId: videoJuegos.companniaId,
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
      companniaId: this.editForm.get(['companniaId']).value,
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

  selectPegi(pegi: String) {
    switch (pegi) {
      case 'PEGI3':
        this.isPegi3 = true;
        this.isPegi7 = false;
        this.isPegi12 = false;
        this.isPegi16 = false;
        this.isPegi18 = false;
        break;
      case 'PEGI7':
        this.isPegi3 = false;
        this.isPegi7 = true;
        this.isPegi12 = false;
        this.isPegi16 = false;
        this.isPegi18 = false;
        break;
      case 'PEGI12':
        this.isPegi3 = false;
        this.isPegi7 = false;
        this.isPegi12 = true;
        this.isPegi16 = false;
        this.isPegi18 = false;
        break;
      case 'PEGI16':
        this.isPegi3 = false;
        this.isPegi7 = false;
        this.isPegi12 = false;
        this.isPegi16 = true;
        this.isPegi18 = false;
        break;
      case 'PEGI18':
        this.isPegi3 = false;
        this.isPegi7 = false;
        this.isPegi12 = false;
        this.isPegi16 = false;
        this.isPegi18 = true;
        break;
    }
  }

  onChange(event) {
    var reader = new FileReader();
    reader.onload = (event: any) => {
      this.caratula = event.target.result;
    };
    if (event.target.files[0]) {
      reader.readAsDataURL(event.target.files[0]);
    } else {
      this.caratula = '';
    }
  }

  onSelectPlataforma(event) {
    console.log(event);
  }
}
