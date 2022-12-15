import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, ReplaySubject } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVideoJuegos, Pegi, VideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosService } from './video-juegos.service';
import { ImagenService } from 'app/entities/imagen';
import { ICompannia } from 'app/shared/model/compannia.model';
import { CompanniaService } from 'app/entities/compannia';
import { IPlataforma, Plataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from 'app/entities/plataforma';
import { ICategoria } from 'app/shared/model/categoria.model';
import { CategoriaService } from 'app/entities/categoria';
import { Fichero } from 'app/core/fichero.model';
@Component({
  selector: 'jhi-video-juegos-update',
  templateUrl: './video-juegos-update.component.html',
  styleUrls: ['./video-juegos-update.component.scss']
})
export class VideoJuegosUpdateComponent implements OnInit {
  @Input() videoJuegos: IVideoJuegos;
  isSaving: boolean;

  compannias: ICompannia[];

  plataformas: Array<IPlataforma>;

  categorias: ICategoria[];

  fechaLanzamientoDp: any;
  caratula;

  isPegi3: boolean;
  isPegi7: boolean;
  isPegi12: boolean;
  isPegi16: boolean;
  isPegi18: boolean;

  selectedPlat: string;
  selectedPlatObj: IPlataforma;
  listPlat: Array<IPlataforma>;

  selectedCat: string;
  selectedCatObj: ICategoria;
  listCat: Array<ICategoria>;

  selectedPegi: Pegi;

  caratulaFichero: Fichero;

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
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
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
    if (!this.videoJuegos) {
      this.videoJuegos = new VideoJuegos();
    }
    if (this.videoJuegos.plataformas) {
      this.listPlat = this.videoJuegos.plataformas;
    } else {
      this.listPlat = new Array<IPlataforma>();
    }
    if (this.videoJuegos.categorias) {
      this.listCat = this.videoJuegos.categorias;
    } else {
      this.listCat = new Array<ICategoria>();
    }
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
      pegi: this.selectedPegi,
      fechaLanzamiento: this.editForm.get(['fechaLanzamiento']).value,
      precio: this.editForm.get(['precio']).value,
      stock: this.editForm.get(['stock']).value,
      companniaId: this.editForm.get(['companniaId']).value,
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
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCompanniaById(index: number, item: ICompannia) {
    return item.id;
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

  onChangeCaratula(event) {
    const file: File = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (event: any) => {
      this.caratula = event.target.result;
    };
    if (file) {
      reader.readAsDataURL(file);
      this.convertFile(file).subscribe(base64 => {
        this.caratulaFichero = new Fichero(file.name, file.type, base64);
      });
    } else {
      this.caratula = '';
    }
  }

  onSelectPlataforma() {
    this.selectedPlatObj = this.plataformas.find(p => p.nombre == this.selectedPlat);
    if (!this.listPlat.find(p => p.nombre.toLocaleLowerCase() == this.selectedPlat.toLocaleLowerCase())) {
      if (this.selectedPlatObj) {
        this.listPlat.push(this.selectedPlatObj);
      } else {
        const newPlat: IPlataforma = { ...new Plataforma(), nombre: this.selectedPlat };
        this.listPlat.push(newPlat);
      }
    }
  }

  deletePlat(index: number) {
    this.listPlat.splice(index, 1);
  }

  onSelectCategoria() {
    this.selectedCatObj = this.plataformas.find(p => p.nombre == this.selectedCat);
    if (!this.listCat.find(p => p.nombre.toLocaleLowerCase() == this.selectedCat.toLocaleLowerCase())) {
      if (this.selectedCatObj) {
        this.listCat.push(this.selectedCatObj);
      } else {
        const newPlat: IPlataforma = { ...new Plataforma(), nombre: this.selectedCat };
        this.listCat.push(newPlat);
      }
    }
  }

  deleteCat(index: number) {
    this.listCat.splice(index, 1);
  }

  private convertFile(file: File): Observable<string> {
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = event => result.next(btoa(event.target.result.toString()));
    return result;
  }
}
