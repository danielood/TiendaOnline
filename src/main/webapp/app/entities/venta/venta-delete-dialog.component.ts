import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVenta } from 'app/shared/model/venta.model';
import { VentaService } from './venta.service';

@Component({
  selector: 'jhi-venta-delete-dialog',
  templateUrl: './venta-delete-dialog.component.html'
})
export class VentaDeleteDialogComponent {
  venta: IVenta;

  constructor(protected ventaService: VentaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ventaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ventaListModification',
        content: 'Deleted an venta'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-venta-delete-popup',
  template: ''
})
export class VentaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ venta }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VentaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.venta = venta;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/venta', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/venta', { outlets: { popup: null } }]);
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
