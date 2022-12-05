/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { PlataformaUpdateComponent } from 'app/entities/plataforma/plataforma-update.component';
import { PlataformaService } from 'app/entities/plataforma/plataforma.service';
import { Plataforma } from 'app/shared/model/plataforma.model';

describe('Component Tests', () => {
  describe('Plataforma Management Update Component', () => {
    let comp: PlataformaUpdateComponent;
    let fixture: ComponentFixture<PlataformaUpdateComponent>;
    let service: PlataformaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [PlataformaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PlataformaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PlataformaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PlataformaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Plataforma(123);
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
        const entity = new Plataforma();
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
