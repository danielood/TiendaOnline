/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { ImagenDetailComponent } from 'app/entities/imagen/imagen-detail.component';
import { Imagen } from 'app/shared/model/imagen.model';

describe('Component Tests', () => {
  describe('Imagen Management Detail Component', () => {
    let comp: ImagenDetailComponent;
    let fixture: ComponentFixture<ImagenDetailComponent>;
    const route = ({ data: of({ imagen: new Imagen(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [ImagenDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ImagenDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ImagenDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.imagen).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
