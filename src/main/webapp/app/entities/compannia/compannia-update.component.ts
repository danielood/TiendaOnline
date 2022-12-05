import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICompannia, Compannia } from 'app/shared/model/compannia.model';
import { CompanniaService } from './compannia.service';

@Component({
  selector: 'jhi-compannia-update',
  templateUrl: './compannia-update.component.html'
})
export class CompanniaUpdateComponent implements OnInit {
  compannia: ICompannia;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(protected companniaService: CompanniaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ compannia }) => {
      this.updateForm(compannia);
      this.compannia = compannia;
    });
  }

  updateForm(compannia: ICompannia) {
    this.editForm.patchValue({
      id: compannia.id,
      nombre: compannia.nombre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const compannia = this.createFromForm();
    if (compannia.id !== undefined) {
      this.subscribeToSaveResponse(this.companniaService.update(compannia));
    } else {
      this.subscribeToSaveResponse(this.companniaService.create(compannia));
    }
  }

  private createFromForm(): ICompannia {
    const entity = {
      ...new Compannia(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompannia>>) {
    result.subscribe((res: HttpResponse<ICompannia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
