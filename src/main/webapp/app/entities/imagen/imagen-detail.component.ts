import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IImagen } from 'app/shared/model/imagen.model';

@Component({
  selector: 'jhi-imagen-detail',
  templateUrl: './imagen-detail.component.html'
})
export class ImagenDetailComponent implements OnInit {
  imagen: IImagen;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imagen }) => {
      this.imagen = imagen;
    });
  }

  previousState() {
    window.history.back();
  }
}
