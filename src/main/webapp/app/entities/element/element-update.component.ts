import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IElement, Element } from 'app/shared/model/element.model';
import { ElementService } from './element.service';
import { INode } from 'app/shared/model/node.model';
import { NodeService } from 'app/entities/node/node.service';

@Component({
  selector: 'jhi-element-update',
  templateUrl: './element-update.component.html'
})
export class ElementUpdateComponent implements OnInit {
  isSaving: boolean;

  nodes: INode[];

  editForm = this.fb.group({
    id: [],
    index: [],
    location: [],
    name: [],
    node: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected elementService: ElementService,
    protected nodeService: NodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ element }) => {
      this.updateForm(element);
    });
    this.nodeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INode[]>) => mayBeOk.ok),
        map((response: HttpResponse<INode[]>) => response.body)
      )
      .subscribe((res: INode[]) => (this.nodes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(element: IElement) {
    this.editForm.patchValue({
      id: element.id,
      index: element.index,
      location: element.location,
      name: element.name,
      node: element.node
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const element = this.createFromForm();
    if (element.id !== undefined) {
      this.subscribeToSaveResponse(this.elementService.update(element));
    } else {
      this.subscribeToSaveResponse(this.elementService.create(element));
    }
  }

  private createFromForm(): IElement {
    return {
      ...new Element(),
      id: this.editForm.get(['id']).value,
      index: this.editForm.get(['index']).value,
      location: this.editForm.get(['location']).value,
      name: this.editForm.get(['name']).value,
      node: this.editForm.get(['node']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IElement>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackNodeById(index: number, item: INode) {
    return item.id;
  }
}
