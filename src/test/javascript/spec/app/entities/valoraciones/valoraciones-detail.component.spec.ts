/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { ValoracionesDetailComponent } from 'app/entities/valoraciones/valoraciones-detail.component';
import { Valoraciones } from 'app/shared/model/valoraciones.model';

describe('Component Tests', () => {
  describe('Valoraciones Management Detail Component', () => {
    let comp: ValoracionesDetailComponent;
    let fixture: ComponentFixture<ValoracionesDetailComponent>;
    const route = ({ data: of({ valoraciones: new Valoraciones(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [ValoracionesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ValoracionesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ValoracionesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.valoraciones).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
