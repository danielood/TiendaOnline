import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FileService } from 'app/core/file.service';

import { IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosUpdateComponent } from './video-juegos-update.component';
import { VideoJuegosService } from './video-juegos.service';

@Component({
  selector: 'jhi-video-juegos-detail',
  templateUrl: './video-juegos-detail.component.html',
  styleUrls: ['./video-juegos-detail.component.scss']
})
export class VideoJuegosDetailComponent implements OnInit {
  videoJuegos: IVideoJuegos;
  portada;
  url;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private fileService: FileService,
    private videoJuegoService: VideoJuegosService,
    private modalService: NgbModal
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ videoJuegos }) => {
      this.videoJuegos = videoJuegos;
    });
    if (this.videoJuegos.caratula) {
      this.portada = this.fileService.ficheroToFile(this.videoJuegos.caratula);
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.url = event.target.result;
      };
      if (this.portada) {
        reader.readAsDataURL(this.portada);
      } else {
        this.url = '';
      }
    }
  }

  previousState() {
    window.history.back();
  }

  openEditModal() {
    this.videoJuegoService.find(this.videoJuegos.id).subscribe((res: HttpResponse<IVideoJuegos>) => {
      const modal = this.modalService.open(VideoJuegosUpdateComponent, { size: 'lg', backdrop: 'static', keyboard: false });
      modal.componentInstance.videoJuego = res.body;
      modal.result.then(res => {
        if (res == 0) {
          window.location.reload();
        }
      });
    });
  }
}
