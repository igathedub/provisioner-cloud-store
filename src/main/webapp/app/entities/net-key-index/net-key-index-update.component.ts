import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INetKeyIndex, NetKeyIndex } from 'app/shared/model/net-key-index.model';
import { NetKeyIndexService } from './net-key-index.service';
import { INode } from 'app/shared/model/node.model';
import { NodeService } from 'app/entities/node/node.service';

@Component({
  selector: 'jhi-net-key-index-update',
  templateUrl: './net-key-index-update.component.html'
})
export class NetKeyIndexUpdateComponent implements OnInit {
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
    protected netKeyIndexService: NetKeyIndexService,
    protected nodeService: NodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ netKeyIndex }) => {
      this.updateForm(netKeyIndex);
    });
    this.nodeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INode[]>) => mayBeOk.ok),
        map((response: HttpResponse<INode[]>) => response.body)
      )
      .subscribe((res: INode[]) => (this.nodes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(netKeyIndex: INetKeyIndex) {
    this.editForm.patchValue({
      id: netKeyIndex.id,
      index: netKeyIndex.index,
      updated: netKeyIndex.updated,
      node: netKeyIndex.node
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const netKeyIndex = this.createFromForm();
    if (netKeyIndex.id !== undefined) {
      this.subscribeToSaveResponse(this.netKeyIndexService.update(netKeyIndex));
    } else {
      this.subscribeToSaveResponse(this.netKeyIndexService.create(netKeyIndex));
    }
  }

  private createFromForm(): INetKeyIndex {
    return {
      ...new NetKeyIndex(),
      id: this.editForm.get(['id']).value,
      index: this.editForm.get(['index']).value,
      updated: this.editForm.get(['updated']).value,
      node: this.editForm.get(['node']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INetKeyIndex>>) {
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
