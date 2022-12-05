import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPlataforma } from 'app/shared/model/plataforma.model';

@Component({
  selector: 'jhi-plataforma-detail',
  templateUrl: './plataforma-detail.component.html'
})
export class PlataformaDetailComponent implements OnInit {
  plataforma: IPlataforma;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ plataforma }) => {
      this.plataforma = plataforma;
    });
  }

  previousState() {
    window.history.back();
  }
}
