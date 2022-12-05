/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TiendaOnlineTestModule } from '../../../test.module';
import { CompanniaDeleteDialogComponent } from 'app/entities/compannia/compannia-delete-dialog.component';
import { CompanniaService } from 'app/entities/compannia/compannia.service';

describe('Component Tests', () => {
  describe('Compannia Management Delete Component', () => {
    let comp: CompanniaDeleteDialogComponent;
    let fixture: ComponentFixture<CompanniaDeleteDialogComponent>;
    let service: CompanniaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [CompanniaDeleteDialogComponent]
      })
        .overrideTemplate(CompanniaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanniaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanniaService);
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
