import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVideoJuegos } from 'app/shared/model/video-juegos.model';

@Component({
  selector: 'jhi-video-juegos-detail',
  templateUrl: './video-juegos-detail.component.html'
})
export class VideoJuegosDetailComponent implements OnInit {
  videoJuegos: IVideoJuegos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ videoJuegos }) => {
      this.videoJuegos = videoJuegos;
    });
  }

  previousState() {
    window.history.back();
  }
}
