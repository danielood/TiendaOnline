import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TiendaOnlineSharedModule } from 'app/shared';
import {
  ImagenComponent,
  ImagenDetailComponent,
  ImagenUpdateComponent,
  ImagenDeletePopupComponent,
  ImagenDeleteDialogComponent,
  imagenRoute,
  imagenPopupRoute
} from './';

const ENTITY_STATES = [...imagenRoute, ...imagenPopupRoute];

@NgModule({
  imports: [TiendaOnlineSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [ImagenComponent, ImagenDetailComponent, ImagenUpdateComponent, ImagenDeleteDialogComponent, ImagenDeletePopupComponent],
  entryComponents: [ImagenComponent, ImagenUpdateComponent, ImagenDeleteDialogComponent, ImagenDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineImagenModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
