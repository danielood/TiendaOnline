import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FileService } from 'app/core/file.service';

import { IVideoJuegos } from 'app/shared/model/video-juegos.model';

@Component({
  selector: 'jhi-video-juegos-detail',
  templateUrl: './video-juegos-detail.component.html',
  styleUrls: ['./video-juegos-detail.component.scss']
})
export class VideoJuegosDetailComponent implements OnInit {
  videoJuegos: IVideoJuegos;
  portada;
  url;

  constructor(protected activatedRoute: ActivatedRoute, private fileService: FileService) {}

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
}
