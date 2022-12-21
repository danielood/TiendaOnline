import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPlataforma, Plataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from './plataforma.service';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosService } from 'app/entities/video-juegos';
import { IProducto } from 'app/shared/model/producto.model';
import { ProductoService } from 'app/entities/producto';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-plataforma-update',
  templateUrl: './plataforma-update.component.html'
})
export class PlataformaUpdateComponent implements OnInit {
  @Input() plataforma: IPlataforma;
  isSaving: boolean;

  videojuegos: IVideoJuegos[];

  productos: IProducto[];

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(
    public activeModal: NgbActiveModal,
    protected jhiAlertService: JhiAlertService,
    protected plataformaService: PlataformaService,
    protected videoJuegosService: VideoJuegosService,
    protected productoService: ProductoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    if (!this.plataforma) {
      this.plataforma = new Plataforma();
    }
    this.updateForm(this.plataforma);
  }

  updateForm(plataforma: IPlataforma) {
    this.editForm.patchValue({
      id: plataforma.id,
      nombre: plataforma.nombre
    });
  }

  previousState() {
    this.activeModal.close(0);
  }

  save() {
    this.isSaving = true;
    const plataforma = this.createFromForm();
    if (plataforma.id !== undefined) {
      this.subscribeToSaveResponse(this.plataformaService.update(plataforma));
    } else {
      this.subscribeToSaveResponse(this.plataformaService.create(plataforma));
    }
  }

  private createFromForm(): IPlataforma {
    const entity = {
      ...new Plataforma(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlataforma>>) {
    result.subscribe((res: HttpResponse<IPlataforma>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackVideoJuegosById(index: number, item: IVideoJuegos) {
    return item.id;
  }

  trackProductoById(index: number, item: IProducto) {
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
