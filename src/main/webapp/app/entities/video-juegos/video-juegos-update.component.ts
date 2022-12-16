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
import { Compannia, ICompannia } from 'app/shared/model/compannia.model';
import { CompanniaService } from 'app/entities/compannia';
import { IValoraciones } from 'app/shared/model/valoraciones.model';
import { ValoracionesService } from 'app/entities/valoraciones';
import { IPlataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from 'app/entities/plataforma';
import { Categoria, ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { Fichero } from 'app/core/fichero.model';
import { FileService } from 'app/core/file.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from 'app/entities/venta';
import { ICarrito } from 'app/shared/model/carrito.model';
import { CarritoService } from 'app/entities/carrito';
@Component({
  selector: 'jhi-video-juegos-update',
  templateUrl: './video-juegos-update.component.html'
})
export class VideoJuegosUpdateComponent implements OnInit {
  @Input() videoJuego: IVideoJuegos;
  isSaving: boolean;

  compannias: Array<ICompannia>;

  plataformas: Array<IPlataforma>;

  categorias: ICategoria[];

  fechaLanzamientoDp: any;
  caratula;
  portadaFile;

  valoraciones: IValoraciones[];

  plataformas: IPlataforma[];

  categorias: ICategoria[];

  selectedComp: string;
  selectedCompObj: ICompannia;

  selectedPegi: Pegi;

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
    compannia: [],
    plataformas: [],
    categorias: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected videoJuegosService: VideoJuegosService,
    protected companniaService: CompanniaService,
    protected valoracionesService: ValoracionesService,
    protected plataformaService: PlataformaService,
    protected categoriaService: CategoriaService,
    private fb: FormBuilder,
    private fileService: FileService,
    private activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.compannias = new Array<ICompannia>();
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
    this.isPegi3 = false;
    this.isPegi7 = false;
    this.isPegi12 = false;
    this.isPegi16 = false;
    this.isPegi18 = false;
    this.caratula = '';
    this.selectedPlat = '';
    this.selectedPlatObj = {};
    this.selectedCat = '';
    this.selectedCatObj = {};
    if (!this.videoJuego) {
      this.videoJuego = new VideoJuegos();
    } else {
      this.updateForm(this.videoJuego);
    }
    if (this.videoJuego.plataformas) {
      this.listPlat = this.videoJuego.plataformas;
    } else {
      this.listPlat = new Array<IPlataforma>();
    }
    if (this.videoJuego.categorias) {
      this.listCat = this.videoJuego.categorias;
    } else {
      this.listCat = new Array<ICategoria>();
    }
    if (this.videoJuego.caratula) {
      this.portadaFile = this.fileService.ficheroToFile(this.videoJuego.caratula);
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.caratula = event.target.result;
      };
      if (this.portadaFile) {
        reader.readAsDataURL(this.portadaFile);
      } else {
        this.caratula = '';
      }
    }
  }

  updateForm(videoJuego: IVideoJuegos) {
    this.editForm.patchValue({
      id: videoJuego.id,
      titulo: videoJuego.titulo,
      sinopsis: videoJuego.sinopsis,
      fechaLanzamiento: videoJuego.fechaLanzamiento,
      precio: videoJuego.precio,
      stock: videoJuego.stock
    });
    if (videoJuego.compannia) {
      this.selectedComp = videoJuego.compannia.nombre;
    }
    this.selectPegi(videoJuego.pegi);
  }

  save() {
    this.isSaving = true;
    const videoJuegos = this.createFromForm();
    if (videoJuegos.id != undefined || videoJuegos.id != null) {
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
      compannia: this.selectedCompObj,
      plataformas: this.listPlat,
      categorias: this.listCat,
      caratula: this.caratulaFichero
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVideoJuegos>>) {
    result.subscribe((res: HttpResponse<IVideoJuegos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.activeModal.close(0);
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  selectPegi(pegi: String) {
    switch (pegi) {
      case 'PEGI3':
        this.isPegi3 = true;
        this.isPegi7 = false;
        this.isPegi12 = false;
        this.isPegi16 = false;
        this.isPegi18 = false;
        this.selectedPegi = Pegi.PEGI3;
        break;
      case 'PEGI7':
        this.isPegi3 = false;
        this.isPegi7 = true;
        this.isPegi12 = false;
        this.isPegi16 = false;
        this.isPegi18 = false;
        this.selectedPegi = Pegi.PEGI7;
        break;
      case 'PEGI12':
        this.isPegi3 = false;
        this.isPegi7 = false;
        this.isPegi12 = true;
        this.isPegi16 = false;
        this.isPegi18 = false;
        this.selectedPegi = Pegi.PEGI12;
        break;
      case 'PEGI16':
        this.isPegi3 = false;
        this.isPegi7 = false;
        this.isPegi12 = false;
        this.isPegi16 = true;
        this.isPegi18 = false;
        this.selectedPegi = Pegi.PEGI16;
        break;
      case 'PEGI18':
        this.isPegi3 = false;
        this.isPegi7 = false;
        this.isPegi12 = false;
        this.isPegi16 = false;
        this.isPegi18 = true;
        this.selectedPegi = Pegi.PEGI18;
        break;
    }
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

  onSelectCategoria() {
    this.selectedCatObj = this.categorias.find(p => p.nombre == this.selectedCat);
    if (!this.listCat.find(p => p.nombre.toLocaleLowerCase() == this.selectedCat.toLocaleLowerCase())) {
      if (this.selectedCatObj) {
        this.listCat.push(this.selectedCatObj);
      } else {
        const newCat: ICategoria = { ...new Categoria(), nombre: this.selectedCat };
        this.listCat.push(newCat);
      }
    }
  }

  onSelectCompannia() {
    this.selectedCompObj = this.compannias.find(c => c.nombre == this.selectedComp);
    if (!this.selectedCompObj) {
      this.selectedCompObj = { ...new Compannia(), nombre: this.selectedComp };
    }
  }

  deleteCat(index: number) {
    this.listCat.splice(index, 1);
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
