import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosService } from './video-juegos.service';

@Component({
  selector: 'jhi-video-juegos-delete-dialog',
  templateUrl: './video-juegos-delete-dialog.component.html'
})
export class VideoJuegosDeleteDialogComponent {
  videoJuegos: IVideoJuegos;

  constructor(
    protected videoJuegosService: VideoJuegosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.videoJuegosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'videoJuegosListModification',
        content: 'Deleted an videoJuegos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-video-juegos-delete-popup',
  template: ''
})
export class VideoJuegosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ videoJuegos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VideoJuegosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.videoJuegos = videoJuegos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/video-juegos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/video-juegos', { outlets: { popup: null } }]);
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
