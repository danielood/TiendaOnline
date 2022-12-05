import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICompannia } from 'app/shared/model/compannia.model';
import { CompanniaService } from './compannia.service';

@Component({
  selector: 'jhi-compannia-delete-dialog',
  templateUrl: './compannia-delete-dialog.component.html'
})
export class CompanniaDeleteDialogComponent {
  compannia: ICompannia;

  constructor(protected companniaService: CompanniaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.companniaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'companniaListModification',
        content: 'Deleted an compannia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-compannia-delete-popup',
  template: ''
})
export class CompanniaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compannia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CompanniaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.compannia = compannia;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/compannia', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/compannia', { outlets: { popup: null } }]);
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
