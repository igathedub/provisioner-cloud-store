import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFeatures, Features } from 'app/shared/model/features.model';
import { FeaturesService } from './features.service';
import { INode } from 'app/shared/model/node.model';
import { NodeService } from 'app/entities/node/node.service';

@Component({
  selector: 'jhi-features-update',
  templateUrl: './features-update.component.html'
})
export class FeaturesUpdateComponent implements OnInit {
  isSaving: boolean;

  nodes: INode[];

  editForm = this.fb.group({
    id: [],
    proxy: [],
    friend: [],
    relay: [],
    lowPower: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected featuresService: FeaturesService,
    protected nodeService: NodeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ features }) => {
      this.updateForm(features);
    });
    this.nodeService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INode[]>) => mayBeOk.ok),
        map((response: HttpResponse<INode[]>) => response.body)
      )
      .subscribe((res: INode[]) => (this.nodes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(features: IFeatures) {
    this.editForm.patchValue({
      id: features.id,
      proxy: features.proxy,
      friend: features.friend,
      relay: features.relay,
      lowPower: features.lowPower
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const features = this.createFromForm();
    if (features.id !== undefined) {
      this.subscribeToSaveResponse(this.featuresService.update(features));
    } else {
      this.subscribeToSaveResponse(this.featuresService.create(features));
    }
  }

  private createFromForm(): IFeatures {
    return {
      ...new Features(),
      id: this.editForm.get(['id']).value,
      proxy: this.editForm.get(['proxy']).value,
      friend: this.editForm.get(['friend']).value,
      relay: this.editForm.get(['relay']).value,
      lowPower: this.editForm.get(['lowPower']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeatures>>) {
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
