/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TiendaOnlineTestModule } from '../../../test.module';
import { ImagenDeleteDialogComponent } from 'app/entities/imagen/imagen-delete-dialog.component';
import { ImagenService } from 'app/entities/imagen/imagen.service';

describe('Component Tests', () => {
  describe('Imagen Management Delete Component', () => {
    let comp: ImagenDeleteDialogComponent;
    let fixture: ComponentFixture<ImagenDeleteDialogComponent>;
    let service: ImagenService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [ImagenDeleteDialogComponent]
      })
        .overrideTemplate(ImagenDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImagenDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImagenService);
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
