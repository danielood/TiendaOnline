/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { CompanniaUpdateComponent } from 'app/entities/compannia/compannia-update.component';
import { CompanniaService } from 'app/entities/compannia/compannia.service';
import { Compannia } from 'app/shared/model/compannia.model';

describe('Component Tests', () => {
  describe('Compannia Management Update Component', () => {
    let comp: CompanniaUpdateComponent;
    let fixture: ComponentFixture<CompanniaUpdateComponent>;
    let service: CompanniaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [CompanniaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CompanniaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CompanniaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CompanniaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Compannia(123);
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
        const entity = new Compannia();
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
