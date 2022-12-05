/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { CompanniaDetailComponent } from 'app/entities/compannia/compannia-detail.component';
import { Compannia } from 'app/shared/model/compannia.model';

describe('Component Tests', () => {
  describe('Compannia Management Detail Component', () => {
    let comp: CompanniaDetailComponent;
    let fixture: ComponentFixture<CompanniaDetailComponent>;
    const route = ({ data: of({ compannia: new Compannia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [CompanniaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CompanniaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CompanniaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.compannia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
