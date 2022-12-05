/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { ImagenUpdateComponent } from 'app/entities/imagen/imagen-update.component';
import { ImagenService } from 'app/entities/imagen/imagen.service';
import { Imagen } from 'app/shared/model/imagen.model';

describe('Component Tests', () => {
  describe('Imagen Management Update Component', () => {
    let comp: ImagenUpdateComponent;
    let fixture: ComponentFixture<ImagenUpdateComponent>;
    let service: ImagenService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [ImagenUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ImagenUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImagenUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImagenService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Imagen(123);
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
        const entity = new Imagen();
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
