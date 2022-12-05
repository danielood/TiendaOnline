import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IValoraciones } from 'app/shared/model/valoraciones.model';
import { ValoracionesService } from './valoraciones.service';

@Component({
  selector: 'jhi-valoraciones-delete-dialog',
  templateUrl: './valoraciones-delete-dialog.component.html'
})
export class ValoracionesDeleteDialogComponent {
  valoraciones: IValoraciones;

  constructor(
    protected valoracionesService: ValoracionesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.valoracionesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'valoracionesListModification',
        content: 'Deleted an valoraciones'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-valoraciones-delete-popup',
  template: ''
})
export class ValoracionesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ valoraciones }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ValoracionesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.valoraciones = valoraciones;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/valoraciones', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/valoraciones', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
