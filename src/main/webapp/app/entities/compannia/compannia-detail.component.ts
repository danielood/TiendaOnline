import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompannia } from 'app/shared/model/compannia.model';

@Component({
  selector: 'jhi-compannia-detail',
  templateUrl: './compannia-detail.component.html'
})
export class CompanniaDetailComponent implements OnInit {
  compannia: ICompannia;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compannia }) => {
      this.compannia = compannia;
    });
  }

  previousState() {
    window.history.back();
  }
}
