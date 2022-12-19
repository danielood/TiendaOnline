/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TiendaOnlineTestModule } from '../../../test.module';
import { ValoracionesDeleteDialogComponent } from 'app/entities/valoraciones/valoraciones-delete-dialog.component';
import { ValoracionesService } from 'app/entities/valoraciones/valoraciones.service';

describe('Component Tests', () => {
  describe('Valoraciones Management Delete Component', () => {
    let comp: ValoracionesDeleteDialogComponent;
    let fixture: ComponentFixture<ValoracionesDeleteDialogComponent>;
    let service: ValoracionesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [ValoracionesDeleteDialogComponent]
      })
        .overrideTemplate(ValoracionesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ValoracionesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ValoracionesService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
