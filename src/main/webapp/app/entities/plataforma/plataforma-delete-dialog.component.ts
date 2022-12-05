import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPlataforma } from 'app/shared/model/plataforma.model';
import { PlataformaService } from './plataforma.service';

@Component({
  selector: 'jhi-plataforma-delete-dialog',
  templateUrl: './plataforma-delete-dialog.component.html'
})
export class PlataformaDeleteDialogComponent {
  plataforma: IPlataforma;

  constructor(
    protected plataformaService: PlataformaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.plataformaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'plataformaListModification',
        content: 'Deleted an plataforma'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-plataforma-delete-popup',
  template: ''
})
export class PlataformaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ plataforma }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(PlataformaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.plataforma = plataforma;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/plataforma', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/plataforma', { outlets: { popup: null } }]);
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
