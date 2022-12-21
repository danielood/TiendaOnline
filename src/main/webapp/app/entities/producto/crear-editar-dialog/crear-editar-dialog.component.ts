import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Fichero } from 'app/core/fichero.model';
import { FileService } from 'app/core/file.service';
import { PlataformaService } from 'app/entities/plataforma';
import { VentaService } from 'app/entities/venta';
import { IImagen } from 'app/shared/model/imagen.model';
import { IPlataforma, Plataforma } from 'app/shared/model/plataforma.model';
import { IProducto, Producto } from 'app/shared/model/producto.model';
import { IVenta } from 'app/shared/model/venta.model';
import { JhiAlertService } from 'ng-jhipster';
import { Observable, ReplaySubject } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductoService } from '../producto.service';

@Component({
  selector: 'jhi-crear-editar-dialog',
  templateUrl: './crear-editar-dialog.component.html',
  styles: []
})
export class CrearEditarDialogComponent implements OnInit {
  @Input() producto: IProducto;
  isSaving: boolean;

  plataformas: IPlataforma[];

  selectedPlat: string;
  selectedPlatObj: IPlataforma;
  listPlat: Array<IPlataforma>;

  imagenFichero: Fichero;
  imagenUrl;
  imagenFile: File;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    descripcion: [],
    precio: [],
    stock: [],
    plataformas: []
  });

  constructor(
    private fb: FormBuilder,
    protected jhiAlertService: JhiAlertService,
    protected productoService: ProductoService,
    protected plataformaService: PlataformaService,
    public activeModal: NgbActiveModal,
    private fileService: FileService
  ) {}
  ngOnInit(): void {
    this.isSaving = false;
    this.plataformaService
      .findAll()
      .pipe(
        filter((mayBeOk: HttpResponse<IPlataforma[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPlataforma[]>) => response.body)
      )
      .subscribe((res: IPlataforma[]) => (this.plataformas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.selectedPlat = '';
    this.selectedPlatObj = {};
    this.imagenFichero = {};
    this.imagenUrl = '';
    if (!this.producto) {
      this.producto = new Producto();
    } else {
      this.updateForm(this.producto);
    }
    if (this.producto.plataformas) {
      this.listPlat = this.producto.plataformas;
    } else {
      this.listPlat = new Array<IPlataforma>();
    }
    if (this.producto.imagen) {
      this.imagenFile = this.fileService.ficheroToFile(this.producto.imagen);
      const reader = new FileReader();
      reader.onload = (event: any) => {
        this.imagenUrl = event.target.result;
      };
      if (this.imagenFile) {
        reader.readAsDataURL(this.imagenFile);
      } else {
        this.imagenUrl = '';
      }
    }
  }

  updateForm(producto: IProducto) {
    this.editForm.patchValue({
      id: producto.id,
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      stock: producto.stock
    });
    if (producto.imagen) {
      this.imagenFichero = producto.imagen;
    }
  }

  save() {
    this.isSaving = true;
    const producto = this.createFromForm();
    if (producto.id != undefined || producto.id != null) {
      this.subscribeToSaveResponse(this.productoService.update(producto));
    } else {
      this.subscribeToSaveResponse(this.productoService.create(producto));
    }
  }

  private createFromForm(): IProducto {
    const entity = {
      ...new Producto(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      precio: this.editForm.get(['precio']).value,
      stock: this.editForm.get(['stock']).value,
      plataformas: this.listPlat,
      imagen: this.imagenFichero
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProducto>>) {
    result.subscribe((res: HttpResponse<IProducto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.activeModal.close(0);
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }

  onSelectPlataforma() {
    this.selectedPlatObj = this.plataformas.find(p => p.nombre == this.selectedPlat);
    if (!this.listPlat.find(p => p.nombre.toLocaleLowerCase() == this.selectedPlat.toLocaleLowerCase())) {
      if (this.selectedPlatObj) {
        this.listPlat.push(this.selectedPlatObj);
      } else {
        const newPlat: IPlataforma = { ...new Plataforma(), nombre: this.selectedPlat };
        this.listPlat.push(newPlat);
      }
    }
  }

  deletePlat(index: number) {
    this.listPlat.splice(index, 1);
  }

  onChangeCaratula(event) {
    const file: File = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (event: any) => {
      this.imagenUrl = event.target.result;
    };
    if (file) {
      reader.readAsDataURL(file);
      this.convertFile(file).subscribe(base64 => {
        this.imagenFichero = new Fichero(file.name, file.type, base64);
      });
    } else {
      this.imagenUrl = '';
    }
  }
  private convertFile(file: File): Observable<string> {
    const result = new ReplaySubject<string>(1);
    const reader = new FileReader();
    reader.readAsBinaryString(file);
    reader.onload = event => result.next(btoa(event.target.result.toString()));
    return result;
  }
}
