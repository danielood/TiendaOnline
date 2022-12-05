/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TiendaOnlineTestModule } from '../../../test.module';
import { VideoJuegosDetailComponent } from 'app/entities/video-juegos/video-juegos-detail.component';
import { VideoJuegos } from 'app/shared/model/video-juegos.model';

describe('Component Tests', () => {
  describe('VideoJuegos Management Detail Component', () => {
    let comp: VideoJuegosDetailComponent;
    let fixture: ComponentFixture<VideoJuegosDetailComponent>;
    const route = ({ data: of({ videoJuegos: new VideoJuegos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TiendaOnlineTestModule],
        declarations: [VideoJuegosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VideoJuegosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VideoJuegosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.videoJuegos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
