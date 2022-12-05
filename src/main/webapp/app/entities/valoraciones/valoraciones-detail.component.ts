import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IValoraciones } from 'app/shared/model/valoraciones.model';

@Component({
  selector: 'jhi-valoraciones-detail',
  templateUrl: './valoraciones-detail.component.html'
})
export class ValoracionesDetailComponent implements OnInit {
  valoraciones: IValoraciones;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ valoraciones }) => {
      this.valoraciones = valoraciones;
    });
  }

  previousState() {
    window.history.back();
  }
}
