/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TiendaOnlineTestModule } from '../../../test.module';
import { PlataformaDeleteDialogComponent } from 'app/entities/plataforma/plataforma-delete-dialog.component';
import { PlataformaService } from 'app/entities/plataforma/plataforma.service';

describe('Component Tests', () => {
  describe('Plataforma Management Delete Component', () => {
    let comp: PlataformaDeleteDialogComponent;
    let fixture: ComponentFixture<PlataformaDeleteDialogComponent>;
    let service: PlataformaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [PlataformaDeleteDialogComponent]
      })
        .overrideTemplate(PlataformaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PlataformaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlataformaService);
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
