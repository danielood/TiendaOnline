import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { TiendaOnlineSharedLibsModule, TiendaOnlineSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [TiendaOnlineSharedLibsModule, TiendaOnlineSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [TiendaOnlineSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TiendaOnlineSharedModule {
  static forRoot() {
    return {
      ngModule: TiendaOnlineSharedModule
    };
  }
}
