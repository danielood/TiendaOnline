import { Component, Input, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICompannia, Compannia } from 'app/shared/model/compannia.model';
import { CompanniaService } from './compannia.service';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-compannia-update',
  templateUrl: './compannia-update.component.html'
})
export class CompanniaUpdateComponent implements OnInit {
  @Input() compannia: ICompannia;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: []
  });

  constructor(
    protected companniaService: CompanniaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder,
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
    this.isSaving = false;
    if (!this.compannia) {
      this.compannia = new Compannia();
    }
    this.updateForm(this.compannia);
  }

  updateForm(compannia: ICompannia) {
    this.editForm.patchValue({
      id: compannia.id,
      nombre: compannia.nombre
    });
  }

  previousState() {
    this.activeModal.close(0);
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
