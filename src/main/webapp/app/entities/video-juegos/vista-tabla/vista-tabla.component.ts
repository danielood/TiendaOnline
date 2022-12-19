import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FileService } from 'app/core/file.service';
import { IJuegoTabla, IVideoJuegos } from 'app/shared/model/video-juegos.model';
import { VideoJuegosUpdateComponent } from '../video-juegos-update.component';
import { VideoJuegosService } from '../video-juegos.service';

@Component({
  selector: 'jhi-vista-tabla',
  templateUrl: './vista-tabla.component.html',
  styles: [],
  styleUrls: ['./vista-tabla.component.scss']
})
export class VistaTablaComponent implements OnInit {
  @Input('juego') juego: IJuegoTabla;
  portada: File;
  url;

  constructor(private fileService: FileService, private modalService: NgbModal, private videoJuegoService: VideoJuegosService) {
    this.url = '';
  }

  ngOnInit() {
    if (this.juego.caratula) {
      this.portada = this.fileService.ficheroToFile(this.juego.caratula);
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.url = event.target.result;
      };
      if (this.portada) {
        reader.readAsDataURL(this.portada);
      } else {
        this.url = '';
      }
    }
  }

  openEditModal() {
    this.videoJuegoService.find(this.juego.id).subscribe((res: HttpResponse<IVideoJuegos>) => {
      const modal = this.modalService.open(VideoJuegosUpdateComponent, { size: 'lg', backdrop: 'static', keyboard: false });
      modal.componentInstance.videoJuego = res.body;
      modal.result.then(res => {
        if (res == 0) {
          window.location.reload();
        }
      });
    });
  }
}
