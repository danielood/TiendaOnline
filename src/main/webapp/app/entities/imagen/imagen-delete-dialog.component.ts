import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImagen } from 'app/shared/model/imagen.model';
import { ImagenService } from './imagen.service';

@Component({
  selector: 'jhi-imagen-delete-dialog',
  templateUrl: './imagen-delete-dialog.component.html'
})
export class ImagenDeleteDialogComponent {
  imagen: IImagen;

  constructor(protected imagenService: ImagenService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.imagenService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'imagenListModification',
        content: 'Deleted an imagen'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-imagen-delete-popup',
  template: ''
})
export class ImagenDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imagen }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ImagenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.imagen = imagen;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/imagen', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/imagen', { outlets: { popup: null } }]);
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
