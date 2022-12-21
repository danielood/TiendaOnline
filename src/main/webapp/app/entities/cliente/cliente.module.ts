import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TiendaOnlineSharedModule } from 'app/shared';
import {
  ClienteComponent,
  ClienteDetailComponent,
  ClienteUpdateComponent,
  ClienteDeletePopupComponent,
  ClienteDeleteDialogComponent,
  clienteRoute,
  clientePopupRoute
} from './';
import { AddDireccionModalComponent } from './add-direccion-modal/add-direccion-modal.component';

const ENTITY_STATES = [...clienteRoute, ...clientePopupRoute];

@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ClienteComponent,
    ClienteDetailComponent,
    ClienteUpdateComponent,
    ClienteDeleteDialogComponent,
    ClienteDeletePopupComponent,
    AddDireccionModalComponent
  ],
  entryComponents: [
    ClienteComponent,
    ClienteUpdateComponent,
    ClienteDeleteDialogComponent,
    ClienteDeletePopupComponent,
    AddDireccionModalComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineClienteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
