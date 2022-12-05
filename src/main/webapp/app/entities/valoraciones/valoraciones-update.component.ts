import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IValoraciones, Valoraciones } from 'app/shared/model/valoraciones.model';
import { ValoracionesService } from './valoraciones.service';
import { ICliente } from 'app/shared/model/cliente.model';
import { ClienteService } from 'app/entities/cliente';
import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosService } from 'app/entities/video-juegos';

@Component({
  selector: 'jhi-valoraciones-update',
  templateUrl: './valoraciones-update.component.html'
})
export class ValoracionesUpdateComponent implements OnInit {
  valoraciones: IValoraciones;
  isSaving: boolean;

  clientes: ICliente[];

  videojuegos: IVideoJuegos[];

  editForm = this.fb.group({
    id: [],
    puntuacion: [],
    comentario: [],
    clienteId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected valoracionesService: ValoracionesService,
    protected clienteService: ClienteService,
    protected videoJuegosService: VideoJuegosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ valoraciones }) => {
      this.updateForm(valoraciones);
      this.valoraciones = valoraciones;
    });
    this.clienteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICliente[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICliente[]>) => response.body)
      )
      .subscribe((res: ICliente[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.videoJuegosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVideoJuegos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVideoJuegos[]>) => response.body)
      )
      .subscribe((res: IVideoJuegos[]) => (this.videojuegos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(valoraciones: IValoraciones) {
    this.editForm.patchValue({
      id: valoraciones.id,
      puntuacion: valoraciones.puntuacion,
      comentario: valoraciones.comentario,
      clienteId: valoraciones.clienteId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const valoraciones = this.createFromForm();
    if (valoraciones.id !== undefined) {
      this.subscribeToSaveResponse(this.valoracionesService.update(valoraciones));
    } else {
      this.subscribeToSaveResponse(this.valoracionesService.create(valoraciones));
    }
  }

  private createFromForm(): IValoraciones {
    const entity = {
      ...new Valoraciones(),
      id: this.editForm.get(['id']).value,
      puntuacion: this.editForm.get(['puntuacion']).value,
      comentario: this.editForm.get(['comentario']).value,
      clienteId: this.editForm.get(['clienteId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IValoraciones>>) {
    result.subscribe((res: HttpResponse<IValoraciones>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
