/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TiendaOnlineTestModule } from '../../../test.module';
import { VentaDeleteDialogComponent } from 'app/entities/venta/venta-delete-dialog.component';
import { VentaService } from 'app/entities/venta/venta.service';

describe('Component Tests', () => {
  describe('Venta Management Delete Component', () => {
    let comp: VentaDeleteDialogComponent;
    let fixture: ComponentFixture<VentaDeleteDialogComponent>;
    let service: VentaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [VentaDeleteDialogComponent]
      })
        .overrideTemplate(VentaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VentaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VentaService);
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
