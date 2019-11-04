import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IKeyIndex, KeyIndex } from 'app/shared/model/key-index.model';
import { KeyIndexService } from './key-index.service';
import { INode } from 'app/shared/model/node.model';
import { NodeService } from 'app/entities/node/node.service';

@Component({
  selector: 'jhi-key-index-update',
  templateUrl: './key-index-update.component.html'
})
export class KeyIndexUpdateComponent implements OnInit {
  isSaving: boolean;

  nodes: INode[];

  editForm = this.fb.group({
    id: [],
    index: [],
    updated: [],
    node: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected keyIndexService: KeyIndexService,
    protected nodeService: NodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ keyIndex }) => {
      this.updateForm(keyIndex);
    });
    this.nodeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INode[]>) => mayBeOk.ok),
        map((response: HttpResponse<INode[]>) => response.body)
      )
      .subscribe((res: INode[]) => (this.nodes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(keyIndex: IKeyIndex) {
    this.editForm.patchValue({
      id: keyIndex.id,
      index: keyIndex.index,
      updated: keyIndex.updated,
      node: keyIndex.node
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const keyIndex = this.createFromForm();
    if (keyIndex.id !== undefined) {
      this.subscribeToSaveResponse(this.keyIndexService.update(keyIndex));
    } else {
      this.subscribeToSaveResponse(this.keyIndexService.create(keyIndex));
    }
  }

  private createFromForm(): IKeyIndex {
    return {
      ...new KeyIndex(),
      id: this.editForm.get(['id']).value,
      index: this.editForm.get(['index']).value,
      updated: this.editForm.get(['updated']).value,
      node: this.editForm.get(['node']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKeyIndex>>) {
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
