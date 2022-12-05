import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IImagen, Imagen } from 'app/shared/model/imagen.model';
import { ImagenService } from './imagen.service';

@Component({
  selector: 'jhi-imagen-update',
  templateUrl: './imagen-update.component.html'
})
export class ImagenUpdateComponent implements OnInit {
  imagen: IImagen;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    path: []
  });

  constructor(protected imagenService: ImagenService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ imagen }) => {
      this.updateForm(imagen);
      this.imagen = imagen;
    });
  }

  updateForm(imagen: IImagen) {
    this.editForm.patchValue({
      id: imagen.id,
      path: imagen.path
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const imagen = this.createFromForm();
    if (imagen.id !== undefined) {
      this.subscribeToSaveResponse(this.imagenService.update(imagen));
    } else {
      this.subscribeToSaveResponse(this.imagenService.create(imagen));
    }
  }

  private createFromForm(): IImagen {
    const entity = {
      ...new Imagen(),
      id: this.editForm.get(['id']).value,
      path: this.editForm.get(['path']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImagen>>) {
    result.subscribe((res: HttpResponse<IImagen>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
