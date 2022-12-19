/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { ValoracionesUpdateComponent } from 'app/entities/valoraciones/valoraciones-update.component';
import { ValoracionesService } from 'app/entities/valoraciones/valoraciones.service';
import { Valoraciones } from 'app/shared/model/valoraciones.model';

describe('Component Tests', () => {
  describe('Valoraciones Management Update Component', () => {
    let comp: ValoracionesUpdateComponent;
    let fixture: ComponentFixture<ValoracionesUpdateComponent>;
    let service: ValoracionesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [ValoracionesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ValoracionesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ValoracionesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ValoracionesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Valoraciones(123);
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
        const entity = new Valoraciones();
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
