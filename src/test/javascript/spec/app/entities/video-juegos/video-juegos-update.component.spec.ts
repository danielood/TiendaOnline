/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { VideoJuegosUpdateComponent } from 'app/entities/video-juegos/video-juegos-update.component';
import { VideoJuegosService } from 'app/entities/video-juegos/video-juegos.service';
import { VideoJuegos } from 'app/shared/model/video-juegos.model';

describe('Component Tests', () => {
  describe('VideoJuegos Management Update Component', () => {
    let comp: VideoJuegosUpdateComponent;
    let fixture: ComponentFixture<VideoJuegosUpdateComponent>;
    let service: VideoJuegosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [VideoJuegosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VideoJuegosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VideoJuegosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VideoJuegosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new VideoJuegos(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new VideoJuegos();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
